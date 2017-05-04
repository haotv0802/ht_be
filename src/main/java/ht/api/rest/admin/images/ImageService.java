package ht.api.rest.admin.images;

import ht.api.rest.admin.images.beans.Image;
import ht.api.rest.admin.images.interfaces.IImageDao;
import ht.api.rest.admin.images.interfaces.IImageService;
import io.jsonwebtoken.lang.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by haho on 3/22/2017.
 */
@Service("adminImageService")
public class ImageService implements IImageService {

  protected final Logger logger = LogManager.getLogger(getClass());

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
  public ResponseEntity getImageFileById(
      Integer id,
      HttpServletResponse response,
      String ext
  ) throws IOException {
    String path = "src/main/images/" + id + "." + ext;
    File file;
    try {
      file = new File(path);
      if (!file.exists()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception ex) {
      logger.debug("message: " + ex.getMessage());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    if (null == file || !file.exists()) {
//      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    BufferedInputStream buffIn = null;
    BufferedOutputStream buffOut = null;
    try {
      buffIn = new BufferedInputStream(new FileInputStream(file));
      buffOut = new BufferedOutputStream(response.getOutputStream());

      byte[] bytes = new byte[1024];
      int count;
      while ((count = buffIn.read(bytes)) > 0) {
        buffOut.write(bytes, 0, count);
        buffOut.flush();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      buffOut.close();
      buffIn.close();
    }
//    byte[] data = Files.readAllBytes(file.toPath());
//    out.write(data);
    String extension = ext.toUpperCase();
    HttpHeaders headers = new HttpHeaders();
    if ("JPEG".equals(extension) || "JPG".equals(extension)) {
      headers.setContentType(MediaType.IMAGE_JPEG);
    } else if ("PDF".equals(extension)) {
      headers.setContentType(MediaType.parseMediaType("application/pdf"));
    } else if ("XML".equals(extension)) {
      headers.setContentType(new MediaType("application", "xml"));
    } else if ("XLS".equals(extension) || "XLSX".equals(extension)) {
      headers.setContentType(new MediaType("application", "vnd.ms-excel"));
    } else if ("DOC".equals(extension) || "DOCX".equals(extension)) {
      headers.setContentType(new MediaType("application", "msword"));
    }
    return new ResponseEntity(headers, HttpStatus.OK);
  }

  @Override
  public void updateImage(int id, MultipartFile uploadedFile) throws IOException {
    String dir = "src/main/images";
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