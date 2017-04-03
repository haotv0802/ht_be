package ht.api.rest.admin.individuals;

import java.util.Date;
import java.util.List;

/**
 * Created by haho on 3/22/2017.
 */
public class IndividualPresenter {
  private Integer id;
  private String firstName;
  private String middleName;
  private String lastName;
  private Date birthday;
  private String gender;
  private String email;
  private String phoneNumber;
  private Integer imageId;

  private String userName;
  private List<String> roles;

  public IndividualPresenter(IndividualModel model) {
    this.id = model.getId();
    this.firstName = model.getFirstName();
    this.middleName = model.getMiddleName();
    this.lastName = model.getLastName();
    this.birthday = model.getBirthday();
    this.gender = model.getGender();
    this.email = model.getEmail();
    this.phoneNumber = model.getPhoneNumber();
    this.imageId = model.getImageId();
    this.userName = model.getUserName();
    this.roles = model.getRoles();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Integer getImageId() {
    return imageId;
  }

  public void setImageId(Integer imageId) {
    this.imageId = imageId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}
