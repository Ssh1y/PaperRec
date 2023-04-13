package com.ssh1y.paperrec.component;

import com.ssh1y.paperrec.utils.JwtHelper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenweihong
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {


    /**
     * 拦截请求，判断是否有token，如果有则判断token是否过期
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return boolean
     * @throws Exception Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 排除 OPTIONS 请求
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || JwtHelper.isExpiration(token)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized access");
            return false;
        }
        return true;
    }
}
