package com.bulletin_board.app.service;


import com.bulletin_board.app.entity.Bulletin;
import com.bulletin_board.app.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BulletinService {

    List<Bulletin> getPageOfBulletins(String page);

    void loadBulletin( String header, String text, User author);
}
