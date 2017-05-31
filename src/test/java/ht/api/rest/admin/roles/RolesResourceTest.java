package ht.api.rest.admin.roles;

import ht.api.rest.BaseDocumentation;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by haho on 3/22/2017.
 */
public class RolesResourceTest extends BaseDocumentation {

  @Test
  public void testGetRoles() throws Exception {
    mockMvc
        .perform(get("/svc/admin/roles")
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
        )
        .andExpect(status().is(200))
    ;
  }

  @Test
  public void testGetRolesInfo() throws Exception {
    mockMvc
        .perform(get("/svc/admin/roles/keyValuePair")
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
        )
        .andExpect(status().is(200))
    ;
  }
}
