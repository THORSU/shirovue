package com.shiro.vue.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.dto.UserDto;
import com.shiro.vue.pojo.dto.UserInfoDto;
import com.shiro.vue.pojo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-11
 * Time: 9:50
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * @desc: 登陆验证
     */
    List<UserInfoDto> checkUser(@Param("name") String name, @Param("pass") String pass);

    /**
     * @param page 分页
     * @param dto  参数dto
     * @desc: 查询用户
     */
    List<UserDto> findUserByPage(Pagination page, @Param("dto") ParamsDto dto);
}
