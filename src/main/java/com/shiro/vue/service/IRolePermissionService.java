package com.shiro.vue.service;

import com.baomidou.mybatisplus.service.IService;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.dto.RolePermisDto;
import com.shiro.vue.pojo.entity.RolePermission;
import com.shiro.vue.pojo.vo.RolePermisVo;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-12
 * Time: 14:02
 */
public interface IRolePermissionService extends IService<RolePermission> {

    /**
     * 给角色添加权限
     *
     * @param vo
     * @return
     */
    Object addRolesPermis(RolePermisVo vo);

    /**
     * 根据角色查询角色权限
     *
     * @param dto
     * @return
     */
    List<RolePermission> findRolesPermisByRole(ParamsDto dto);

    /**
     * 根据角色id查询记录数
     *
     * @param roleId
     * @return
     */
    Integer findCountByRole(String roleId, String url);

    /**
     * 根据父id\角色id查询角色菜单
     *
     * @param fatherId
     * @param rid
     * @return
     */
    List<RolePermisDto> findRolesPermisByFatherId(String fatherId, String rid);

}
