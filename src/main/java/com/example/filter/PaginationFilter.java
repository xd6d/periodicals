package com.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Processes each request due to the following rules:
 * <ol>
 *     <li>If request does not have page parameter, required for pagination, this filter sets it equal to 1.</li>
 *     <li>If request has page parameter, this filter validates it.
 *     In case the parameter is not valid - it is set to 1, else left without changes.</li>
 * </ol>
 */
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
