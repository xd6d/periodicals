package com.example.filter;

import com.example.entity.User;
import com.example.service.UserService;
import com.example.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthorizedUserFilter")
public class AuthorizedUserFilter implements Filter {

    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) {
        userService = new UserServiceImpl();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null) {
            User user = userService.getUserByUsername(((User) userObject).getUsername());
            req.getSession().setAttribute("user", user);
            chain.doFilter(request, response);
        } else
            resp.sendRedirect("/login");
    }
}
