package com.example.listener;

import com.example.dao.ConnectionAdapter;
import com.example.service.impl.PublicationServiceImpl;
import com.example.service.impl.TopicServiceImpl;
import com.example.service.impl.UserServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationServletContextListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("topicService", new TopicServiceImpl());
        servletContext.setAttribute("publicationService", new PublicationServiceImpl());
        servletContext.setAttribute("userService", new UserServiceImpl());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionAdapter.closeConnection();
        ConnectionAdapter.unregisterDriver();
    }

}
