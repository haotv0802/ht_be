package ht.api.rest.testing;

import ht.auth.UserDetailsImpl;
import ht.common.beans.HeaderLang;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by haho on 3/13/2017.
 */
@RestController
@RequestMapping(path = "/svc")
public class TestingResource {
  private static final Logger logger = LogManager.getLogger(TestingResource.class);

  @Autowired
  private TestingService testingService;

  @GetMapping("/testing")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<AuthorityBean> getAuthorities(
      @AuthenticationPrincipal UserDetailsImpl userDetails
      ,@HeaderLang String lang) {
    return testingService.getAuthorities();
  }
}
