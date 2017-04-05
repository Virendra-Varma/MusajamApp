package com.viru.musajamreactapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FailedResponse {

  @SerializedName("status") @Expose public String status;
  @SerializedName("msg") @Expose public String msg;

  @Override public String toString() {
    return "FailedResponse{" +
        "msg='" + msg + '\'' +
        ", status='" + status + '\'' +
        '}';
  }

  public String getStatus() {
    return status;
  }

  public String getMsg() {
    return msg;
  }
}
