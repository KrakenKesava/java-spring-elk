package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Random;

@Controller
public class LogController {
  private static final Logger logger = LoggerFactory.getLogger(LogController.class);

  @GetMapping("/")
  public String home() {
    logger.info("Home page accessed");
    return "index";
  }

  @GetMapping("/success")
  @ResponseBody
  public String success() {
    logger.info("Success endpoint hit");
    return "Success: 200 OK";
  }

  @GetMapping("/not-found")
  @ResponseBody
  public String notFound(HttpServletResponse response) {
    logger.warn("404 endpoint hit");
    response.setStatus(HttpStatus.NOT_FOUND.value());
    return "Error: 404 Not Found";
  }

  @GetMapping("/server-error")
  @ResponseBody
  public String serverError() {
    logger.error("500 endpoint hit");
    throw new RuntimeException("Simulated 500 Error");
  }

  @GetMapping("/random")
  @ResponseBody
  public String random(HttpServletResponse response) {
    int choice = new Random().nextInt(3);
    switch (choice) {
      case 0: return success();
      case 1: return notFound(response);
      default: return serverError();
    }
  }

  @PostMapping("/feedback")
  @ResponseBody
  public String submitFeedback(@RequestParam String feedback) {
    logger.info("Feedback received: {}", feedback);
    return "Thank you for your feedback: " + feedback;
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public String handleError(Exception ex, HttpServletResponse response) {
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    return "Internal Error: 500 - " + ex.getMessage();
  }
}
