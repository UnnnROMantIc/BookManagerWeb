package com.book.servlet.manage;

import com.book.service.BookService;
import com.book.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/return-book")
public class ReturnServlet extends HttpServlet {

    BookService service;
    @Override
    public void init() throws ServletException {
        service = new BookServiceImpl();
    }//这个可以创建一个单例来完成，每次创建太麻烦了
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");  // 设置内容类型和字符编码
        String id = req.getParameter("id");
        service.returnBook(id);
        resp.sendRedirect("index");
    }
}
