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

@WebServlet("/index")
public class IndexServlet  extends HttpServlet {


    BookService service;
    @Override
    public void init() throws ServletException {
        service = new BookServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");  // 设置内容类型和字符编码

        Context context = new Context();
        User user = (User) req.getSession().getAttribute("user");
        context.setVariable("nickname", user.getNickname());
        context.setVariable("borrow_list", service.getBorrowList());
        //首页数量等 这样用service去写开销太大了，完全可以用mapper，这个效率太低了
        context.setVariable("book_count", service.getBookList().size());
        context.setVariable("student_count", service.getStudentList().size());
        ThymeleafUtil.process("index.html",context,resp.getWriter());

    }
}
