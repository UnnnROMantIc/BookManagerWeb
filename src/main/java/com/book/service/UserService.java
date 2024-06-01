package com.book.service;

import javax.servlet.http.HttpSession;

//解耦
public interface UserService {
    boolean auth(String username, String password, HttpSession session);
}
