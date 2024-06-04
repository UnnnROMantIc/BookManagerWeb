package com.book.dao;

import com.book.entity.Borrow;
import org.apache.ibatis.annotations.*;
import com.book.entity.Book;

import java.util.List;

public interface BookMapper {

    @Results ({
            @Result(column = "id",property = "id"),
            @Result(column = "bid",property = "book_id"),
            @Result(column = "title",property = "book_name"),
            @Result(column = "time",property = "time"),
            @Result(column = "name",property = "student_name"),
            @Result(column = "sid",property = "student_id"),
    })
    @Select("select * from borrow, student,book where borrow.bid = book.bid and student.sid = borrow.sid")
    List<Borrow> getBorrowList();

    //借书
    @Insert("insert into borrow(sid, bid, time) values(#{sid}, #{bid}, Now())")
    void addBorrow(@Param("sid") int sid, @Param("bid") int book_id);
    //还书
    @Delete("delete from borrow where id = #{id}")
    void deleteBorrow(String id);

    @Select("select * from book")
    List<Book> getBookList();

    @Delete("delete from book where bid = #{bid}")
    void deleteBook(int bid);

    //使用反引号将 desc 列名包裹起来，这样可以确保它不被误认为是关键字
    @Insert("insert into book(title,`desc`,price) values(#{title}, #{desc}, #{price})")
    void addBook(@Param("title")String title, @Param("desc")String desc, @Param("price")double price);
}
