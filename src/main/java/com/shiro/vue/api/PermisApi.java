package com.shiro.vue.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.api.base.BaseApi;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.dto.PermisDto;
import com.shiro.vue.pojo.entity.Permission;
import com.shiro.vue.pojo.vo.PermisVo;
import com.shiro.vue.service.IPermissionService;
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
 * Date: 2018-10-15
 * Time: 8:48
 */
@RestController
@RequestMapping(value = "PermisApi/v1")
public class PermisApi extends BaseApi {

    @Autowired
    private IPermissionService permissionService;

    /**
     * 查询菜单
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/findPermissionByPage", method = RequestMethod.GET)
    public Object findPermissionByPage(ParamsDto dto) {
        Page<Permission> page = new Page<>(dto.getStartPage(), dto.getPageSize());
        List<Permission> list = permissionService.findPermissionByPage(page, dto);
        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), list, page.getTotal());
    }

    /**
     * 新增菜单
     *
     * @param vo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addPermissions", method = RequestMethod.POST)
    public Object addPermissions(@Valid PermisVo vo, BindingResult bindingResult) {
        vo.setUserName(super.getUserName());
        vo.setUserId(super.getUserId());
        return permissionService.addPermissions(vo);
    }

    /**
     * 删除菜单
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/delPermis", method = RequestMethod.POST)
    public Object delPermis(ParamsDto dto) {
        if (null == dto.getIds() || dto.getIds().length == 0) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        return permissionService.delPermis(dto.getIds());
    }


    /**
     * 查询父级菜单为0的所有菜单
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/findLastPermissionByType", method = RequestMethod.GET)
    public Object findAllBasePermission(String type) {
        List<Permission> list = permissionService.findLastPermissionByType(type);
        if (null == list || list.isEmpty()) {
            return ResultUtil.result(EnumCode.GONE.getValue(), "没有记录");
        }
        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), list);
    }

    /**
     * 绑定树形菜单
     *
     * @return
     */
    @RequestMapping(value = "/findBasePermission", method = RequestMethod.GET)
    public Object findBasePermission() {
        List<PermisDto> list = permissionService.findBasePermission();
        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), list);
    }
}
