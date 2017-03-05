package ht.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import ht.auth.Credentials;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

  @Test
  public void testLogin() throws Exception {
    Credentials c = new Credentials();
    c.setUserName("admin");
    c.setUserPass("admin");
    c.setUserLang("AN");

    mockMvc
        .perform(post("/svc/login")
            .header("Accept-Language", "en")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(c))
        )
        .andExpect(status().is(200))
        ;
  }

  @Test
  public void testHello() throws Exception {
    // in the branch
    mockMvc
        .perform(post("/svc/hello")
            .header("Accept-Language", "en")
        )
        .andExpect(status().is(200))
    ;
  }
}
