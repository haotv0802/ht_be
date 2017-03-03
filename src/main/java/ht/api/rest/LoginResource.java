package ht.api.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by haho on 3/3/2017.
 */
@RestController
@RequestMapping(path = "/svc")
public class LoginResource {

  private static final Logger logger = LogManager.getLogger(LoginResource.class);

  @GetMapping("/login")
  public void login() {
    logger.info("In Resource");
  }
}
