package com.shiro.vue.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.dto.ReqTotalDto;
import com.shiro.vue.pojo.entity.OperatingRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-15
 * Time: 14:43
 */
public interface OperatingRecordMapper extends BaseMapper<OperatingRecord> {
    /**
     * 查询操作记录
     *
     * @param page 分页
     * @param dto  参数dto
     * @return
     */
    List<OperatingRecord> findOperatingRecordByPage(Pagination page, @Param("dto") ParamsDto dto);

    /**
     * 统计所有 请求
     *
     * @author: jwy
     * @date: 2018/1/11
     */
    List<ReqTotalDto> findAllRequstCount();
}
