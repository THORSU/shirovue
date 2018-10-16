package com.shiro.vue.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.mapper.LoginLogMapper;
import com.shiro.vue.pojo.dto.LoginTotalDto;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.LoginLog;
import com.shiro.vue.service.ILoginLogService;
import com.shiro.vue.utils.ResultUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-15
 * Time: 8:51
 */
@Service
public class LoginLogService extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {
    @Override
    public Integer findMaxLoginTatalByUserId(String id) {
        return super.baseMapper.findMaxLoginTatalByUserId(id);
    }

    @Override
    public List<LoginLog> findUserLoginLogByPage(Page<LoginLog> page, ParamsDto dto) {
        return super.baseMapper.findUserLoginLogByPage(page, dto);
    }

    @Override
    public Object findUserLoginTotal() {
        List<LoginLog> loignList = super.baseMapper.findUserLoginTotal();
        String[] strName = new String[loignList.size()];
        int[] itotal = new int[loignList.size()];
        for (int i = 0, j = loignList.size(); i < j; i++) {
            strName[i] = loignList.get(i).getUserName();
            itotal[i] = loignList.get(i).getLoginTotal();
        }
        List<LoginTotalDto> listLoginTotal = new ArrayList<>();
        LoginTotalDto loginTotalDto = new LoginTotalDto();
        loginTotalDto.setName(strName);
        loginTotalDto.setTotal(itotal);
        listLoginTotal.add(loginTotalDto);
        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), listLoginTotal);
    }
}
