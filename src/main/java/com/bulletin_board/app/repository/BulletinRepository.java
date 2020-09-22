package com.bulletin_board.app.repository;

import com.bulletin_board.app.entity.Bulletin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface BulletinRepository extends PagingAndSortingRepository<Bulletin, Integer> {

  Page<Bulletin> findAllByOrderByDateDesc(Pageable pageable);

  Optional<Bulletin> findByTextAndHeader(String text, String header);
}
