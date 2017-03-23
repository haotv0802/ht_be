package ht.api.rest.admin.rooms;

/**
 * Created by haho on 3/22/2017.
 */
public class RoomTypeBean {
  private String name;
  private String imageURL;
  private String imageInfo;
  private String imageId;

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

  public String getImageId() {
    return imageId;
  }

  public void setImageId(String imageId) {
    this.imageId = imageId;
  }

  public String getImageInfo() {
    return imageInfo;
  }

  public void setImageInfo(String imageInfo) {
    this.imageInfo = imageInfo;
  }
}
