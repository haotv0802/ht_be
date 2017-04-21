package ht.api.rest.admin.images;

import ht.api.rest.admin.BaseAdminResource;
import ht.api.rest.admin.images.beans.Image;
import ht.api.rest.admin.images.interfaces.IImageService;
import ht.auth.UserDetailsImpl;
import ht.common.beans.HeaderLang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by haho on 3/22/2017.
 */
@RestController
public class ImagesResource extends BaseAdminResource {

  private final IImageService imageService;

  @Autowired
  public ImagesResource(@Qualifier("adminImageService") IImageService imageService) {
    Assert.notNull(imageService);
    this.imageService = imageService;
  }

  @GetMapping("/images")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<Image> getImages(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @HeaderLang String lang) {
    return imageService.getImages();
  }

  @GetMapping("/images/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public Image getImagesById(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @HeaderLang String lang,
      @PathVariable("id") Integer id
  ) {
    return imageService.getImageById(id);
  }

  @PostMapping(name = "/images/update", consumes = "multipart/form-data")
  public ResponseEntity updateImage(
    @AuthenticationPrincipal UserDetailsImpl userDetails,
    @HeaderLang String lang,
//    @RequestPart("image") Image image,
    @RequestParam("uploadedFile") MultipartFile uploadedFile
  ){
//    this.imageService.updateImage(image, uploadedFile);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @GetMapping(name = "/images/update2/update")
  public ResponseEntity updateImage2(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @HeaderLang String lang,
      @PathVariable("id") Integer id
  ){
//    this.imageService.updateImage(image, uploadedFile);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

}
