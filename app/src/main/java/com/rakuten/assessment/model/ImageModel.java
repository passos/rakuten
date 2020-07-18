package com.rakuten.assessment.model;

import android.annotation.SuppressLint;

import com.rakuten.assessment.utils.Log;

public class ImageModel {
  public String id;
  public String owner;
  public String secret;
  public String server;
  public int farm;
  public String title;
  public int ispublic;
  public int isfriend;
  public int isfamily;

  private String url;

  @SuppressLint("DefaultLocale")
  public String getUrl() {
    if (url == null || url.length() == 0) {
      url = String.format("https://farm%d.staticflickr.com/%s/%s_%s.jpg", farm, server, id, secret);
    }
    return url;
  }
}
