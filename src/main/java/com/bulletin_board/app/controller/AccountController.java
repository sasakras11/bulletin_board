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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

  private final SessionBean sessionBean;
  private final UserService userService;
  private final BulletinService bulletinService;

  @GetMapping(value = "/account")
  public ModelAndView account() {

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("user", sessionBean.getUser());
    modelAndView.setViewName("account");

    return modelAndView;
  }

  @PostMapping(value = "/editAccount")
  public ModelAndView editAccount(
      @RequestParam("email") String email,
      @RequestParam("password") String password,
      @RequestParam("firstName") String firstName) {
    ModelAndView modelAndView = new ModelAndView();
    User user = userService.editUser(sessionBean.getUser().getId(), email, password, firstName);
    sessionBean.setUser(user);
    modelAndView.addObject("user", sessionBean.getUser());
    modelAndView.setViewName("account");
    return modelAndView;
  }



}
