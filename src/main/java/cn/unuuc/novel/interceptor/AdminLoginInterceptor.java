package cn.unuuc.novel.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminLoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 遇到探测请求直接放行
        if (request.getMethod().equals("OPTION"))
            return true;

        if (request.getSession().getAttribute("loginId") != null){
            return true;
        }
        response.sendRedirect("/admin/login");
        return false;
    }

}
