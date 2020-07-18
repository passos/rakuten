package com.rakuten.assessment.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rakuten.assessment.AppRuntime;
import com.rakuten.assessment.model.ImageModel;
import com.rakuten.assessment.utils.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
  private static final String TAG = Log.tag(DataRepository.class);

  public DataRepository() {
  }

  public LiveData<List<ImageModel>> getImages() {
    MutableLiveData<List<ImageModel>> result = new MutableLiveData<>();

    Log.d(TAG, "getting images");
    // TODO: add paging support
    AppRuntime.getInstance()
      .getPhotoService()
      .getPhotos()
      .enqueue(new Callback<ServiceResponse.GetPhotoResponse>() {
        @Override
        public void onResponse(Call<ServiceResponse.GetPhotoResponse> call, Response<ServiceResponse.GetPhotoResponse> response) {
          Log.d(TAG, "request done: %s", response.toString());
          if (response.body() != null) {
            result.setValue(response.body().photos.photo);
          }
        }

        @Override
        public void onFailure(Call<ServiceResponse.GetPhotoResponse> call, Throwable t) {
          Log.d(TAG, "request failed: %s", t.getMessage());
        }
      });

    return result;
  }
}
