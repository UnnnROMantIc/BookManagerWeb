package com.book.servlet.auth;

import com.book.service.UserService;
import com.book.service.impl.UserServiceImpl;
import com.book.utils.ThymeleafUtil;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    UserService service;
    @Override
    public void init() throws ServletException {
        service = new UserServiceImpl();
    }

    @Override
    //浏览器访问网页 Get请求
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");  // 设置内容类型和字符编码

        Context context = new Context();
        if(req.getSession().getAttribute("login-failure") != null) {
            context.setVariable("failure", true);
            req.getSession().removeAttribute("login-failure");
        }
        //已经登录成功 就不要跳转回登录界面了 直接跳转到首页 和登录成功重定向一样样的
        //缓存的问题 用生命周期clean来解决
        if(req.getSession().getAttribute("user") != null) {
            resp.sendRedirect("index");
        }
        ThymeleafUtil.process("login.html", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");  // 设置内容类型和字符编码
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember-me");
        if(service.auth(username,password, req.getSession())){
            //登录成功跳转
            resp.sendRedirect("index");
        }else {
            //添加错误标识
            req.getSession().setAttribute("login-failure",new Object());
            this.doGet(req, resp);
        }
    }
}
