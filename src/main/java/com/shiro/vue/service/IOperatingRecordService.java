package com.shiro.vue.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.OperatingRecord;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-15
 * Time: 14:40
 */
public interface IOperatingRecordService extends IService<OperatingRecord> {

    /**
     * 查询操作记录
     *
     * @param page
     * @param dto
     * @return
     */
    List<OperatingRecord> findOperatingRecordByPage(Page<OperatingRecord> page, ParamsDto dto);

    /**
     * 访问统计
     *
     * @return
     */
    Object findUserReqTotal();
}
