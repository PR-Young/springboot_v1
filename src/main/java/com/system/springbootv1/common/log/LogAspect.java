package com.system.springbootv1.common.log;

import com.system.springbootv1.common.shiro.ShiroUtils;
import com.system.springbootv1.model.SysLog;
import com.system.springbootv1.model.SysUser;
import com.system.springbootv1.service.SysLogService;
import com.system.springbootv1.utils.ServletUtils;
import com.system.springbootv1.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: yy 2020/01/27
 **/
@Aspect
@Component
@EnableAsync
public class LogAspect {

    @Resource
    SysLogService sysLogService;

    @Pointcut("@annotation(com.system.springbootv1.common.log.Log)")
    public void logPointCut() {
    }

    @Pointcut("execution(* com.system.springbootv1.controller.*.*(..))")
    public void logPointCutController() {
    }

    @Pointcut("execution(* com.system.springbootv1.service.*.*(..))")
    public void logPointCutService() {
    }

    @AfterReturning("logPointCutController()")
    public void doBefore(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    @AfterThrowing(value = "logPointCutController()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    @Async
    public void handleLog(JoinPoint joinPoint, Exception e) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Log annotation = method.getAnnotation(Log.class);
            if (null != annotation) {
                String value = annotation.value();
            }
            String url = ServletUtils.getRequest().getRequestURI();
            String ip = getRealIp(ServletUtils.getRequest());
            SysLog sysLog = new SysLog();
            SysUser currentUser = ShiroUtils.getUser();
            if (null != currentUser) {
                sysLog.setHostIp(ip);
                sysLog.setUrl(url);
                sysLog.setUserName(currentUser.getUserName());
                if (null != e) {
                    sysLog.setNotes(StringUtils.substring(e.getMessage(), 0, 2000));
                }
                sysLogService.insert(sysLog);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

}
