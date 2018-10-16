package com.shiro.vue.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.exception.MyException;
import com.shiro.vue.mapper.UserMapper;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.dto.RolePermisDto;
import com.shiro.vue.pojo.dto.UserDto;
import com.shiro.vue.pojo.dto.UserInfoDto;
import com.shiro.vue.pojo.entity.LoginLog;
import com.shiro.vue.pojo.entity.User;
import com.shiro.vue.pojo.entity.UserRole;
import com.shiro.vue.pojo.vo.UserInfoVo;
import com.shiro.vue.pojo.vo.UserVo;
import com.shiro.vue.service.ILoginLogService;
import com.shiro.vue.service.IRolePermissionService;
import com.shiro.vue.service.IUserRoleService;
import com.shiro.vue.service.IUserService;
import com.shiro.vue.utils.ResultUtil;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author: QuincySu
 * Date: 2018-10-11
 * Time: 9:46
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private ILoginLogService loginLogService;

    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRolePermissionService rolePermissionService;

    @Override
    public List<UserInfoDto> checkUser(String name, String pass) {
        return super.baseMapper.checkUser(name, pass);
    }

    @Override
    public Object login(String name, String pass, HttpSession session, HttpServletRequest request) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name, SecureUtil.md5(pass));
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        UserInfoDto userInfoDto = (UserInfoDto) subject.getPrincipal();
        session.setAttribute("user", userInfoDto);

        // 登录日志
        LoginLog loginLog = new LoginLog();
        loginLog.setUid(userInfoDto.getId());
        loginLog.setLoginTime(new Date());
        loginLog.setLoginIP(request.getRemoteAddr());
        loginLog.setLoginTotal(loginLogService.findMaxLoginTatalByUserId(userInfoDto.getId())); // 登录总次数
        loginLogService.insert(loginLog);

        // 一级 模块
        List<RolePermisDto> parentList = rolePermissionService.findRolesPermisByFatherId(null, null);
        List<RolePermisDto> sonList = null;
        List<RolePermisDto> sonssonList = null;
        String rid = userInfoDto.getRoleName().equals("admin") ? null : userInfoDto.getRoleid();
        for (int i = 0, j = parentList.size(); i < j; i++) {

            List<RolePermisDto> trueChildrenList = new ArrayList<>();

            // 二级 页面
            sonList = rolePermissionService.findRolesPermisByFatherId(parentList.get(i).getId(), null);
            for (int k = 0, l = sonList.size(); k < l; k++) {

                // 三级按钮
                sonssonList = rolePermissionService.findRolesPermisByFatherId(sonList.get(k).getId(), rid);
                // 如果按钮级拥有权限说明页面也有权限
                if (!sonssonList.isEmpty() && sonssonList.size() > 0) {
                    trueChildrenList.add(sonList.get(k));
                }
            }
            parentList.get(i).setChildren((ArrayList<RolePermisDto>) trueChildrenList);
        }
        userInfoDto.setRolePermis((ArrayList) parentList);
        return ResultUtil.result(EnumCode.OK.getValue(), "登陆成功", JSON.toJSON(userInfoDto));
    }

    @Override
    public List<UserDto> findUserByPage(Page<UserDto> page, ParamsDto dto) {
        return super.baseMapper.findUserByPage(page, dto);
    }

    @Override
    @Transactional
    public Object addUser(UserVo userVo) {
        Map<String, Object> map = new HashMap<>();
        map.put("nickname", userVo.getName().trim());
        List<User> list = super.baseMapper.selectByMap(map);
        if (list.size() > 0) {
            throw new MyException(ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), "昵称已经存在", null));
        }
        User user = new User();
        user.setNickname(userVo.getName());
        user.setPswd(SecureUtil.md5(userVo.getPass()));
        user.setEmail(userVo.getEmail());
        user.setCreateTime(new Date());
        super.baseMapper.insert(user);

        UserRole ur = new UserRole();
        ur.setUid(user.getId());
        ur.setRid(userVo.getRole());
        Boolean result = userRoleService.insert(ur);
        if (!result) {
            throw new MyException(ResultUtil.result(EnumCode.INTERNAL_SERVER_ERROR.getValue(), EnumCode.INTERNAL_SERVER_ERROR.getText(), null));
        }
        return ResultUtil.result(EnumCode.OK.getValue(), "新增成功");
    }

    @Override
    public Object delUsers(String[] ids) {
        for (String id : ids) {
            baseMapper.deleteById(id);
        }
        return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
    }

    @Override
    public Object editUserStatus(String id, Integer type) {
        User user = new User();
        user.setId(id);
        user.setStatus(type);
        Integer row = super.baseMapper.updateById(user);
        return row > 0 ? ResultUtil.result(EnumCode.OK.getValue(), type == 0 ? "已禁止登陆" : "已允许登陆") : ResultUtil.result(EnumCode.INTERNAL_SERVER_ERROR.getValue(), "修改失败");
    }

    @Override
    public Object editUserInfo(UserInfoVo v0) {
        User user = new User();
        user.setId(v0.getId());
        user.setNickname(v0.getName());
        user.setEmail(v0.getEmail());
        user.setHeadPortraits(v0.getUserImg());
        Integer row = super.baseMapper.updateById(user);
        if (row > 0) {
            return ResultUtil.result(EnumCode.OK.getValue(), "修改成功，下次登录生效");
        }
        return ResultUtil.result(EnumCode.INTERNAL_SERVER_ERROR.getValue(), "修改失败");
    }

}
