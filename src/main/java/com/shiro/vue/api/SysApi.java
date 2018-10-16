package com.shiro.vue.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.pojo.dto.AttributeDetailDto;
import com.shiro.vue.pojo.dto.ParamsDto;
import com.shiro.vue.pojo.entity.Attribute;
import com.shiro.vue.pojo.entity.AttributeDetail;
import com.shiro.vue.pojo.entity.LoginLog;
import com.shiro.vue.pojo.entity.OperatingRecord;
import com.shiro.vue.pojo.vo.AttributeDetailVo;
import com.shiro.vue.service.IAttributeDetailService;
import com.shiro.vue.service.IAttributeService;
import com.shiro.vue.service.ILoginLogService;
import com.shiro.vue.service.IOperatingRecordService;
import com.shiro.vue.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-15
 * Time: 13:59
 */
@RestController
@RequestMapping(value = "SysApi/v1")
public class SysApi {
    @Autowired
    private IAttributeService attributeService;
    @Autowired
    private IAttributeDetailService attributeDetailService;
    @Autowired
    private IOperatingRecordService operatingRecordService;
    @Autowired
    private ILoginLogService loginLogService;


    /**
     * 查询属性
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/findAttributesByPage", method = RequestMethod.GET)
    public Object findAttributesByPage(ParamsDto dto) {

        Page<Attribute> page = new Page<Attribute>(dto.getStartPage(), dto.getPageSize());
        List<Attribute> list = attributeService.findAttributesByPage(page, dto);
        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), list, page.getTotal());
    }

    /**
     * 查询属性明细
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/findAttributesDetailByPage", method = RequestMethod.GET)
    public Object findAttributesDetailByPage(ParamsDto dto) {

        Page<AttributeDetail> page = new Page<AttributeDetail>(dto.getStartPage(), dto.getPageSize());
        List<AttributeDetail> list = attributeDetailService.findAttributeDetailByPage(page, dto);
        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), list, page.getTotal());
    }

    /**
     * 新增属性
     *
     * @param attribute
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addAttributes", method = RequestMethod.POST)
    public Object addAttributes(@Valid Attribute attribute, BindingResult bindingResult) {
        return attributeService.addAttributes(attribute);
    }

    /**
     * 新增属性明细
     *
     * @param vo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addAttributeDetail", method = RequestMethod.POST)
    public Object addAttributeDetail(@Valid AttributeDetailVo vo, BindingResult bindingResult) {
        return attributeDetailService.addAttributeDetail(vo);
    }

    /**
     * 删除属性
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/delAttributes", method = RequestMethod.POST)
    public Object delAttributes(ParamsDto dto) {
        if (null == dto.getIds() || dto.getIds().length == 0) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        return attributeService.delAttributes(dto.getIds());
    }

    /**
     * 删除属性明细
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/delAttributeDetails", method = RequestMethod.POST)
    public Object delAttributeDetails(ParamsDto dto) {
        if (null == dto.getIds() || dto.getIds().length == 0) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        return attributeDetailService.delAttributeDetails(dto.getIds());
    }

    /**
     * 根据属性id查询属性
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/findAttributeDetailByAttrId", method = RequestMethod.GET)
    public Object selAttributeDetailsByAttrId(ParamsDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        List<AttributeDetailDto> list = attributeDetailService.findAttributeDetailByAttrId(dto.getId());
        if (null == list || list.isEmpty()) {
            return ResultUtil.result(EnumCode.GONE.getValue(), "没有记录");
        }
        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), list);
    }

    /**
     * 查询操作记录
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/findOperatingRecordByPage", method = RequestMethod.GET)
    public Object findOperatingRecordByPage(ParamsDto dto) {

        Page<OperatingRecord> page = new Page<>(dto.getStartPage(), dto.getPageSize());
        List<OperatingRecord> list = operatingRecordService.findOperatingRecordByPage(page, dto);
        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), list, page.getTotal());

    }


    /**
     * 用户登录日志
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/findUserLoginLogByPage", method = RequestMethod.GET)
    public Object findUserLoginLogByPage(ParamsDto dto) {

        Page<LoginLog> page = new Page<LoginLog>(dto.getStartPage(), dto.getPageSize());
        List<LoginLog> list = loginLogService.findUserLoginLogByPage(page, dto);
        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), list, page.getTotal());

    }

    /**
     * 统计用户登录
     *
     * @return
     */
    @RequestMapping(value = "/findUserLoginTotal", method = RequestMethod.GET)
    public Object findUserLoginTotal() {
        return loginLogService.findUserLoginTotal();
    }


    /**
     * 访问统计
     *
     * @return
     */
    @RequestMapping(value = "/findUserReqTotal", method = RequestMethod.GET)
    public Object findUserReqTotal() {
        return operatingRecordService.findUserReqTotal();
    }
}
