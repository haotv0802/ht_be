package ht.api.rest.admin.images;

import ht.api.rest.BaseDocumentation;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by haho on 3/22/2017.
 */
public class ImagesResourceTest extends BaseDocumentation {

  @Test
  public void testGetImages() throws Exception {
    mockMvc
        .perform(get("/svc/admin/images")
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
        )
        .andExpect(status().is(200))
    ;
  }
  @Test
  public void testGetImageById() throws Exception {
    mockMvc
        .perform(get("/svc/admin/images/{id}", 1111)
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
        )
        .andExpect(status().is(200))
    ;
  }
}