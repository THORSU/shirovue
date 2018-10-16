package com.shiro.vue.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.mapper.AttributeDetailMapper;
import com.shiro.vue.pojo.dto.AttributeDetailDto;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.AttributeDetail;
import com.shiro.vue.pojo.vo.AttributeDetailVo;
import com.shiro.vue.service.IAttributeDetailService;
import com.shiro.vue.utils.ResultUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-15
 * Time: 14:14
 */
@Service
public class AttributeDetailService extends ServiceImpl<AttributeDetailMapper, AttributeDetail> implements IAttributeDetailService {
    @Override
    public List<AttributeDetail> findAttributeDetailByPage(Page<AttributeDetail> page, ParamsDto dto) {
        return super.baseMapper.findAttributeDetailByPage(page, dto);
    }

    @Override
    public Object addAttributeDetail(AttributeDetailVo attributeDetail) {
        AttributeDetail attributeDetail1 = new AttributeDetail();
        attributeDetail1.setAttrId(attributeDetail.getId());
        attributeDetail1.setName(attributeDetail.getName());
        Integer rows = super.baseMapper.insert(attributeDetail1);
        if (rows > 0) {
            return ResultUtil.result(EnumCode.OK.getValue(), "新增成功");
        }
        return ResultUtil.result(EnumCode.INTERNAL_SERVER_ERROR.getValue(), "新增失败");
    }

    @Override
    public List<AttributeDetailDto> findAttributeDetailByAttrId(String attrId) {
        return super.baseMapper.findAttributeDetailByAttrId(attrId);
    }

    @Override
    public Object delAttributeDetails(String[] ids) {
        for (String id : ids) {
            super.baseMapper.deleteById(id);
        }

        return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
    }

}
