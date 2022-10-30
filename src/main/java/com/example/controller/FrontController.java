package com.example.controller;

import com.example.entity.User;
import com.example.service.PublicationService;
import com.example.service.TopicService;
import com.example.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

/**
 * The only controller. Handles all HTTP requests.
 */
@WebServlet(value = {"/"}, name = "FrontController")
public class FrontController extends HttpServlet {
    TopicService topicService;
    PublicationService publicationService;
    UserService userService;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        topicService = (TopicService) servletContext.getAttribute("topicService");
        publicationService = (PublicationService) servletContext.getAttribute("publicationService");
        userService = (UserService) servletContext.getAttribute("userService");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        switch (requestURI) {
            case "/admin/edit-publication":
                editPublication(req, resp);
                break;
            case "/admin/delete":
                delete(req, resp);
                break;
            case "/admin/edit":
                edit(req, resp);
                break;
            case "/admin/new":
                newPublication(req, resp);
                break;
            case "/admin/add":
                add(req, resp);
                break;
            case "/admin/unblock":
                unblock(req, resp);
                break;
            case "/admin/block":
                block(req, resp);
                break;
            case "/admin/users":
                users(req, resp);
                break;
            case "/user/subscribe":
                subscribe(req, resp);
                break;
            case "/user/subscribed":
                subscribed(req, resp);
                break;
            case "/user/add-balance":
                addBalance(req, resp);
                break;
            case "/user/replenish":
                replenish(req, resp);
                break;
            case "/user/account":
                account(req, resp);
                break;
            case "/logout":
                logout(req, resp);
                break;
            case "/enter-account":
                enterAccount(req, resp);
                break;
            case "/login":
                login(req, resp);
                break;
            case "/create-user":
                createUser(req, resp);
                break;
            case "/registration":
                registration(req, resp);
                break;
            case "/locale":
                locale(req, resp);
                break;
            default:
                index(req, resp);
        }
    }

    protected void locale(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lang = req.getParameter("locale");
        Cookie cookie = new Cookie("locale", "en");
        if (lang.equals("uk"))
            cookie.setValue(lang);
        cookie.setMaxAge(-1);
        resp.addCookie(cookie);
        resp.sendRedirect("/");
    }

    protected void editPublication(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String publicationIdSrt = req.getParameter("publicationId");
        String title = req.getParameter("title");
        String titleUK = req.getParameter("titleUK");
        String price = req.getParameter("price");
        String[] topics = req.getParameterValues("topic");
        publicationService.edit(publicationIdSrt, title, titleUK, price, topics);
        resp.sendRedirect("/");
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String publicationIdStr = req.getParameter("publicationId");
        publicationService.delete(publicationIdStr);
        resp.sendRedirect("/");
    }

    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String publicationIdStr = req.getParameter("publicationId");
        String locale = getLocale(req);
        try {
            req.setAttribute("publication", publicationService.getPublicationById(publicationIdStr));
            req.setAttribute("topics", topicService.provideTopics("name", locale));
            req.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            resp.sendRedirect("/");
        }
    }

    protected void newPublication(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");
        String titleUK = req.getParameter("titleUK");
        String price = req.getParameter("price");
        String[] topics = req.getParameterValues("topic");
        publicationService.createPublication(title, titleUK, price, topics);
        resp.sendRedirect("/");
    }

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String locale = getLocale(req);
        req.setAttribute("topics", topicService.provideTopics("name", locale));
        req.getRequestDispatcher("/WEB-INF/jsp/add.jsp").forward(req, resp);
    }

    protected void unblock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        userService.unblock(username);
        resp.sendRedirect("/admin/users");
    }

    protected void block(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        userService.block(username);
        resp.sendRedirect("/admin/users");
    }

    protected void users(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("users", userService.getAllUsers());
        req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
    }

    protected void subscribe(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        String publicationId = req.getParameter("publicationId");
        userService.subscribe(user, publicationId);
        resp.sendRedirect("/");
    }

    protected void subscribed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String topicParam = req.getParameter("topic");
        String sortName = req.getParameter("sort");
        String reversedName = req.getParameter("reversed");
        String search = req.getParameter("search");
        String locale = getLocale(req);

        req.setAttribute("publications", publicationService.provideUsersPublications(user, topicParam, search, sortName, reversedName, locale));
        req.setAttribute("topics", topicService.provideTopics("name", locale));
        req.getRequestDispatcher("/WEB-INF/jsp/subscribed.jsp").forward(req, resp);
    }

    protected void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicParam = req.getParameter("topic");
        String sortName = req.getParameter("sort");
        String reversedName = req.getParameter("reversed");
        String search = req.getParameter("search");
        String locale = getLocale(req);

        req.setAttribute("publications", publicationService.providePublications(topicParam, search, sortName, reversedName, locale));
        req.setAttribute("topics", topicService.provideTopics("name", locale));
        req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
    }

    private String getLocale(HttpServletRequest req) {
        String locale = "en";
        Cookie[] cookies = req.getCookies();
        if (cookies == null)
            return locale;
        for (Cookie cookie : cookies)
            if (cookie.getName().equals("locale")) {
                locale = cookie.getValue();
                break;
            }
        return locale;
    }

    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("user", null);
        resp.sendRedirect("/");
    }

    protected void addBalance(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object userObject = req.getSession().getAttribute("user");
        String replenishString = req.getParameter("replenish");

        try {
            userService.replenish((User) userObject, replenishString);
            resp.sendRedirect("/user/account");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/replenish.jsp").forward(req, resp);
        }
    }

    protected void replenish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/replenish.jsp").forward(req, resp);
    }

    protected void account(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/WEB-INF/jsp/account.jsp").forward(req, resp);
    }

    protected void enterAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter("email");
        String givenPassword = req.getParameter("password");

        try {
            User user = userService.enterAccount(email, givenPassword);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/user/account");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    protected void createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm");

        try {
            User user = userService.createUser(username, email, password, confirmPassword);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/user/account");
        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
        }
    }

    protected void registration(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
    }
}
