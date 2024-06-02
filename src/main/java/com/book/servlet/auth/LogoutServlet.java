package com.book.servlet.auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")//也在过滤器的范围之内 因为过滤器是"/*"对所有生效的
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //退出操作先清除再重定向
        req.getSession().removeAttribute("user");
        resp.sendRedirect("login");
    }
}
