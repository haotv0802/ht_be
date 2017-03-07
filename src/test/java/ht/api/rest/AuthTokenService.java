package ht.api.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.io.PrintWriter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Property of CODIX Bulgaria EAD
 * Created by vtodorov
 * Date:  10/09/2016 Time: 12:54 PM
 */
@Service
public class AuthTokenService implements IAuthTokenService {

  private static final Logger logger = LogManager.getLogger(AuthTokenService.class);

  private String authToken;

  @Autowired
  public AuthTokenService(
    WebApplicationContext wac,
    @Qualifier("springSessionRepositoryFilter") Filter sessionRepositoryFilter,
    @Qualifier("txFilter") Filter txFilter
    ) throws Exception
  {
    final PrintWriter printWriter = IoBuilder.forLogger(logger).buildPrintWriter();

    MockMvc mockMvc
      = MockMvcBuilders
         .webAppContextSetup(wac)
           .addFilter(sessionRepositoryFilter)
           .addFilter(txFilter)
         .apply(springSecurity())
         .alwaysDo(print(printWriter))
        .build();

    authToken = TestUtils.performLogin(mockMvc, "admin", "admin");
  }

  @Override
  public String getAuthToken(){
    return authToken;
  }
}
