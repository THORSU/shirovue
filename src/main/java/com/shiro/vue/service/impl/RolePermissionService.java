package com.shiro.vue.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.mapper.RolePermissionMapper;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.dto.RolePermisDto;
import com.shiro.vue.pojo.entity.RolePermission;
import com.shiro.vue.pojo.vo.RolePermisVo;
import com.shiro.vue.service.IRolePermissionService;
import com.shiro.vue.utils.ResultUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: QuincySu
 * Date: 2018-10-12
 * Time: 14:05
 */
@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

    @Override
    public Object addRolesPermis(RolePermisVo vo) {

        String roleId = vo.getRoleId();
        String[] permisids = vo.getPermiIds() == null ? new String[0] : vo.getPermiIds();

        Map<String, Object> map = new HashMap<>();
        map.put("rid", roleId);
        super.baseMapper.deleteByMap(map);

        for (int i = 0, j = permisids.length; i < j; i++) {
            RolePermission rp = new RolePermission();
            rp.setRid(roleId);
            rp.setPid(permisids[i]);
            super.baseMapper.insert(rp);

        }
        return ResultUtil.result(EnumCode.OK.getValue(), "保存成功");
    }

    @Override
    public List<RolePermission> findRolesPermisByRole(ParamsDto dto) {
        return super.baseMapper.findRolesPermisByRole(dto);
    }

    @Override
    public Integer findCountByRole(String roleId, String url) {
        return super.baseMapper.findCountByRole(roleId, url);
    }

    @Override
    public List<RolePermisDto> findRolesPermisByFatherId(String fatherId, String rid) {
        return super.baseMapper.findRolesPermisByFatherId(fatherId, rid);
    }

}
