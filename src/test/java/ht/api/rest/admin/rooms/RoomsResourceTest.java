package ht.api.rest.admin.rooms;

import ht.api.rest.BaseDocumentation;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by haho on 3/22/2017.
 */
public class RoomsResourceTest extends BaseDocumentation {

  @Test
  public void testGetRoomTypes() throws Exception {
    mockMvc
        .perform(get("/svc/admin/rooms/roomTypes")
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
        )
        .andExpect(status().is(200))
    ;
  }
}
