package com.weltond.web.servlet;

import com.weltond.domain.User;
import com.weltond.exceptions.UsersException;
import com.weltond.service.UserService;
import com.weltond.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Weltond Ning
 * @Project MyLoginMVC
 * @Date 5/21/2019
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/servlet/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService us = new UserServiceImpl();
        try {
            user = us.login(user);
            if (user != null) {
                request.getSession().setAttribute("user", user);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } catch (UsersException e) {
            e.printStackTrace();
            request.setAttribute("wrongUser", e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        ///*
        if (user == null) {
            request.setAttribute("wrongUser", "User name or Password WRONG. Please re-enter");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("user", user);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        //*/

    }
}
