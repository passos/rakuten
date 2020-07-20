package com.rakuten.assessment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rakuten.assessment.AppRuntime;
import com.rakuten.assessment.model.ImageModel;
import com.rakuten.assessment.utils.Log;

import java.util.List;

public class ImageListViewModel extends ViewModel {
  private static final String TAG = Log.tag(ImageListViewModel.class);
  private MutableLiveData<ImageModel> activeImage = new MutableLiveData<>();

  public LiveData<List<ImageModel>> getImages() {
    Log.d(TAG, "getting images");
    return AppRuntime.getInstance().getRepository().getImages();
  }

  public LiveData<ImageModel> getActiveImage() {
    return activeImage;
  }

  public void setActiveImage(ImageModel image) {
    activeImage.setValue(image);
  }
}
