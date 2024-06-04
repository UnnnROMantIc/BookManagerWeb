package com.book.service.impl;

import com.book.dao.BookMapper;
import com.book.dao.StudentMapper;
import com.book.entity.Book;
import com.book.entity.Borrow;
import com.book.entity.Student;
import com.book.service.BookService;
import com.book.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.*;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    @Override
    public List<Borrow> getBorrowList() {
        try (SqlSession sqlSession =  MybatisUtil.getSession()){
            BookMapper mapper =  sqlSession.getMapper(BookMapper.class);
            return mapper.getBorrowList();
        }
    }
    @Override
    public void returnBook(String  id) {
        try (SqlSession sqlSession =  MybatisUtil.getSession()){
            BookMapper mapper =  sqlSession.getMapper(BookMapper.class);
            mapper.deleteBorrow(id);
        }
    }

//    @Override
//    //这边这个过滤是可以简单的放在Mapper里面的，那是最好的，直接进行一个联查过滤，写在这边是个为了提现一下这个service的作用
//    //但是如果借阅表特别庞大的话 开销是很大的
//    public List<Book> getActiveBookList() {
//        Set<Integer> set = new HashSet<>();
//        this.getBorrowList().forEach(borrow -> set.add(borrow.getId()));//储存被借了的书籍
//        try (SqlSession sqlSession =  MybatisUtil.getSession()){
//            BookMapper mapper =  sqlSession.getMapper(BookMapper.class);
//            return mapper.getBookList()
//                    .stream()
//                    .filter(book -> set.contains(book.getBid()))//不在被借阅列表的才通过
//                    .collect(Collectors.toList());
////添加借阅这边有点繁琐了
//        }
//    }
@Override
public List<Book> getActiveBookList() {
    Set<Integer> set = new HashSet<>();
    this.getBorrowList().forEach(borrow -> set.add(borrow.getBook_id())); // 储存被借了的书籍的ID
    try (SqlSession sqlSession = MybatisUtil.getSession()) {
        BookMapper mapper = sqlSession.getMapper(BookMapper.class);
        return mapper.getBookList()
                .stream()
                .filter(book -> !set.contains(book.getBid())) // 过滤掉被借阅的图书
                .collect(Collectors.toList());
    }
}

    //显示书籍列表
//    @Override
//    public Map<Book, Boolean> getBookList() {
//        //set这边太麻烦了 建数据库的时候就该考虑到这个问题 就不会有这么多的操作了
//        Set<Integer> set = new HashSet<>();
//        this.getBorrowList().forEach(borrow -> set.add(borrow.getBook_id())); // 储存被借了的书籍的ID
//        try (SqlSession sqlSession = MybatisUtil.getSession()) {
//            Map<Book, Boolean> map = new HashMap<>();
//            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
//            mapper.getBookList().forEach(book -> map.put(book, !set.contains(book.getBid())));
//            return map;
//        }
//    }
    @Override
    public Map<Book, Boolean> getBookList() {
        Set<Integer> set = new HashSet<>();
        this.getBorrowList().forEach(borrow -> set.add(borrow.getBook_id()));
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            Map<Book, Boolean> map = new LinkedHashMap<>();
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.getBookList().forEach(book -> map.put(book, set.contains(book.getBid())));
            return map;
        }
    }

    @Override
    public void deleteBook(int bid) {
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.deleteBook(bid);

        }
    }



    @Override
    public List<Student> getStudentList() {
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            return mapper.getStudentList();
        }
    }

    //这边最后再做一下判断，因为发起的请求不一定是符合规则的
    //但是这边可以强制的去提交一个错误的数据
    //这会破坏我们的体系，或者可以把数据库的bid改一下，加个索引Unicode
    @Override
    public void addBorrow(int sid, int bid) {

        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
             mapper.addBorrow(sid, bid);

        }
    }

    @Override
    public void addBook(String title, String desc, double price) {
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.addBook(title, desc, price);
        }
}

}

