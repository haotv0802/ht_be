package ht.api.rest.admin.images.interfaces;

import ht.api.rest.admin.images.beans.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by haho on 3/28/2017.
 */
public interface IImageService {
  List<Image> getImages();

  Image getImageById(Integer id);

  void updateImage(Image image, MultipartFile multipartFile);
}
