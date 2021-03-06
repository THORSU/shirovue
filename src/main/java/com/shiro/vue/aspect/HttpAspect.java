package com.shiro.vue.aspect;

import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.api.base.BaseApi;
import com.shiro.vue.exception.MyException;
import com.shiro.vue.pojo.entity.OperatingRecord;
import com.shiro.vue.service.IOperatingRecordService;
import com.shiro.vue.service.IPermissionService;
import com.shiro.vue.service.IRolePermissionService;
import com.shiro.vue.utils.ResultUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * @author: QuincySu
 * Date: 2018-10-11
 * Time: 17:17
 */
@Aspect
@Component
public class HttpAspect extends BaseApi {
    @Autowired
    private IRolePermissionService rolePermissionService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IOperatingRecordService operatingRecordService;

    private final static Logger log = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.shiro.vue.api..*(..))")
    public void log() {

    }

    /**
     * @desc: 记录请求
     * @author: jwy
     * @date: 2017/12/15
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        shiroFilter(joinPoint);
    }

    /**
     * @desc: 响应请求
     * @author: jwy
     * @date: 2017/12/15
     */
    @After("log()")
    public void doAfter() {
        log.info("========================== ↓响应请求↓ ==========================");
    }

    /**
     * @desc: 打印返回值
     * @author: jwy
     * @date: 2017/12/15
     */
    @AfterReturning(returning = "obj", pointcut = "log()")
    public void doAfterReturnning(Object obj) {
        log.info("请求返回值：{}", obj);
    }


    /**
     * @desc: 统一参数验证处理
     * @author: jwy
     * @date: 2017/12/15
     */
    @Around("execution(* com.shiro.vue.api..*(..)) && args(..,bindingResult)")
    public Object doAround(ProceedingJoinPoint pjp, BindingResult bindingResult) throws Throwable {

        shiroFilter(pjp);

        Object retVal;
        if (bindingResult.hasErrors()) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), bindingResult.getFieldError().getDefaultMessage(), null);
        } else {
            retVal = pjp.proceed();
        }
        return retVal;
    }

    /**
     * @desc: 请求拦截器
     * @author: jwy
     * @date: 2017/12/28
     */
    public void shiroFilter(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String requestUrl = request.getRequestURI().replaceAll(request.getContextPath(), "");
        String remoteAddr = request.getRemoteAddr();
        String method = request.getMethod();
        String args = Arrays.toString(joinPoint.getArgs());

        log.info("========================== ↓收到请求↓ ==========================");
        log.info("请求url:{}", requestUrl);
        log.info("请求源ip:{}", remoteAddr);
        log.info("请求方式:{}", method);
        // log.info("请求方法:{}",joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()+ "()");
        log.info("请求参数:{}", args);
        log.info("getContextPath:{}", request.getContextPath());
        log.info("========================== ↑收到请求↑ ==========================");

        OperatingRecord or = new OperatingRecord();
        or.setRequestUrl(requestUrl);
        or.setRemoteAddr(remoteAddr);
        or.setMethod(method);
        or.setParams(args);
        or.setCreateTime(new Date());
        or.setUid(super.getUserId());

        Integer count = permissionService.findCountByUrl(requestUrl);
        if (count != 0) {
            String roleId = super.getRoleId();
            if (StringUtils.isEmpty(roleId)) {
                or.setFlag("授权不通过");
                operatingRecordService.insert(or);
                throw new MyException(ResultUtil.result(EnumCode.FORBIDDEN.getValue(), EnumCode.FORBIDDEN.getText()));
            }

            Integer row = rolePermissionService.findCountByRole(roleId, request.getRequestURI().replaceAll(request.getContextPath(), ""));
            if (row == 0 && !super.getRoleName().equals("admin")) {
                or.setFlag("授权不通过");
                operatingRecordService.insert(or);
                throw new MyException(ResultUtil.result(EnumCode.FORBIDDEN.getValue(), EnumCode.FORBIDDEN.getText()));
            }
        }
        or.setFlag("授权通过");
        operatingRecordService.insert(or);
    }
}
