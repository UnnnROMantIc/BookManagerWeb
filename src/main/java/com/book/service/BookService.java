package com.book.service;

import com.book.entity.Book;
import com.book.entity.Borrow;
import com.book.entity.Student;

import java.util.List;
import java.util.Map;

public interface BookService {
    List<Borrow> getBorrowList();
    //还书
    void returnBook(String id );
    //过滤掉已经借出的书籍
    List<Book> getActiveBookList();

    List<Student> getStudentList();

    void addBorrow(int sid, int bid);
    //要显示全部书籍的同时 显示他是不是被借了
    Map<Book, Boolean> getBookList();

    void deleteBook(int bid);

    void addBook(String title, String desc, double price);
}
