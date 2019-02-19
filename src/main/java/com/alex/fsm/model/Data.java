package com.alex.fsm.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Data implements Serializable {

  private String userId;
  private String userSecret;
  private String emailAddress;
  private String idCode;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserSecret() {
    return userSecret;
  }

  public void setUserSecret(String userSecret) {
    this.userSecret = userSecret;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getIdCode() {
    return idCode;
  }

  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }
}
