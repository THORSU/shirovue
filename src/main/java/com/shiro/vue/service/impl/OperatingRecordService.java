package com.shiro.vue.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.mapper.OperatingRecordMapper;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.dto.ReqTotalDto;
import com.shiro.vue.pojo.dto.RequstOprDto;
import com.shiro.vue.pojo.entity.OperatingRecord;
import com.shiro.vue.service.IOperatingRecordService;
import com.shiro.vue.utils.ResultUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-15
 * Time: 14:41
 */
@Service
public class OperatingRecordService extends ServiceImpl<OperatingRecordMapper, OperatingRecord> implements IOperatingRecordService {
    @Override
    public List<OperatingRecord> findOperatingRecordByPage(Page<OperatingRecord> page, ParamsDto dto) {
        return super.baseMapper.findOperatingRecordByPage(page, dto);
    }

    @Override
    public Object findUserReqTotal() {
        List<ReqTotalDto> reqList = super.baseMapper.findAllRequstCount();
        List<RequstOprDto> reqData1 = new ArrayList<>();
        List<RequstOprDto> reqData2 = new ArrayList<>();
        String[] arrNa = new String[reqList.size()];
        RequstOprDto reo = null;
        for (int i = 0, j = reqList.size(); i < j; i++) {
            if (reqList.get(i).getType().equals(1)) {
                // Method
                reo = new RequstOprDto();
                reo.setName(reqList.get(i).getNa());
                reo.setValue(reqList.get(i).getTotal());
                reqData1.add(reo);
            } else {
                // 源
                reo = new RequstOprDto();
                reo.setName(reqList.get(i).getNa());
                reo.setValue(reqList.get(i).getTotal());
                reqData2.add(reo);
            }
            arrNa[i] = reqList.get(i).getNa();
        }

        RequstOprDto r = new RequstOprDto();
        List<RequstOprDto> reqData3 = new ArrayList<>();
        r.setMetlist((ArrayList) reqData1);
        r.setUsrlist((ArrayList) reqData2);
        r.setArrName(arrNa);
        reqData3.add(r);

        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), reqData3);
    }
}
