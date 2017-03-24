package ht.api.rest.admin.rooms;

import java.util.List;

/**
 * Created by haho on 3/22/2017.
 */
public class RoomTypeBean {
  private int id;
  private String name;
  private List<RoomTypeImageBean> images;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<RoomTypeImageBean> getImages() {
    return images;
  }

  public void setImages(List<RoomTypeImageBean> images) {
    this.images = images;
  }
}
