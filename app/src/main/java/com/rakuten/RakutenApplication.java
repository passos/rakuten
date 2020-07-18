package com.rakuten;

import android.app.Application;

import com.rakuten.assessment.AppRuntime;

public class RakutenApplication extends Application {
  public RakutenApplication() {
    super();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    AppRuntime.getInstance().init(this);
  }
}
