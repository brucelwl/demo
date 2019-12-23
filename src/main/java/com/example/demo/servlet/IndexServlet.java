package com.example.demo.servlet;

import org.springframework.boot.web.servlet.ServletContextInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruce on 2019/11/24 19:44
 */
@WebServlet(value = "/index", initParams = {@WebInitParam(name = "aa", value = "aaa")})
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = -6913998089462380487L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.getWriter().write("SpringBoot中使用Servlet");

        List<ServletContextInitializer> mergedInitializers = new ArrayList<>();
        mergedInitializers.add(servletContext -> {

        });
    }
}
