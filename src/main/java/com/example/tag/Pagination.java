package com.example.tag;

import com.example.dao.impl.RoleRepositoryImpl;
import lombok.Setter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Collection;

@Setter
public class Pagination extends TagSupport {
    private static final Logger logger = LogManager.getLogger(RoleRepositoryImpl.class);
    private Collection<Object> items;
    private int step;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        if (step <=0)
            step = 5;
        String sort = getParam(pageContext.getRequest(), "sort");
        String reversed = getParam(pageContext.getRequest(), "reversed");
        String topic = getParam(pageContext.getRequest(), "topic");
        String search = getParam(pageContext.getRequest(), "search");
        try {
            out.println("<ul class=\"pagination justify-content-center\">");
            for (int i = 0; i < items.size(); i += step) {
                int page = i / step + 1;
                out.println("<li class=\"page-item\">");
                out.println("<a class=\"page-link\"" +
                        "href=\"?sort="+sort+
                        "&reversed="+reversed+
                        "&topic="+topic+
                        "&search="+search+
                        "&page="+page+"\">");
                out.println(page);
                out.println("</a>");
                out.println("</li>");
            }
            out.println("</ul>");
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return super.doStartTag();
    }

    private String getParam(ServletRequest request, String name) {
        String param = request.getParameter(name);
        if (param == null)
            param = "";
        return param;
    }
}
