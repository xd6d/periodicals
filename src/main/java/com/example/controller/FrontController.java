package com.example.controller;

import com.example.entity.Publication;
import com.example.entity.Topic;
import com.example.entity.User;
import com.example.service.PublicationService;
import com.example.service.Sorter;
import com.example.service.TopicService;
import com.example.service.UserService;
import com.example.service.impl.PublicationServiceImpl;
import com.example.service.impl.TopicServiceImpl;
import com.example.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet({"/", "/main/*"})
public class FrontController extends HttpServlet {
    TopicService topicService;
    PublicationService publicationService;
    UserService userService;
    Sorter sorter;

    @Override
    public void init() {
        topicService = new TopicServiceImpl();
        publicationService = new PublicationServiceImpl();
        userService = new UserServiceImpl();
        sorter = new Sorter();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        switch (requestURI) {
            case "/edit-publication":
                editPublication(req, resp);
                break;
            case "/delete":
                delete(req, resp);
                break;
            case "/edit":
                edit(req, resp);
                break;
            case "/new":
                newPublication(req, resp);
                break;
            case "/add":
                add(req, resp);
                break;
            case "/unblock":
                unblock(req, resp);
                break;
            case "/block":
                block(req, resp);
                break;
            case "/users":
                users(req, resp);
                break;
            case "/subscribe":
                subscribe(req, resp);
                break;
            case "/subscribed":
                subscribed(req, resp);
                break;
            case "/replenish":
                replenish(req, resp);
                break;
            case "/logout":
                logout(req, resp);
                break;
            case "/registration":
                registration(req, resp);
                break;
            case "/login":
                login(req, resp);
                break;
            case "/account":
                account(req, resp);
                break;
            default:
                index(req, resp);
        }
    }

