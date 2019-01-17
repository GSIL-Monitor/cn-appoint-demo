/**
 * 
 */
package com.jd.appoint.store.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lizhihua11
 *
 */
public class RepeatSubmitInterceptor implements HandlerInterceptor {

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String method = request.getMethod();
        if (!"POST".equalsIgnoreCase(method) && !"PUT".equalsIgnoreCase(method) && !"DELETE".equalsIgnoreCase(method)) {
            return true;
        }

        String uri = request.getRequestURI().toLowerCase().trim();
        String token = request.getParameter(WebConstants.TOKEN);
        if (StringUtils.isBlank(token))
            token = request.getHeader(WebConstants.TOKEN);

        if (StringUtils.isNotBlank(token)) {
            HttpSession session = request.getSession(true);
            Map<String, String> tokenMap = (Map<String, String>) session.getAttribute(WebConstants.TOKEN);
            if (null == tokenMap) {
                tokenMap = new ConcurrentHashMap<String, String>();
                session.setAttribute(WebConstants.TOKEN, tokenMap);
            }

            String oldToken = tokenMap.get(uri);
            if (token.equals(oldToken)) {
                response.getOutputStream().write("重复提交".getBytes("UTF-8"));
                response.flushBuffer();
                return false;
            }
            tokenMap.put(uri, token);
        }

        return true;
    }

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     *
     **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // nogthing to do.
    }

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     *
     **/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // nogthing to do.
    }

}
