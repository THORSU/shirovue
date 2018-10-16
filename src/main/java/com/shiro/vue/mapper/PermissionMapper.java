package com.shiro.vue.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.dto.PermisDto;
import com.shiro.vue.pojo.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-12
 * Time: 14:17
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 查询菜单
     *
     * @param page
     * @param dto
     * @return
     */
    List<Permission> findPermissionByPage(Pagination page, @Param("dto") ParamsDto dto);

    /**
     * 根据url查询记录
     *
     * @param requestUrl
     * @return
     */
    Integer findCountByUrl(@Param("requestUrl") String requestUrl);

    /**
     * 查询所有父级菜单绑定下拉框
     *
     * @param type
     * @return
     */
    List<Permission> findLastPermissionByType(@Param("type") String type);

    /**
     * 查询所有父级菜单绑定树形
     *
     * @return
     */
    List<PermisDto> findBasePermission();

    /**
     * 根据父级id查询菜单
     *
     * @param fatherId
     * @return
     */
    List<PermisDto> findPermissionByFatherId(@Param("fatherId") String fatherId);
}
