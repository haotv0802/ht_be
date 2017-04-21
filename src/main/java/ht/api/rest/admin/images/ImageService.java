package ht.api.rest.admin.images;

import ht.api.rest.admin.images.beans.Image;
import ht.api.rest.admin.images.interfaces.IImageDao;
import ht.api.rest.admin.images.interfaces.IImageService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by haho on 3/22/2017.
 */
@Service("adminImageService")
public class ImageService implements IImageService {

  private final IImageDao imageDao;

  @Autowired
  public ImageService(@Qualifier("adminImageDao") IImageDao imageDao) {
    Assert.notNull(imageDao);
    this.imageDao = imageDao;
  }

  @Override
  public List<Image> getImages() {
    return imageDao.getImages();
  }

  @Override
  public Image getImageById(Integer id) {
    return imageDao.getImageById(id);
  }

  @Override
  public void updateImage(Image image, MultipartFile multipartFile) {
    // TODO save the file to folder.

    this.imageDao.updateImage(image);
  }
}
