package ht.api.rest.admin.rooms;

/**
 * Created by haho on 3/22/2017.
 */
public class RoomTypeBean {
  private int id;
  private String name;
  private String imageURL;

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

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }
}
