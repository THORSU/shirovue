package com.shiro.vue.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.mapper.AttributeMapper;
import com.shiro.vue.pojo.dto.AttributeDetailDto;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.Attribute;
import com.shiro.vue.service.IAttributeDetailService;
import com.shiro.vue.service.IAttributeService;
import com.shiro.vue.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-15
 * Time: 14:38
 */
@Service
public class AttributeService extends ServiceImpl<AttributeMapper, Attribute> implements IAttributeService {

    @Autowired
    private IAttributeDetailService attributeDetailService;

    @Override
    public List<Attribute> findAttributesByPage(Page<Attribute> page, ParamsDto dto) {
        return super.baseMapper.findAttributesByPage(page, dto);
    }

    @Override
    public Object addAttributes(Attribute attribute) {
        super.baseMapper.insert(attribute);
        return ResultUtil.result(EnumCode.OK.getValue(), "新增成功");
    }

    @Override
    @Transactional
    public Object delAttributes(String[] ids) {
        for (String id : ids) {
            super.baseMapper.deleteById(id);
            List<AttributeDetailDto> list = attributeDetailService.findAttributeDetailByAttrId(id);
            if (null != list && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    attributeDetailService.deleteById(list.get(i).getId());
                }
            }
        }
        return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
    }
}
