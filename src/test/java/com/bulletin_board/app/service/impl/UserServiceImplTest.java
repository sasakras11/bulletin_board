package com.bulletin_board.app.service.impl;

import com.bulletin_board.app.entity.User;
import com.bulletin_board.app.exception.BulletinsApplicationException;
import com.bulletin_board.app.repository.UserRepository;
import com.bulletin_board.app.service.PasswordEncoder;
import com.bulletin_board.app.service.Validator;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  private static final String PASSWORD = "password";
  private static final String FIRST_NAME = "alex";
  private static final String EMAIL = "alex@gmail.com";
  private static final String LAST_NAME = "alexev";
  private static final User USER =
      User.builder()
          .email(EMAIL)
          .password(PASSWORD)
          .firstName(FIRST_NAME)
          .lastName(LAST_NAME)
          .build();
  @Mock private UserRepository userRepository;

  @Mock private Validator validator;
  @Mock private PasswordEncoder passwordEncoder;

  @InjectMocks private UserServiceImpl service;

  @After
  public void resetMocks() {
    reset(userRepository, validator, passwordEncoder);
  }

  @Test(expected = BulletinsApplicationException.class)
  public void whenPasswordIsWrongShouldBeException() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USER));
    when(passwordEncoder.matches(any(), any())).thenReturn(false);

    User user = service.login(EMAIL, PASSWORD);

    verify(userRepository).findByEmail(eq(EMAIL));
    verify(passwordEncoder).matches(any(), any());
  }

  @Test(expected = BulletinsApplicationException.class)
  public void whenThereIsNoSuchUserInDatabaseShouldBeException() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(passwordEncoder.matches(any(), any())).thenReturn(true);

    User user = service.login(EMAIL, PASSWORD);

    verify(userRepository).findByEmail(eq(EMAIL));
    verify(passwordEncoder.matches(any(), any()));
  }

  @Test
  public void whenCredentialsAreVerifiedLoginShouldBeSuccessful() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USER));
    when(passwordEncoder.matches(any(), any())).thenReturn(true);
    User user = service.login(EMAIL, PASSWORD);

    assertEquals(USER, user);

    verify(userRepository).findByEmail(eq(EMAIL));
    verify(passwordEncoder).matches(any(), any());
  }

  @Test
  public void userShouldRegisterIfHeIsValidatedAndThereIsNoSuchUserInDatabase() {
    doNothing().when(validator).validateEmail(EMAIL);
    doNothing().when(validator).validatePassword(PASSWORD);
    doNothing().when(validator).validateName(FIRST_NAME);
    doNothing().when(validator).validateName(LAST_NAME);
    when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);

    when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

    assertEquals(USER, service.register(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME));

    verify(validator, times(1)).validateEmail(EMAIL);
    verify(validator, times(1)).validatePassword(PASSWORD);
    verify(validator, times(1)).validateName(FIRST_NAME);
    verify(validator, times(1)).validateName(LAST_NAME);

    verify(userRepository).findByEmail(eq(EMAIL));
  }

  @Test(expected = BulletinsApplicationException.class)
  public void ifUserIsAlreadyInDatabaseShouldBeException() {
    doNothing().when(validator).validateEmail(EMAIL);
    doNothing().when(validator).validatePassword(PASSWORD);
    doNothing().when(validator).validateName(FIRST_NAME);
    doNothing().when(validator).validateName(LAST_NAME);

    when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(USER));

    service.register(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME);

    verify(userRepository).findByEmail(EMAIL);
    verify(validator).validateEmail(EMAIL);
    verify(validator, times(1)).validatePassword(PASSWORD);
    verify(validator, times(1)).validateName(FIRST_NAME);
    verify(validator, times(1)).validateName(LAST_NAME);
  }
}
