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

  @GetMapping(value = "/")
  public ModelAndView getLoginPage() {

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("login");

    return modelAndView;
  }

  @PostMapping(value = "/bulletins")
  public ModelAndView loggingIn(
      @RequestParam("email") String email, @RequestParam("password") String password) {

    ModelAndView modelAndView = new ModelAndView();
    User user = userService.login(email, password);
    sessionBean.setUser(user);
    modelAndView.addObject("bulletins", bulletinService.getPageOfBulletins("1"));

    modelAndView.setViewName("bulletins");

    return modelAndView;
  }
}
