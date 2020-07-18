package com.rakuten.assessment.data;

import com.rakuten.assessment.model.ImageModel;

import java.util.List;

public class ServiceResponse {
  public static class GetPhotoResponse {
    Photos photos;
  }

  public static class Photos {
    int page;
    int pages;
    int perpage;
    int total;
    List<ImageModel> photo;
  }
}
