package com.shiro.vue.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.vue.pojo.dto.AttributeDetailDto;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.AttributeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-15
 * Time: 14:15
 */
public interface AttributeDetailMapper extends BaseMapper<AttributeDetail> {
    /**
     * @desc: 查询属性明细
     * @author: jwy
     * @date: 2017/12/26
     */
    List<AttributeDetail> findAttributeDetailByPage(Pagination page, @Param("dto") ParamsDto dto);

    /**
     * @desc: 根据属性id查询属性明细
     * @author: jwy
     * @date: 2017/12/27
     */
    List<AttributeDetailDto> findAttributeDetailByAttrId(@Param("attrId") String attrId);
}
