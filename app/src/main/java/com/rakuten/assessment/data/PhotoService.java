package com.rakuten.assessment.data;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface PhotoService {
  public static final String API_KEY = "fee10de350d1f31d5fec0eaf330d2dba";
  public static final Map<String, String> DEFAULT_OPTIONS = new HashMap<String, String>() {{
    put("api_key", API_KEY);
    put("format", "json");
  }};

  // http://yuriy.me/rakuten-rewards/photos.json?method=flickr.photos.getRecent&api_key=fee10de350d1f31d5fec0eaf330d2dba&page=1&format=json&nojsoncallback=true&safe_search=true
  @GET("rakuten-rewards/photos.json?method=flickr.photos.getRecent&api_key=fee10de350d1f31d5fec0eaf330d2dba&page=1&format=json&nojsoncallback=true&safe_search=true")
  Call<ServiceResponse.GetPhotoResponse> getPhotos();
}
