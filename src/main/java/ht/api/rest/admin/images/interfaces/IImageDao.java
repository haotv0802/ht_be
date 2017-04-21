package ht.api.rest.admin.images.interfaces;

import ht.api.rest.admin.images.beans.Image;

import java.util.List;

/**
 * Created by haho on 3/28/2017.
 */
public interface IImageDao {
  List<Image> getImages();

  Image getImageById(Integer id);
}
