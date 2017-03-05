package ht.api.rest;

import ht.auth.Credentials;
import ht.auth.CredentialsResult;
import ht.auth.LoginDao;
import io.jsonwebtoken.lang.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by haho on 3/3/2017.
 */
@RestController
@RequestMapping(path = "/svc")
public class LoginResource {

  private static final Logger logger = LogManager.getLogger(LoginResource.class);

  @Autowired
  private LoginDao loginDao;

  @PostMapping("/login")
  public void login(@RequestBody Credentials credentials, HttpServletRequest request) {
    Assert.notNull(credentials);
    logger.info("In Resource");
    CredentialsResult result = loginDao.checkCredentials(credentials);
  }

  @PostMapping("/hello")
  public void hello() {
    logger.info("In Hello");
  }
}
