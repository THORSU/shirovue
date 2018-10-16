package com.shiro.vue.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-12
 * Time: 13:56
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 查询角色
     *
     * @param page
     * @param dto
     * @return
     */
    List<Role> findRoleByPage(Pagination page, @Param("dto") ParamsDto dto);

    /**
     * 绑定角色下拉框
     *
     * @return
     */
    List<Role> findAllRoles();
}
