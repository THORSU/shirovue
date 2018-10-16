package com.shiro.vue.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.Attribute;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-15
 * Time: 14:36
 */
public interface IAttributeService extends IService<Attribute> {

    /**
     * 查询属性
     *
     * @param page
     * @param dto
     * @return
     */
    List<Attribute> findAttributesByPage(Page<Attribute> page, ParamsDto dto);

    /**
     * 新增属性
     *
     * @param attribute
     * @return
     */
    Object addAttributes(Attribute attribute);

    /**
     * 删除属性
     *
     * @param ids
     * @return
     */
    Object delAttributes(String[] ids);
}
