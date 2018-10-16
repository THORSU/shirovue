package com.shiro.vue.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.dto.PermisDto;
import com.shiro.vue.pojo.entity.Permission;
import com.shiro.vue.pojo.vo.PermisVo;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-12
 * Time: 14:11
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 查询菜单
     *
     * @param page
     * @param dto
     * @return
     */
    List<Permission> findPermissionByPage(Page<Permission> page, ParamsDto dto);

    /**
     * 新增菜单
     *
     * @param vo
     * @return
     */
    Object addPermissions(PermisVo vo);

    /**
     * 删除菜单
     *
     * @param ids
     * @return
     */
    Object delPermis(String[] ids);

    /**
     * 根据url查询记录
     *
     * @param requestUrl
     * @return
     */
    Integer findCountByUrl(String requestUrl);

    /**
     * 根据父级id查询上级菜单
     *
     * @param type
     * @return
     */
    List<Permission> findLastPermissionByType(String type);

    /**
     * 查询所有父级菜单绑定树形
     *
     * @return
     */
    List<PermisDto> findBasePermission();

}