    protected void editPublication(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null) {
            User user = userService.getUserByUsername(((User) userObject).getUsername());
            req.getSession().setAttribute("user", user);
            if (!user.isBlocked()) {
                if (user.getRole().getRole().equals("ADMIN")) {
                    String publicationIdSrt = req.getParameter("publicationId");
                    String title = req.getParameter("title");
                    String price = req.getParameter("price");
                    String[] topics = req.getParameterValues("topic");
                    if (title != null) {
                        publicationService.edit(publicationIdSrt, title, price, topics);
                    }
                }
                resp.sendRedirect("/");
            } else
                req.getRequestDispatcher("/WEB-INF/jsp/blocked.jsp").forward(req, resp);
        } else
            resp.sendRedirect("/login");
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null) {
            User user = userService.getUserByUsername(((User) userObject).getUsername());
            req.getSession().setAttribute("user", user);
            if (!user.isBlocked()) {
                if (user.getRole().getRole().equals("ADMIN")) {
                    String publicationIdStr = req.getParameter("publicationId");
                    if (publicationIdStr != null)
                        publicationService.delete(publicationIdStr);
                }
                resp.sendRedirect("/");
            } else
                req.getRequestDispatcher("/WEB-INF/jsp/blocked.jsp").forward(req, resp);
        } else
            resp.sendRedirect("/login");
    }

    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null) {
            User user = userService.getUserByUsername(((User) userObject).getUsername());
            req.getSession().setAttribute("user", user);
            if (!user.isBlocked()) {
                if (user.getRole().getRole().equals("ADMIN")) {
                    String publicationIdStr = req.getParameter("publicationId");
                    if (publicationIdStr != null) {
                        Publication publication = publicationService.getPublicationById(Integer.parseInt(publicationIdStr));
                        if (publication != null) {
                            req.setAttribute("topics", topicService.getAllTopics());
                            req.setAttribute("publication", publication);
                            req.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(req, resp);
                        } else {
                            resp.sendRedirect("/");
                        }
                    } else
                        resp.sendRedirect("/");
                } else
                    resp.sendRedirect("/");
            } else
                req.getRequestDispatcher("/WEB-INF/jsp/blocked.jsp").forward(req, resp);
        } else
            resp.sendRedirect("/login");
    }

    protected void newPublication(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null) {
            User user = userService.getUserByUsername(((User) userObject).getUsername());
            req.getSession().setAttribute("user", user);
            if (!user.isBlocked()) {
                if (user.getRole().getRole().equals("ADMIN")) {
                    String title = req.getParameter("title");
                    String price = req.getParameter("price");
                    String[] topics = req.getParameterValues("topic");
                    if (title != null && price != null) {
                        publicationService.createPublication(title, Double.parseDouble(price), topics);
                    }
                }
                resp.sendRedirect("/");
            } else
                req.getRequestDispatcher("/WEB-INF/jsp/blocked.jsp").forward(req, resp);
        } else
            resp.sendRedirect("/login");
    }

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null) {
            User user = userService.getUserByUsername(((User) userObject).getUsername());
            req.getSession().setAttribute("user", user);
            if (!user.isBlocked()) {
                if (user.getRole().getRole().equals("ADMIN")) {
                    req.setAttribute("topics", topicService.getAllTopics());
                    req.getRequestDispatcher("/WEB-INF/jsp/add.jsp").forward(req, resp);
                } else
                    resp.sendRedirect("/");
            } else
                req.getRequestDispatcher("/WEB-INF/jsp/blocked.jsp").forward(req, resp);
        } else
            resp.sendRedirect("/login");
    }

    protected void unblock(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null) {
            User user = userService.getUserByUsername(((User) userObject).getUsername());
            req.getSession().setAttribute("user", user);
            if (!user.isBlocked()) {
                if (user.getRole().getRole().equals("ADMIN")) {
                    String username = req.getParameter("username");
                    if (username != null) {
                        userService.unblock(userService.getUserByUsername(username));
                    }
                    resp.sendRedirect("/users");
                } else
                    resp.sendRedirect("/");
            } else
                req.getRequestDispatcher("/WEB-INF/jsp/blocked.jsp").forward(req, resp);
        } else
            resp.sendRedirect("/login");
    }

    protected void block(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null) {
            User user = userService.getUserByUsername(((User) userObject).getUsername());
            req.getSession().setAttribute("user", user);
            if (!user.isBlocked()) {
                if (user.getRole().getRole().equals("ADMIN")) {
                    String username = req.getParameter("username");
                    if (username != null) {
                        userService.block(userService.getUserByUsername(username));
                    }
                    resp.sendRedirect("/users");
                } else
                    resp.sendRedirect("/");
            } else
                req.getRequestDispatcher("/WEB-INF/jsp/blocked.jsp").forward(req, resp);
        } else
            resp.sendRedirect("/login");
    }

    protected void users(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null) {
            User user = userService.getUserByUsername(((User) userObject).getUsername());
            req.getSession().setAttribute("user", user);
            if (!user.isBlocked()) {
                if (user.getRole().getRole().equals("ADMIN")) {
                    req.setAttribute("users", userService.getAllUsers());
                    req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
                } else
                    resp.sendRedirect("/");
            } else
                req.getRequestDispatcher("/WEB-INF/jsp/blocked.jsp").forward(req, resp);
        } else
            resp.sendRedirect("/login");
    }

    protected void subscribe(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null) {
            User user = userService.getUserByUsername(((User) userObject).getUsername());
            req.getSession().setAttribute("user", user);
            if (!user.isBlocked()) {
                String publicationId = req.getParameter("publicationId");
                if (publicationId != null) {
                    userService.subscribe(user, Integer.parseInt(publicationId));
                }
                resp.sendRedirect("/");
            } else
                req.getRequestDispatcher("/WEB-INF/jsp/blocked.jsp").forward(req, resp);
        } else
            resp.sendRedirect("/login");
    }

    protected void subscribed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        validatePage(req);
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null) {
            User user = userService.getUserByUsername(((User) userObject).getUsername());
            req.getSession().setAttribute("user", user);
            if (!user.isBlocked()) {
                req.setAttribute("topics", topicService.getAllAndSortByName());
                List<Publication> publications = user.getPublications();
                String topicParam = req.getParameter("topic");
                if (topicParam != null) {
                    Topic topic = topicService.getTopicByStringId(topicParam);
                    if (topic != null)
                        publications = publicationService.selectPublicationsByTopic(topic, publications);
                }
                String sortName = req.getParameter("sort");
                String reversedName = req.getParameter("reversed");
                String search = req.getParameter("search");
                if (search != null)
                    publications = publicationService.selectPublicationsBySearch(search, publications);
                req.setAttribute("publications", sorter.sort(sortName, reversedName, publications));
                req.getRequestDispatcher("/WEB-INF/jsp/subscribed.jsp").forward(req, resp);
            } else
                req.getRequestDispatcher("/WEB-INF/jsp/blocked.jsp").forward(req, resp);
        } else
            resp.sendRedirect("/login");
    }

    protected void replenish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null) {
            User user = userService.getUserByUsername(((User) userObject).getUsername());
            req.getSession().setAttribute("user", user);
            if (!user.isBlocked()) {
                String replenishString = req.getParameter("replenish");
                if (replenishString != null) {
                    boolean success = userService.replenish(user, Double.parseDouble(replenishString));
                    if (success)
                        resp.sendRedirect("/account");
                    else {
                        req.setAttribute("error", "Something went wrong. Please contact us.");
                        req.getRequestDispatcher("/WEB-INF/jsp/replenish.jsp").forward(req, resp);
                    }
                } else
                    req.getRequestDispatcher("/WEB-INF/jsp/replenish.jsp").forward(req, resp);
            } else
                req.getRequestDispatcher("/WEB-INF/jsp/blocked.jsp").forward(req, resp);
        } else
            resp.sendRedirect("/login");
    }

    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("user", null);
        resp.sendRedirect("/");
    }

    protected void createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm");
        if (password.equals(confirmPassword)) {
            User user = userService.getUserByEmail(email);
            if (user == null) {
                user = userService.getUserByUsername(username);
                if (user == null) {
                    req.getSession().setAttribute("user", userService.createUser(username, email, password));
                    resp.sendRedirect("/account");
                } else {
                    req.setAttribute("error", "User with the given username already exists.");
                    req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("error", "This email is already registered.");
                req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Passwords are not the same.");
            req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
        }
    }

    protected void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("topics", topicService.getAllAndSortByName());
        List<Publication> publications = publicationService.getAllPublications();
        String topicParam = req.getParameter("topic");
        if (topicParam != null) {
            Topic topic = topicService.getTopicByStringId(topicParam);
            if (topic != null)
                publications = publicationService.getPublicationsByTopic(topic);
        }
        String sortName = req.getParameter("sort");
        String reversedName = req.getParameter("reversed");
        String search = req.getParameter("search");
        if (search != null)
            publications = publicationService.selectPublicationsBySearch(search, publications);
        req.setAttribute("publications", sorter.sort(sortName, reversedName, publications));
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null)
            req.getSession().setAttribute("user", userService.getUserByUsername(((User) userObject).getUsername()));
        validatePage(req);
        req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
    }

    private void validatePage(HttpServletRequest req) {
        int page = 1;
        String pageParam = req.getParameter("page");
        if (pageParam != null && pageParam.matches("^[\\d]+$"))
            page = Integer.parseInt(pageParam);
        req.setAttribute("page", page);
    }

    protected void account(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Object userObject = req.getSession().getAttribute("user");
        if (userObject != null) {
            req.getSession().setAttribute("user", userService.getUserByUsername(((User) userObject).getUsername()));
            req.getRequestDispatcher("/WEB-INF/jsp/account.jsp").forward(req, resp);
        } else
            resp.sendRedirect("/login");
    }

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null)
            resp.sendRedirect("/account");
        else {
            if (req.getParameter("email") != null)
                enterAccount(req, resp);
            else
                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }

    protected void enterAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter("email");
        String givenPassword = req.getParameter("password");
        String actualPassword = userService.getPassword(email);
        if (actualPassword == null) {
            req.setAttribute("error", "User with the given email does not exist.");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        } else if (givenPassword.equals(actualPassword)) {
            req.getSession().setAttribute("user", userService.getUserByEmail(email));
            resp.sendRedirect("/account");
        } else {
            req.setAttribute("error", "Incorrect password.");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }

    protected void registration(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null)
            resp.sendRedirect("/account");
        else {
            if (req.getParameter("username") != null)
                createUser(req, resp);
            else
                req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
        }
    }
}
