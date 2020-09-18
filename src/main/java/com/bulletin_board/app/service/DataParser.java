package com.bulletin_board.app.service;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class DataParser {

  public Optional<Integer> parseInt(String val) {
    try {
      return Optional.of(Integer.parseInt(val));
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }



}
