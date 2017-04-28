package ht.transaction.beans;

import java.util.Date;

/**
 * Created by haho on 3/27/2017.
 * This image is for Individuals & Promotions
 */
public class TransactionCommit {
  private int id;
  private String authToken;
  private Boolean possibleToCommit;
  private Date expDate;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public Boolean getPossibleToCommit() {
    return possibleToCommit;
  }

  public void setPossibleToCommit(Boolean possibleToCommit) {
    this.possibleToCommit = possibleToCommit;
  }

  public Date getExpDate() {
    return expDate;
  }

  public void setExpDate(Date expDate) {
    this.expDate = expDate;
  }
}
