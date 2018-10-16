package com.shiro.vue.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.dto.UserDto;
import com.shiro.vue.pojo.dto.UserInfoDto;
import com.shiro.vue.pojo.entity.User;
import com.shiro.vue.pojo.vo.UserInfoVo;
import com.shiro.vue.pojo.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-11
 * Time: 9:25
 */
@Service
public interface IUserService extends IService<User> {
    /**
     * @desc: 登陆验证
     */
    List<UserInfoDto> checkUser(String name, String pass);

    /**
     * 登录
     *
     * @param name
     * @param pass
     * @param session
     * @param request
     * @return
     */
    Object login(String name, String pass, HttpSession session, HttpServletRequest request);


    /**
     * 查询用户
     *
     * @param page
     * @param dto
     * @return
     */
    List<UserDto> findUserByPage(Page<UserDto> page, ParamsDto dto);


    /**
     * 新增用户
     *
     * @param userVo
     * @return
     */
    Object addUser(UserVo userVo);


    /**
     * 批量删除用户
     *
     * @param ids
     * @return
     */
    Object delUsers(String[] ids);

    /**
     * 修改用户状态
     *
     * @param id
     * @param type
     * @return
     */
    Object editUserStatus(String id, Integer type);

    /**
     * 用户修改自己的个人信息
     *
     * @param v0
     * @return
     */
    Object editUserInfo(UserInfoVo v0);
}
