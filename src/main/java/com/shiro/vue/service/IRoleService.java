package com.shiro.vue.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.Role;
import com.shiro.vue.pojo.vo.RoleVo;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-12
 * Time: 13:52
 */
public interface IRoleService extends IService<Role> {

    /**
     * 查询角色
     *
     * @param page
     * @param dto
     * @return
     */
    List<Role> findRoleByPage(Page<Role> page, ParamsDto dto);

    /**
     * 添加角色
     *
     * @param vo
     * @return
     */
    Object addRoles(RoleVo vo);

    /**
     * 批量删除角色
     *
     * @param ids
     * @return
     */
    Object delRole(String[] ids);

    /**
     * 绑定角色下拉框
     *
     * @return
     */
    List<Role> findAllRoles();
}
