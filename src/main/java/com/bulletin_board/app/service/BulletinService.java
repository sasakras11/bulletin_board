package com.bulletin_board.app.service;


import com.bulletin_board.app.entity.Bulletin;

import java.util.List;

public interface BulletinService {

    List<Bulletin> getPageOfBulletins(String page);

}
