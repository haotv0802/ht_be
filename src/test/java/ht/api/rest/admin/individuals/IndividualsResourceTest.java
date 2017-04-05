package ht.api.rest.admin.individuals;

import ht.api.rest.BaseDocumentation;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by haho on 04/03/2017.
 */
public class IndividualsResourceTest extends BaseDocumentation {

  @Test
  public void testGetIndividuals() throws Exception {
    mockMvc
        .perform(get("/svc/admin/individuals")
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
        )
        .andExpect(status().is(200))
    ;
  }

  @Test
  public void testIsUserNameExisting() throws Exception {
    mockMvc
        .perform(get("/svc/admin/individuals/isUserNameExisting/haho")
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
        )
        .andExpect(status().is(200))
    ;
  }
}
