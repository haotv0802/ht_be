package ht.api.rest.admin.images;

import com.fasterxml.jackson.databind.ObjectMapper;
import ht.api.rest.BaseDocumentation;
import ht.api.rest.admin.images.beans.Image;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

  @Test
  public void testUpdateImage() throws Exception {
    MvcResult result = mockMvc
        .perform(get("/svc/admin/images/{id}", 1)
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
        )
        .andExpect(status().is(200))
        .andReturn()
    ;

    ObjectMapper objectMapper = new ObjectMapper();
    JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
    Image image = objectMapper.readValue(result.getResponse().getContentAsString(), Image.class);

    MockMultipartFile imageInfo = new MockMultipartFile("image", "", "application.json", result.getResponse().getContentAsString().getBytes());

    MockMultipartFile uploadedFile = new MockMultipartFile("uploadedFile", "filename.txt", "text/plain", "some xml".getBytes());

    mockMvc
//        .perform(post("/svc/admin/images/update")
        .perform(RestDocumentationRequestBuilders.fileUpload("/svc/admin/images/update")
            .file(uploadedFile)
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
            .contentType(MediaType.MULTIPART_FORM_DATA)
        )
        .andExpect(status().is(200))
    ;
  }

  @Test
  public void testUpdateImage2() throws Exception {
//    MvcResult result = mockMvc
//        .perform(get("/svc/admin/images/{id}", 1)
//            .header("Accept-Language", "en")
//            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
//        )
//        .andExpect(status().is(200))
//        .andReturn()
//        ;
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
//    Image image = objectMapper.readValue(result.getResponse().getContentAsString(), Image.class);
//
//    MockMultipartFile imageInfo = new MockMultipartFile("image", "", "application.json", result.getResponse().getContentAsString().getBytes());
//
//    MockMultipartFile uploadedFile = new MockMultipartFile("uploadedFile", "filename.txt", "text/plain", "some xml".getBytes());

    mockMvc
        .perform(get("/svc/admin/images/update2/update")
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
        )
        .andExpect(status().is(200))
    ;
  }
}