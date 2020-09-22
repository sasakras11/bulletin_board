package com.bulletin_board.app.controller;

import com.bulletin_board.app.service.BulletinService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BulletinController {
  private final BulletinService bulletinService;
  private final SessionBean sessionBean;
  private static final String BULLETINS_LIST_NAME = "bulletins";
  private static final String BULLETINS_HTML_PAGE_NAME = "bulletins";
  private static final String PAGES_COUNT_ATTRIBUTE = "pagesCount";

  @PostMapping(value = "/addBulletin")
  public ModelAndView addBulletin(
      @RequestParam("image") MultipartFile image,
      @RequestParam("header") String header,
      @RequestParam("text") String text) {
    ModelAndView modelAndView = new ModelAndView();
    bulletinService.loadBulletin(image, header, text, sessionBean.getUser());
    modelAndView.addObject(BULLETINS_LIST_NAME, bulletinService.getPageOfBulletins("1"));
    modelAndView.addObject(PAGES_COUNT_ATTRIBUTE, bulletinService.pagesCount());

    modelAndView.setViewName(BULLETINS_HTML_PAGE_NAME);
    return modelAndView;
  }

  @GetMapping(value = "/bulletins")
  public ModelAndView getBulletinsPage() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject(BULLETINS_LIST_NAME, bulletinService.getPageOfBulletins("1"));
    modelAndView.addObject(PAGES_COUNT_ATTRIBUTE, bulletinService.pagesCount());

    modelAndView.setViewName(BULLETINS_HTML_PAGE_NAME);

    return modelAndView;
  }

  @GetMapping(value = {"/page"})
  public ModelAndView findPage(@RequestParam("pageNum") String pageNum) {

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject(BULLETINS_LIST_NAME, bulletinService.getPageOfBulletins(pageNum));
    modelAndView.addObject(PAGES_COUNT_ATTRIBUTE, bulletinService.pagesCount());
    modelAndView.setViewName(BULLETINS_HTML_PAGE_NAME);

    return modelAndView;
  }

  @GetMapping(value = "/bulletinAddPage")
  public ModelAndView getAddBulletinPage() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("add_bulletin");
    return modelAndView;
  }
}
