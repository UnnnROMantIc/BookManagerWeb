package com.book.servlet.pages;

import com.book.entity.User;
import com.book.service.BookService;
import com.book.service.impl.BookServiceImpl;
import com.book.utils.ThymeleafUtil;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    BookService service;
    @Override
    public void init() throws ServletException {
        service = new BookServiceImpl();
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
//        resp.setContentType("text/html; charset=UTF-8");  // 设置内容类型和字符编码
//
//        Context context = new Context();
//        User user = (User) req.getSession().getAttribute("user");
//        context.setVariable("nickname", user.getNickname());
//        context.setVariable("student_list", service.getStudentList());
//
//        ThymeleafUtil.process("students.html",new Context(),resp.getWriter());
//
//    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        Context context = new Context();
        User user = (User) req.getSession().getAttribute("user");
        context.setVariable("nickname", user.getNickname());
        context.setVariable("student_list", service.getStudentList());
        ThymeleafUtil.process("students.html", context, resp.getWriter());
    }
}
