package com.bulletin_board.app.service.impl;

import com.bulletin_board.app.entity.Bulletin;
import com.bulletin_board.app.entity.User;
import com.bulletin_board.app.exception.BulletinsApplicationException;
import com.bulletin_board.app.repository.BulletinRepository;
import com.bulletin_board.app.service.DataParser;
import com.bulletin_board.app.service.Validator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BulletinServiceImplTest {

  private static final int ITEMS_PER_PAGE = 10;
  private static final List<Bulletin> BULLETINS =
      new ArrayList<>() {
        {
          add(Bulletin.builder().id(1).build());
          add(Bulletin.builder().id(2).build());
          add(Bulletin.builder().id(3).build());
          add(Bulletin.builder().id(4).build());
          add(Bulletin.builder().id(5).build());
          add(Bulletin.builder().id(6).build());
          add(Bulletin.builder().id(7).build());
          add(Bulletin.builder().id(8).build());
          add(Bulletin.builder().id(9).build());
          add(Bulletin.builder().id(10).build());
          add(Bulletin.builder().id(11).build());
          add(Bulletin.builder().id(12).build());
          add(Bulletin.builder().id(13).build());
          add(Bulletin.builder().id(14).build());
          add(Bulletin.builder().id(15).build());
          add(Bulletin.builder().id(16).build());
          add(Bulletin.builder().id(17).build());
        }
      };

  private static final String TEXT =
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer vitae turpis tristique, egestas dolor eu, tincidunt est. Nam gravida metus odio, nec rhoncus sem tincidunt id";
  private static final String HEADER = "Lorem ipsum dolor";
  private static final String IMAGE_PATH = "path";
  private static final Date DATE = new Date();
  private static final User AUTHOR =
      User.builder()
          .email("alex@gmail.com")
          .password("password")
          .firstName("first name")
          .lastName("last name")
          .build();

  private static final Bulletin BULLETIN =
      Bulletin.builder()
          .header(HEADER)
          .text(TEXT)
          .pathToImage(IMAGE_PATH)
          .date(DATE)
          .author(AUTHOR)
          .build();
  @Mock private Validator validator;

  @Mock private MultipartFile multipartFile;

  @Mock private BulletinRepository bulletinRepository;

  @Mock private DataParser dataParser;

  @Mock private PageRequest pageRequest;

  @InjectMocks private BulletinServiceImpl service;

  @After
  public void resetMocks() {
    reset(validator, multipartFile, bulletinRepository, dataParser);
  }

  @Test(expected = BulletinsApplicationException.class)
  public void whenBulletinIsAlreadyInDatabaseShouldBeThrownException() {

    doNothing().when(validator).validateBulletinHeader(HEADER);
    doNothing().when(validator).validateBulletinText(TEXT);
    when(bulletinRepository.findByTextAndHeader(TEXT, HEADER)).thenReturn(Optional.of(BULLETIN));

    service.loadBulletin(multipartFile, HEADER, TEXT, AUTHOR);

    verify(validator).validateBulletinText(TEXT);
    verify(validator).validateBulletinHeader(HEADER);
    verify(bulletinRepository).findByTextAndHeader(TEXT, HEADER);
  }

  @Test(expected = BulletinsApplicationException.class)
  public void whenHeaderOfBulletinIsNotValidShouldBeException() {
    doThrow(BulletinsApplicationException.class).when(validator).validateBulletinHeader(HEADER);

    service.loadBulletin(multipartFile, HEADER, TEXT, AUTHOR);

    verify(validator).validateBulletinHeader(HEADER);
  }

  @Test(expected = BulletinsApplicationException.class)
  public void whenTextOFBulletinIsNotValidShouldBeException() {
    doThrow(BulletinsApplicationException.class).when(validator).validateBulletinText(TEXT);

    service.loadBulletin(multipartFile, HEADER, TEXT, AUTHOR);

    verify(validator).validateBulletinText(TEXT);
  }

  @Test
  public void whenPageNumberIsNotValidShouldReturnNumberOne() {
    when(bulletinRepository.count()).thenReturn(15L);

    assertFalse(service.isPageNumberValid(17));
    assertFalse(service.isPageNumberValid(-2));

    verify(bulletinRepository).count();
  }

  @Test
  public void pagesCountShouldReturnRightCorrectPages() {
    when(bulletinRepository.count()).thenReturn(15L);

    assertEquals(2, service.pagesCount());

    verify(bulletinRepository).count();
  }
}
