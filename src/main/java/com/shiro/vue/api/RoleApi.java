package com.shiro.vue.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.Role;
import com.shiro.vue.pojo.entity.RolePermission;
import com.shiro.vue.pojo.vo.RolePermisVo;
import com.shiro.vue.pojo.vo.RoleVo;
import com.shiro.vue.service.IRolePermissionService;
import com.shiro.vue.service.IRoleService;
import com.shiro.vue.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-12
 * Time: 13:41
 */
@RestController
@RequestMapping(value = "RoleApi/v1")
public class RoleApi {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    /**
     * 查询角色
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/findRoleByPage", method = RequestMethod.GET)
    public Object findRoleByPage(ParamsDto dto) {
        Page<Role> page = new Page<>(dto.getStartPage(), dto.getPageSize());
        List<Role> list = roleService.findRoleByPage(page, dto);
        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), list, page.getTotal());
    }

    /**
     * 新增角色
     *
     * @param vo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public Object addRoles(@Valid RoleVo vo, BindingResult bindingResult) {
        return roleService.addRoles(vo);
    }

    /**
     * 批量删除角色
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "delRoles", method = RequestMethod.POST)
    public Object delRoles(ParamsDto dto) {
        if (null == dto.getIds() || dto.getIds().length == 0) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        return roleService.delRole(dto.getIds());
    }

    /**
     * 绑定角色下拉框
     *
     * @param vo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/findAllRoles", method = RequestMethod.GET)
    public Object findAllRoles(RolePermisVo vo, BindingResult bindingResult) {
        List<Role> list = roleService.findAllRoles();
        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), list);
    }

    /**
     * 给用户添加角色
     *
     * @param vo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addRolesPermis", method = RequestMethod.POST)
    public Object addRolesPermis(RolePermisVo vo, BindingResult bindingResult) {
        return rolePermissionService.addRolesPermis(vo);
    }

    /**
     * 根据角色查询角色权限
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/findRolesPermisByRole", method = RequestMethod.GET)
    public Object findRolesPermisByRole(ParamsDto dto) {
        if (null == dto.getId()) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        List<RolePermission> list = rolePermissionService.findRolesPermisByRole(dto);
        String[] arr = new String[list.size()];
        for (int i = 0, j = list.size(); i < j; i++) {
            arr[i] = list.get(i).getPid();
        }

        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), arr);
    }
}
