package com.bjpowernode.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("----进入过滤器----");
        //设置post请求的字符集
        req.setCharacterEncoding("utf-8");
        //设置响应的字符集
        resp.setContentType("text/html;charset=utf-8");
        //放行请求
        chain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
