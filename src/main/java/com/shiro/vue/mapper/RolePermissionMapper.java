package com.shiro.vue.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.dto.RolePermisDto;
import com.shiro.vue.pojo.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-12
 * Time: 14:08
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * 根据角色查询角色权限
     *
     * @param dto
     * @return
     */
    List<RolePermission> findRolesPermisByRole(@Param("dto") ParamsDto dto);

    /**
     * 根据角色id查询记录数
     *
     * @param roleId
     * @return
     */
    Integer findCountByRole(@Param("roleId") String roleId, @Param("url") String url);

    /**
     * 根据父id\角色id查询角色菜单
     *
     * @param fatherId
     * @param rid
     * @return
     */
    List<RolePermisDto> findRolesPermisByFatherId(@Param("fatherId") String fatherId, @Param("rid") String rid);

}
