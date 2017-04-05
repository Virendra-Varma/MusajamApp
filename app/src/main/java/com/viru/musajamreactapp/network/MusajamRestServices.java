package com.viru.musajamreactapp.network;

import android.support.annotation.NonNull;

/**
 * Created by viru on 4/2/2017.
 */

class MusajamRestServices extends ServiceGenerator {
  @Override protected String getBaseUrl() {
    return "http://starlord.hackerearth.com/";
  }

  @Override public <S> S createService(@NonNull Class<S> apiClass) {
    return super.createService(apiClass, new LoggingInterceptor());
  }
}
