package ht.api.rest.admin.rooms;

import ht.api.rest.BaseDocumentation;
import ht.api.rest.admin.rooms.beans.RoomTypeBean;
import org.json.JSONArray;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.annotations.Test;

import java.util.List;

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

  @Test
  public void testUpdateRoomTypes() throws Exception {
    MvcResult result = mockMvc
        .perform(get("/svc/admin/rooms/roomTypes")
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
        )
        .andExpect(status().is(200))
        .andReturn()
    ;

    String resultAsString = result.getResponse().getContentAsString();

    JSONArray array = new JSONArray(resultAsString);
    List<RoomTypeBean> roomTypes = this.objectMapper.convertValue(array, List.class);
  }
}
