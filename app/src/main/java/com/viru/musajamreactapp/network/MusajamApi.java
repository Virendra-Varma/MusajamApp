package com.viru.musajamreactapp.network;

import com.viru.musajamreactapp.model.Project;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by viru on 4/2/2017.
 */

interface MusajamApi {
  @GET("kickstarter") Call<List<Project>> getAllProjects();
}
