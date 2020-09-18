package com.bulletin_board.app.service.impl;

import com.bulletin_board.app.service.DataParser;
import com.bulletin_board.app.entity.User;
import com.bulletin_board.app.exception.LoginCredentialsException;
import com.bulletin_board.app.repository.UserRepository;
import com.bulletin_board.app.service.UserService;
import com.bulletin_board.app.service.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;
    private final DataParser dataParser;

    @Override
    public User login(String email, String password) {

        Optional<User> byEmail = userRepository.findByEmail(email);
        boolean loggedIn = false;
        if (byEmail.isPresent()) {
      System.out.println(passwordEncoder.encode(password));
      System.out.println(byEmail.get().getPassword());
            loggedIn = passwordEncoder.matches(password, byEmail.get().getPassword());
        }

        if (!loggedIn) {
            throw new LoginCredentialsException("login");
        }
        log.info(
                String.format(
                        "user with email [%s] and password [%s] logged in successfully",
                        email, password));
        return byEmail.get();
    }

}
