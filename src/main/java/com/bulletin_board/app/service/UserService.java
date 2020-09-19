package com.bulletin_board.app.service;

import com.bulletin_board.app.entity.User;

public interface UserService {

    User login(String email, String password);

    User register(String email,String password,String firstName, String lastName);

    User editUser(int id,String newEmail,String newPassword, String newFirstName);



}
