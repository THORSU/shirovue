package com.shiro.vue.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.vue.pojo.dto.AttributeDetailDto;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.AttributeDetail;
import com.shiro.vue.pojo.vo.AttributeDetailVo;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-15
 * Time: 14:11
 */
public interface IAttributeDetailService extends IService<AttributeDetail> {

    /**
     * 查询属性明细
     *
     * @param page
     * @param dto
     * @return
     */
    List<AttributeDetail> findAttributeDetailByPage(Page<AttributeDetail> page, ParamsDto dto);

    /**
     * 新增属性明细
     *
     * @param attributeDetail
     * @return
     */
    Object addAttributeDetail(AttributeDetailVo attributeDetail);

    /**
     * 根据属性id查询属性明细
     *
     * @param attrId
     * @return
     */
    List<AttributeDetailDto> findAttributeDetailByAttrId(String attrId);

    /**
     * 删除属性明细
     *
     * @param ids
     * @return
     */
    Object delAttributeDetails(String[] ids);
}
