package ht.api.rest;

import ht.auth.*;
import io.jsonwebtoken.lang.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by haho on 3/3/2017.
 */
@RestController
@RequestMapping(path = "/svc")
public class LoginResource {

  private static final Logger logger = LogManager.getLogger(LoginResource.class);

  @Autowired
  private TokenAuthenticationService tokenAuthenticationService;

  @Autowired
  private LoginDao loginDao;

  @PostMapping("/login")
  public void login(@RequestBody Credentials credentials, HttpServletRequest request, HttpServletResponse response) {
    Assert.notNull(credentials);
    logger.info("In Resource");
    CredentialsResult result = loginDao.checkCredentials(credentials);

    UserDetailsImpl userDetails = loginDao.findOneByUsername(credentials.getUserName());
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), null);

    tokenAuthenticationService.addAuthentication(response, authentication);
  }

  @PostMapping("/hello")
  public void hello() {
    logger.info("In Hello");
  }
}
