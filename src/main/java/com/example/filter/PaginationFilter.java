package com.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "PaginationFilter")
public class PaginationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        int page = 1;
        String pageParam = req.getParameter("page");
        if (pageParam != null && pageParam.matches("^[\\d]+$"))
            page = Integer.parseInt(pageParam);
        req.setAttribute("page", page);
        chain.doFilter(request, response);
    }
}
