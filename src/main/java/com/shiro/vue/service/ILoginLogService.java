package com.shiro.vue.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.LoginLog;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-11
 * Time: 11:47
 */
public interface ILoginLogService extends IService<LoginLog> {
    /**
     * 查询登录次数
     *
     * @param id
     * @return
     */
    Integer findMaxLoginTatalByUserId(String id);


    /**
     * 用户登录日志
     *
     * @param dto 参数dto
     * @return
     */
    List<LoginLog> findUserLoginLogByPage(Page<LoginLog> page, ParamsDto dto);

    /**
     * 统计登陆
     *
     * @author: jwy
     * @date: 2018/1/11
     */
    Object findUserLoginTotal();
}
