package ht.api.rest.admin.images;

import ht.api.rest.admin.BaseAdminResource;
import ht.api.rest.admin.images.beans.Image;
import ht.api.rest.admin.images.interfaces.IImageService;
import ht.auth.UserDetailsImpl;
import ht.common.beans.HeaderLang;
import ht.transaction.ImxTransactionCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by haho on 3/22/2017.
 */
@RestController
public class ImagesResource extends BaseAdminResource {

  private final IImageService imageService;

  private final ImxTransactionCommit imxTransactionCommit;

  @Autowired
  public ImagesResource(
      @Qualifier("adminImageService") IImageService imageService,
      ImxTransactionCommit imxTransactionCommit
  ) {
    Assert.notNull(imageService);
    Assert.notNull(imxTransactionCommit);
    this.imageService = imageService;
    this.imxTransactionCommit = imxTransactionCommit;
  }

  @GetMapping("/images")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<Image> getImages(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @HeaderLang String lang) {
    return imageService.getImages();
  }

  @GetMapping("/images/{id}/info")
  @PreAuthorize("hasAuthority('ADMIN')")
  public Image getImagesById(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @HeaderLang String lang,
      @PathVariable("id") Integer id
  ) {
    return imageService.getImageById(id);
  }

  @PostMapping(value = "/images/{id}/updateImage", consumes = "multipart/form-data")
  public ResponseEntity updateImage(
    @AuthenticationPrincipal UserDetailsImpl userDetails,
    @HeaderLang String lang,
    @RequestParam("imageFile") MultipartFile imageFile,
    @PathVariable("id") Integer id
  ){
//    this.imageService.updateImage(image, uploadedFile);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/images/updateImageInfo")
  public ResponseEntity updateImageInfo(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @HeaderLang String lang,
      @RequestBody Image image,
      HttpServletRequest request,
      HttpSession httpSession
  ){
    this.imageService.updateImageInfo(image);
    imxTransactionCommit.permitCommit(httpSession);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
