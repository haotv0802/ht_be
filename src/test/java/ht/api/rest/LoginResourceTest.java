package ht.api.rest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by haho on 3/3/2017.
 */
@WebAppConfiguration
@ContextConfiguration(
    locations = {
         "/config/spring-mvc.xml"
        ,"/config/spring-mvc-test.xml"
    })
public class LoginResourceTest extends DocumentationBase {
  protected MockMvc mockMvc;

  @Test
  public void testLogin() throws Exception {
    mockMvc
        .perform(get("/svc/login")
            .header("Accept-Language", "en")
        )
        .andExpect(status().is(200))
        ;

  }
}
