package ht.api.rest.admin.images;

import ht.api.rest.admin.BaseAdminResource;
import ht.api.rest.admin.images.beans.Image;
import ht.api.rest.admin.images.interfaces.IImageService;
import ht.auth.UserDetailsImpl;
import ht.common.beans.HeaderLang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
       @AuthenticationPrincipal UserDetailsImpl userDetails
      ,@HeaderLang String lang) {
    return imageService.getImages();
  }
}
