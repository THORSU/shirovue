package com.shiro.vue.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.mapper.PermissionMapper;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.dto.PermisDto;
import com.shiro.vue.pojo.entity.Permission;
import com.shiro.vue.pojo.vo.PermisVo;
import com.shiro.vue.service.IPermissionService;
import com.shiro.vue.utils.ResultUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-12
 * Time: 14:15
 */
@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    @Override
    public List<Permission> findPermissionByPage(Page<Permission> page, ParamsDto dto) {
        return super.baseMapper.findPermissionByPage(page, dto);
    }

    @Override
    public Object addPermissions(PermisVo vo) {
        Permission p = new Permission();
        p.setName(vo.getName());
        p.setUrl(vo.getUrl());
        p.setType(vo.getType());
        p.setFatherId(vo.getLastId());
        p.setCreateTime(new Date());
        p.setCreater(vo.getUserName());
        super.baseMapper.insert(p);
        return ResultUtil.result(EnumCode.OK.getValue(), "新增成功");
    }

    @Override
    public Object delPermis(String[] ids) {
        for (String id : ids) {
            super.baseMapper.deleteById(id);
        }
        return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
    }

    @Override
    public Integer findCountByUrl(String requestUrl) {
        return super.baseMapper.findCountByUrl(requestUrl);
    }

    @Override
    public List<Permission> findLastPermissionByType(String type) {
        return super.baseMapper.findLastPermissionByType(type);
    }

    @Override
    public List<PermisDto> findBasePermission() {
        List<PermisDto> list = super.baseMapper.findBasePermission();
        if (null != list && !list.isEmpty()) {
            for (int i = 0, j = list.size(); i < j; i++) {
                List<PermisDto> children = super.baseMapper.findPermissionByFatherId(list.get(i).getId());
                if (null != children && !children.isEmpty()) {
                    list.get(i).setChildren((ArrayList<PermisDto>) children);
                    for (int i1 = 0, j1 = children.size(); i1 < j1; i1++) {
                        List<PermisDto> children1 = super.baseMapper.findPermissionByFatherId(children.get(i1).getId());
                        if (null != children1 && !children1.isEmpty()) {
                            children.get(i1).setChildren((ArrayList<PermisDto>) children1);
                        }
                    }
                }
            }
        }
        return list;
    }
}
