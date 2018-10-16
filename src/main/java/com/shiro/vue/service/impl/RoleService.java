package com.shiro.vue.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.Enum.EnumRoleType;
import com.shiro.vue.mapper.RoleMapper;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.Role;
import com.shiro.vue.pojo.vo.RoleVo;
import com.shiro.vue.service.IRoleService;
import com.shiro.vue.utils.ResultUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-12
 * Time: 13:54
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Override
    public List<Role> findRoleByPage(Page<Role> page, ParamsDto dto) {
        return super.baseMapper.findRoleByPage(page, dto);
    }

    @Override
    public Object addRoles(RoleVo vo) {
        Role r = new Role();
        r.setName(vo.getName());
        r.setDescription(vo.getDescription());
        //默认添加非超级管理员用户
        r.setType(EnumRoleType.USER.getValue());
        super.baseMapper.insert(r);
        return ResultUtil.result(EnumCode.OK.getValue(), "新增成功");
    }

    @Override
    public Object delRole(String[] ids) {
        for (String id : ids) {
            super.baseMapper.deleteById(id);
        }
        return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
    }

    @Override
    public List<Role> findAllRoles() {
        return super.baseMapper.findAllRoles();
    }
}
