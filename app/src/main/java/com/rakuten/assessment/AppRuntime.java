package com.rakuten.assessment;

import com.rakuten.RakutenApplication;
import com.rakuten.assessment.data.DataRepository;
import com.rakuten.assessment.data.PhotoService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppRuntime {
  private volatile static AppRuntime INSTANCE = null;

  public static AppRuntime getInstance() {
    if (INSTANCE == null) {
      synchronized (AppRuntime.class) {
        if (INSTANCE == null) {
          INSTANCE = new AppRuntime();
        }
      }
    }
    return INSTANCE;
  }

  private RakutenApplication application;
  private PhotoService photoService;
  private DataRepository repository;
  private Executor executor;

  private AppRuntime() {
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(Consts.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();
    photoService = retrofit.create(PhotoService.class);

    executor = Executors.newFixedThreadPool(Consts.THREAD_POOL_SIZE);
    
    repository = new DataRepository();
  }

  public void init(RakutenApplication application) {
    this.application = application;
  }

  public RakutenApplication getApplication() {
    return application;
  }

  public PhotoService getPhotoService() {
    return photoService;
  }

  public Executor getExecutor() {
    return executor;
  }

  public DataRepository getRepository() {
    return repository;
  }
}
