package com.alex.fsm;

public class Util {
  private static final String EMAIL_REGEX =
      "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
  private static final String ID_REGEX = "^\\d{15}$";

  public static boolean verifyEmail(String emailAddress) {
    return emailAddress != null && emailAddress.matches(EMAIL_REGEX);
  }

  public static boolean verifyIdCode(String idCode) {
    return idCode != null && idCode.matches(ID_REGEX);
  }

  public static boolean verifyCredential(String userId, String userSecret) {
    return userId != null && userSecret != null && userSecret.length() > 5 && userId.length() > 2;
  }
}
