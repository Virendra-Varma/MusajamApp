package com.viru.musajamreactapp.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class NetworkDataService<T, E> {
  static final String UNKNOWN_REASON = "unknown reason";

  public abstract void sendSuccessResponse(@NonNull T responseObj);

  public abstract void sendErrorResponse(@Nullable E errorObj, @Nullable String reason);

  boolean checkError(Throwable t) {
    return t != null && t.getMessage() != null && !t.getMessage().isEmpty();
  }
}
