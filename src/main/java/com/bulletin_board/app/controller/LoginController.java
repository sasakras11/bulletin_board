package com.bulletin_board.app.controller;

import com.bulletin_board.app.entity.User;
import com.bulletin_board.app.service.BulletinService;
import com.bulletin_board.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

  private final UserService userService;
  private final BulletinService bulletinService;
  private final SessionBean sessionBean;
  private static final String BULLETINS_LIST_NAME = "bulletins";
  private static final String BULLETINS_HTML_PAGE_NAME = "bulletins";

  @GetMapping(value = "/")
  public ModelAndView getLoginPage() {

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("login");

    return modelAndView;
  }

  @GetMapping(value = "/registration")
  public ModelAndView getRegistrationPage() {

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("register");

    return modelAndView;
  }

  @PostMapping(
      value = "/bulletins",
      params = {"email", "password"})
  public ModelAndView getBulletinsPage(
      @RequestParam("email") String email, @RequestParam("password") String password) {

    ModelAndView modelAndView = new ModelAndView();
    User user = userService.login(email, password);
    sessionBean.setUser(user);
    modelAndView.addObject("pagesCount", bulletinService.pagesCount());

    modelAndView.addObject(BULLETINS_LIST_NAME, bulletinService.getPageOfBulletins("1"));

    modelAndView.setViewName(BULLETINS_HTML_PAGE_NAME);

    return modelAndView;
  }

  @PostMapping(
      value = "/bulletins",
      params = {"email", "password", "firstName", "lastName"})
  public ModelAndView getBulletinsPage(
      @RequestParam("email") String email,
      @RequestParam("password") String password,
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName) {

    ModelAndView modelAndView = new ModelAndView();
    User user = userService.register(email, password, firstName, lastName);
    sessionBean.setUser(user);
    modelAndView.addObject(BULLETINS_LIST_NAME, bulletinService.getPageOfBulletins("1"));
    modelAndView.addObject("pagesCount", bulletinService.pagesCount());
    modelAndView.setViewName(BULLETINS_HTML_PAGE_NAME);

    return modelAndView;
  }
}
