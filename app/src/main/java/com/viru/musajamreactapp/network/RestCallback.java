package com.viru.musajamreactapp.network;

import android.support.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RestCallback<T, E> extends NetworkDataService<T, E> implements Callback<T> {
  private E object;

  protected RestCallback(@Nullable E obj) {
    this.object = obj;
  }

  protected RestCallback() {
    this(null);
  }

  @Override public void onResponse(Call<T> call, Response<T> response) {
    if (response.isSuccessful()) {
      sendSuccessResponse(response.body());
    } else {
      if (object != null) {
        sendErrorResponse(ErrorUtils.parseError(response, object), null);
      } else {
        sendErrorResponse(null, response.errorBody().toString());
      }
    }
  }

  @Override public void onFailure(Call<T> call, Throwable t) {
    if (checkError(t)) {
      sendErrorResponse(null, t.getMessage());
    } else {
      sendErrorResponse(null, UNKNOWN_REASON);
    }
  }
}
