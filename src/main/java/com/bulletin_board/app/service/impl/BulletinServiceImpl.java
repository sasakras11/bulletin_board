package com.bulletin_board.app.service.impl;

import com.bulletin_board.app.entity.Bulletin;
import com.bulletin_board.app.entity.User;
import com.bulletin_board.app.exception.BulletinsApplicationException;
import com.bulletin_board.app.repository.BulletinRepository;
import com.bulletin_board.app.service.BulletinService;
import com.bulletin_board.app.service.DataParser;
import com.bulletin_board.app.service.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BulletinServiceImpl implements BulletinService {

  private final BulletinRepository bulletinRepository;
  private final DataParser dataParser;
  private final Validator validator;
  private static final int ITEMS_PER_PAGE = 10;

  @Override
  public void loadBulletin(MultipartFile image, String header, String text, User author) {

    validator.validateBulletinHeader(header);
    validator.validateBulletinText(text);

    if (!bulletinRepository.findByTextAndHeader(text, header).isPresent()) {
      validator.validateFileExtension(Objects.requireNonNull(image.getOriginalFilename()));
      String path = "images/" + image.getOriginalFilename();
      try {
        Path copyLocation =
            Paths.get(
                "target/classes/static/images"
                    + File.separator
                    + StringUtils.cleanPath(image.getOriginalFilename()));
        Files.copy(image.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
      } catch (Exception e) {
        throw new BulletinsApplicationException("cannot upload file", "account");
      }

      Date date = new Date();

      Bulletin bulletin =
          Bulletin.builder()
              .header(header)
              .text(text)
              .author(author)
              .date(date)
              .pathToImage(path)
              .build();

      bulletinRepository.save(bulletin);

      log.info(
          String.format(
              "uploaded new bulletin { header : %s , date : %s by user with email %s }",
              header, date, author.getEmail()));
    } else {
      throw new BulletinsApplicationException(
          "bulletin with such text and header already exists", "add_bulletin");
    }
  }

  @Override
  public List<Bulletin> getPageOfBulletins(String page) {

    return dataParser
        .parseInt(page)
        .filter(this::isPageNumberValid)
        .map(
            x ->
                bulletinRepository
                    .findAllByOrderByDateDesc(PageRequest.of(x - 1, ITEMS_PER_PAGE))
                    .getContent())
        .orElseGet(
            () ->
                bulletinRepository
                    .findAllByOrderByDateDesc(PageRequest.of(0, ITEMS_PER_PAGE))
                    .getContent());
  }

  public boolean isPageNumberValid(int page) {
    if (page < 1) return false;
    long maxPage = pagesCount();
    return maxPage >= page;
  }

  public int pagesCount() {
    long itemsCount = bulletinRepository.count();
    long pagesCount = itemsCount / ITEMS_PER_PAGE;
    if (itemsCount % ITEMS_PER_PAGE != 0) {
      pagesCount++;
    }
    return (int) pagesCount;
  }
}
