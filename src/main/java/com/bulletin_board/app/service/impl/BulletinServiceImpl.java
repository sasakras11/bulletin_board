package com.bulletin_board.app.service.impl;

import com.bulletin_board.app.entity.Bulletin;
import com.bulletin_board.app.entity.User;
import com.bulletin_board.app.repository.BulletinRepository;
import com.bulletin_board.app.service.BulletinService;
import com.bulletin_board.app.service.DataParser;
import com.bulletin_board.app.service.util.FileLoader;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BulletinServiceImpl implements BulletinService {

  private final BulletinRepository bulletinRepository;
  private final DataParser dataParser;
  private final FileLoader fileLoader;
  private static final int ITEMS_PER_PAGE = 10;

  @Override
  public void loadBulletin( String header, String text, User author) {


    Bulletin bulletin =
        Bulletin.builder()
            .header(header)
            .text(text)
            .author(author)
            .date(new Date())
            .build();

    bulletinRepository.save(bulletin);
  }

  @Override
  public List<Bulletin> getPageOfBulletins(String page) {


    return dataParser
        .parseInt(page)
        .filter(this::isPageNumberValid)
        .map(x -> bulletinRepository.findAll(PageRequest.of(x - 1, ITEMS_PER_PAGE)).getContent())
        .orElseGet(
            () -> bulletinRepository.findAll(PageRequest.of(0, ITEMS_PER_PAGE)).getContent());
  }

  public boolean isPageNumberValid(int page) {
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
