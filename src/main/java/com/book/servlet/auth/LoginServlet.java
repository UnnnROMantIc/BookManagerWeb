package com.book.servlet.auth;

import com.book.service.UserService;
import com.book.service.impl.UserServiceImpl;
import com.book.utils.ThymeleafUtil;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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

//    @Override
//    //浏览器访问网页 Get请求
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html; charset=UTF-8");  // 设置内容类型和字符编码
//        //req.setCharacterEncoding("utf-8");
//        //resp.setCharacterEncoding("utf-8"); 这样都可以的
//        Context context = new Context();
//        if(req.getSession().getAttribute("login-failure") != null) {
//            context.setVariable("failure", true);
//            req.getSession().removeAttribute("login-failure");
//        }
//        //已经登录成功 就不要跳转回登录界面了 直接跳转到首页 和登录成功重定向一样样的
//        //缓存的问题 用生命周期clean来解决
//        if(req.getSession().getAttribute("user") != null) {
//            resp.sendRedirect("index");
//        }
//        ThymeleafUtil.process("login.html", context, resp.getWriter());
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html; charset=UTF-8");  // 设置内容类型和字符编码
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        String remember = req.getParameter("remember-me");
//        if(service.auth(username,password, req.getSession())){
//            //登录成功跳转
//            resp.sendRedirect("index");
//        }else {
//            //添加错误标识
//            req.getSession().setAttribute("login-failure",new Object());
//            this.doGet(req, resp);
//        }
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        Context context = new Context();

        // 检查是否存在用户名和密码Cookie
        Cookie[] cookies = req.getCookies();
        String username = null;
        String password = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                } else if (cookie.getName().equals("password")) {
                    password = cookie.getValue();
                }
            }
        }

        // 将用户名和密码添加到上下文中
        context.setVariable("username", username);
        context.setVariable("password", password);

        if(req.getSession().getAttribute("login-failure") != null) {
            context.setVariable("failure", true);
            req.getSession().removeAttribute("login-failure");
        }

        if(req.getSession().getAttribute("user") != null) {
            resp.sendRedirect("index");
        } else {
            ThymeleafUtil.process("login.html", context, resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean rememberMe = req.getParameter("remenber-me") != null;

        if (service.auth(username, password, req.getSession())) {
            // 登录成功
            if (rememberMe) {
                // 创建Cookie,存储用户名和密码
                Cookie usernameCookie = new Cookie("username", username);
                Cookie passwordCookie = new Cookie("password", password);
                usernameCookie.setMaxAge(604800); // 设置Cookie有效期为7天
                passwordCookie.setMaxAge(604800);
                resp.addCookie(usernameCookie);
                resp.addCookie(passwordCookie);
            }
            resp.sendRedirect("index");
        } else {
            // 登录失败
            req.getSession().setAttribute("login-failure", new Object());
            doGet(req, resp);
        }
//        在doGet方法中,我们首先检查是否存在用户名和密码Cookie,如果存在,则将它们存储在username和password变量中
//        然后,我们将这些值添加到Thymeleaf的上下文中,以便在模板中使用它们
//        在doPost方法中,我们检查remenber-me参数是否存在。如果存在,则表示用户选择了"记住我",我们创建用户名和密码Cookie,并将它们添加到响应中
    }
}

