package ht.api.rest.admin.images;

import ht.api.rest.admin.images.beans.Image;
import ht.api.rest.admin.images.interfaces.IImageDao;
import ht.api.rest.admin.images.interfaces.IImageService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
  public void updateImage(int id, MultipartFile uploadedFile) throws IOException {
    String dir = "src/images";
//    String dir = "src/test/resources/files2";
    BufferedInputStream bis = null;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Files.createDirectories(Paths.get(dir));
    try {
      bis = new BufferedInputStream(uploadedFile.getInputStream());
      fos = new FileOutputStream(dir + "/" + id + ".jpg");
      bos = new BufferedOutputStream(fos);
      byte[] bytes = new byte[1024];
      int count;
      while ((count = bis.read(bytes)) > 0) {
        bos.write(bytes, 0, count);
        bos.flush();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      bos.close();
      fos.close();
      bis.close();
    }
  }

  @Override
  public void updateImageInfo(Image image) {
    this.imageDao.updateImage(image);
  }
}