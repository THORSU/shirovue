package com.shiro.vue.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.Enum.EnumRoleType;
import com.shiro.vue.api.base.BaseApi;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.dto.UserDto;
import com.shiro.vue.pojo.vo.UserInfoVo;
import com.shiro.vue.pojo.vo.UserVo;
import com.shiro.vue.service.IUserService;
import com.shiro.vue.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-11
 * Time: 14:56
 */
@RestController
@RequestMapping(value = "UserApi/v1")
public class UserApi extends BaseApi {

    @Autowired
    private IUserService userService;

    /**
     * 查询用户
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/findUserByPage", method = RequestMethod.GET)
    public Object findUserByPage(ParamsDto dto) {
        Page<UserDto> page = new Page<>(dto.getStartPage(), dto.getPageSize());
        dto.setType(EnumRoleType.USER.getValue());
        List<UserDto> list = userService.findUserByPage(page, dto);
        return ResultUtil.result(EnumCode.OK.getValue(), "请求成功", list, page.getTotal());
    }

    /**
     * 添加用户
     *
     * @param userVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public Object addUser(@Valid UserVo userVo, BindingResult bindingResult) {
        return userService.addUser(userVo);
    }

    /**
     * 批量删除用户
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/delUsers", method = RequestMethod.POST)
    public Object delUsers(ParamsDto dto) {
        if (null == dto.getIds() || dto.getIds().length == 0) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        return userService.delUsers(dto.getIds());
    }

    /**
     * 修改用户状态
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/editUserStatus", method = RequestMethod.POST)
    public Object editUserStatus(ParamsDto dto) {
        if (StringUtils.isEmpty(dto.getId()) || null == dto.getType()) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        return userService.editUserStatus(dto.getId(), dto.getType());
    }

    @RequestMapping(value = "editUserInfo", method = RequestMethod.POST)
    public Object editUserInfo(UserInfoVo vo) {
        return userService.editUserInfo(vo);
    }
}
