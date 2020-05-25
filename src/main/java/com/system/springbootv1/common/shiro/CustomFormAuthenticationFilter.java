package com.system.springbootv1.common.shiro;

import com.alibaba.fastjson.JSONObject;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.utils.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @description:
 * @author: yy 2020/05/04
 **/
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
    /**
     * 屏蔽OPTIONS请求
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean accessAllowed = super.isAccessAllowed(request, response, mappedValue);
        if (!accessAllowed) {
            // 判断请求是否是options请求
            String method = WebUtils.toHttp(request).getMethod();
            if (StringUtils.equalsIgnoreCase("OPTIONS", method)) {
                return true;
            }
        }
        return accessAllowed;
    }

    /**
     * 解决未登录302问题
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            // 返回固定的JSON串
            WebUtils.toHttp(response).setContentType("application/json; charset=utf-8");
            WebUtils.toHttp(response).getWriter().print(JSONObject.toJSONString(BaseResult.unLogin()));
            return false;
        }
    }

}
