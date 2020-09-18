package com.bulletin_board.app.repository;

import com.bulletin_board.app.entity.Bulletin;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BulletinRepository extends PagingAndSortingRepository<Bulletin,Integer> {
}
