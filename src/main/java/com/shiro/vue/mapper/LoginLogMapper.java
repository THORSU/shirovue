package com.shiro.vue.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.LoginLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-15
 * Time: 8:51
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 查询登录次数
     *
     * @param id
     * @return
     */
    Integer findMaxLoginTatalByUserId(@Param("id") String id);

    /**
     * 用户登录日志
     *
     * @param dto 参数dto
     * @return
     */
    List<LoginLog> findUserLoginLogByPage(Pagination page, @Param("dto") ParamsDto dto);

    /**
     * 查询用户登录总次数
     *
     * @author: jwy
     * @date: 2018/1/11
     */
    List<LoginLog> findUserLoginTotal();
}
