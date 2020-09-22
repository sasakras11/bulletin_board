package com.bulletin_board.app.service;

import com.bulletin_board.app.entity.Bulletin;
import com.bulletin_board.app.exception.BulletinsApplicationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.fail;

public class ValidatorTest {

  private static final String TOO_LONG_TEXT_FOR_BULLETIN =
      "Suspendisse potenti. Suspendisse condimentum justo vel metus iaculis gravida. Donec aliquet tortor a enim viverra dapibus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Proin ultricies nunc in erat ullamcorper interdum. Proin vulputate malesuada quam, eget placerat nibh sagittis eget. Vestibulum efficitur ipsum sed purus aliquam, nec fermentum turpis vulputate"
          + "Curabitur pretium magna ut orci gravida, at euismod lectus iaculis. Etiam aliquam scelerisque odio vitae elementum. Nulla commodo nunc a sem tincidunt, et semper nunc viverra. Donec convallis elit eget laoreet tincidunt. Pellentesque a nunc eu ante tempus molestie. Duis id nibh aliquet, blandit pu";

  private Validator validator;

  @Before
  public void init() {
    validator = new Validator();
  }

  @Test(expected = BulletinsApplicationException.class)
  public void WhenNameIsTooShortShouldBeException() {
    validator.validateName("a");
  }

  @Test(expected = BulletinsApplicationException.class)
  public void WhenNameIsTooLongShouldBeException() {
    validator.validateName("alexander Krasnov");
  }

  @Test(expected = BulletinsApplicationException.class)
  public void WhenNameContainsNumbersShouldBeException() {
    validator.validateName("alex123");
  }

  @Test(expected = BulletinsApplicationException.class)
  public void WhenEmailIsWrongShouldBeException() {
    validator.validateEmail("mymail");
  }

  @Test(expected = BulletinsApplicationException.class)
  public void WhenTextIsTooLongShouldBeException() {
    validator.validateBulletinText(TOO_LONG_TEXT_FOR_BULLETIN);
  }

  @Test(expected = BulletinsApplicationException.class)
  public void WhenTextIsTooShortShouldBeException() {
    validator.validateBulletinText("this is text");
  }

  @Test(expected = BulletinsApplicationException.class)
  public void WhenHeaderIsTooShortShouldBeException() {
    validator.validateBulletinHeader("this is");
  }

  @Test(expected = BulletinsApplicationException.class)
  public void WhenHeaderIsTooLongShouldBeException() {
    validator.validateBulletinHeader("Hello everyone,i am text for header and i am too long !!!");
  }

  @Test(expected = BulletinsApplicationException.class)
  public void whenFileExtensionIsWrongShouldBeException() {
    validator.validateFileExtension("file.txt");
  }
}
