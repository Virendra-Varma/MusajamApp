package com.viru.musajamreactapp.events;

import com.viru.musajamreactapp.model.FailedResponse;

public class FailedBaseResponse {
  private FailedResponse failedResponse;
  private String reason;

  public FailedResponse getFailedResponse() {
    return failedResponse;
  }

  public String getReason() {
    return reason;
  }

  public FailedBaseResponse(FailedResponse failedResponse, String reason) {

    this.failedResponse = failedResponse;
    this.reason = reason;
  }
}
