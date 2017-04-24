package ht.api.rest.admin.images;

import ht.api.rest.BaseDocumentation;
import ht.api.rest.admin.images.beans.Image;
import ht.transaction.Transaction;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by haho on 3/22/2017.
 */
public class ImagesResourceTest extends BaseDocumentation {

  private String txId;

//  @BeforeMethod
//  public void beforeTest() throws Exception {
//    //start transaction
//    txId = objectMapper
//        .readValue(mockMvc
//            .perform(RestDocumentationRequestBuilders.post("/svc/transactions").header("X-AUTH-TOKEN", authTokenService.getAuthToken()))
//            .andExpect(status().isCreated())
//            .andReturn()
//            .getResponse()
//            .getContentAsString(), Transaction.class)
//        .getTxId();
//
//  }
//
//  @AfterMethod
//  public void afterTest() throws Exception {
//    //"rollback" transaction
//    mockMvc
//        .perform(delete("/svc/transactions")
//            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
//            .header("txId", txId))
//        .andExpect(status().isOk());
//  }

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
        .perform(get("/svc/admin/images/{id}/info", 1111)
            .header("Accept-Language", "en")
//            .header("txId", txId)
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
        )
        .andExpect(status().is(200))
    ;
  }

  @Test
  public void testUpdateImage() throws Exception {
    MvcResult result = mockMvc
        .perform(get("/svc/admin/images/{id}/info", 1)
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
        )
        .andExpect(status().is(200))
        .andReturn()
    ;

    JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
    Image image = objectMapper.readValue(result.getResponse().getContentAsString(), Image.class);

//    MockMultipartFile imageInfo = new MockMultipartFile("image", "", "application.json", result.getResponse().getContentAsString().getBytes());

    MockMultipartFile uploadedFile = new MockMultipartFile("imageFile", "filename.txt", "text/plain", "some xml".getBytes());

    mockMvc
//        .perform(post("/svc/admin/images/update")
        .perform(RestDocumentationRequestBuilders.fileUpload("/svc/admin/images/{id}/updateImage", image.getId())
            .file(uploadedFile)
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
            .content(objectMapper.writeValueAsString(image))
            .contentType(MediaType.MULTIPART_FORM_DATA)
        )
        .andExpect(status().is(200))
    ;
  }

  @Test
  public void testUpdateImageInfo() throws Exception {
    MvcResult result = mockMvc
        .perform(get("/svc/admin/images/{id}/info", 1)
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
        )
        .andExpect(status().is(200))
        .andReturn()
        ;

    JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
    Image image = objectMapper.readValue(result.getResponse().getContentAsString(), Image.class);

    mockMvc
//        .perform(post("/svc/admin/images/update")
        .perform(post("/svc/admin/images/updateImageInfo")
            .header("Accept-Language", "en")
            .header("X-AUTH-TOKEN", authTokenService.getAuthToken())
            .content(objectMapper.writeValueAsString(image))
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().is(200))
    ;
  }
}