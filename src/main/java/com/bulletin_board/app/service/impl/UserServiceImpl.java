package com.bulletin_board.app.service.impl;

import com.bulletin_board.app.exception.BulletinsApplicationException;
import com.bulletin_board.app.service.DataParser;
import com.bulletin_board.app.entity.User;
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
      loggedIn = passwordEncoder.matches(password, byEmail.get().getPassword());
    }

    if (!loggedIn) {
      throw new BulletinsApplicationException("wrong credentials", "login");
    }
    log.info(
        String.format(
            "user with email [%s] and password [%s] logged in successfully", email, password));
    return byEmail.get();
  }

  public User register(String email, String password, String firstName, String lastName) {

    validator.validateEmail(email);
    validator.validatePassword(password);
    validator.validateName(firstName);
    validator.validateName(lastName);

    if (!userRepository.findByEmail(email).isPresent()) {
      User user =
          User.builder()
              .email(email)
              .password(passwordEncoder.encode(password))
              .firstName(firstName)
              .lastName(lastName)
              .build();
      userRepository.save(user);
      log.info(
          String.format(
              "user with email [%s],password [%s], first name [%s] and last name [%s]  registered successfully",
              email, password, firstName, lastName));

      return user;

    } else {
      throw new BulletinsApplicationException(
          "user with this email already registered", "registration");
    }
  }

  @Override
  public User editUser(int id, String newEmail, String newPassword, String newFirstName) {
    validator.validateEmail(newEmail);
    validator.validatePassword(newPassword);
    validator.validateName(newFirstName);

    Optional<User> user = userRepository.findById(id);

    user.ifPresentOrElse(
        userToEdit ->
            userRepository.save(
                User.builder()
                    .id(userToEdit.getId())
                    .email(newEmail)
                    .password(passwordEncoder.encode(newPassword))
                    .firstName(newFirstName)
                    .lastName(userToEdit.getLastName())
                    .build()),
        () -> {
          throw new BulletinsApplicationException(
              "something went wrong with editing your account", "account");
        });

    return user.get();
  }
}
