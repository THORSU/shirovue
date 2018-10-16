package com.shiro.vue.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.Attribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-15
 * Time: 14:39
 */
public interface AttributeMapper extends BaseMapper<Attribute> {
    /**
     * @desc: 查询属性
     * @author: jwy
     * @date: 2017/12/26
     */
    List<Attribute> findAttributesByPage(Pagination page, @Param("dto") ParamsDto dto);
}
