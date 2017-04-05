package com.viru.musajamreactapp.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.viru.musajamreactapp.events.BusProvider;
import com.viru.musajamreactapp.events.MusajamEvents;
import com.viru.musajamreactapp.model.FailedResponse;
import com.viru.musajamreactapp.model.Project;
import java.util.List;

import retrofit2.Call;

/**
 * Created by viru on 3/18/2017.
 */

public class RestDataService {
  private MusajamRestServices services;

  public RestDataService() {
    this.services = new MusajamRestServices();
  }

  public Call<List<Project>> getAllProjects(){
    Call<List<Project>> call = services.createService(MusajamApi.class).getAllProjects();
    call.enqueue(new RestCallback<List<Project>, FailedResponse>(new FailedResponse()) {
      @Override public void sendSuccessResponse(@NonNull List<Project> responseObj) {
        BusProvider.getInstance().post(new MusajamEvents.AllProjectFatched(responseObj));
      }

      @Override
      public void sendErrorResponse(@Nullable FailedResponse errorObj, @Nullable String reason) {
        BusProvider.getInstance().post(new MusajamEvents.ProjectFatchedFailed(errorObj, reason));
      }
    });
    return call;
  }
}
