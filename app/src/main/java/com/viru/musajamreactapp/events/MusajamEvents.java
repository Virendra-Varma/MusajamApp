package com.viru.musajamreactapp.events;

import com.viru.musajamreactapp.model.FailedResponse;
import com.viru.musajamreactapp.model.Project;
import java.util.List;

/**
 * Created by viru on 4/2/2017.
 */

public class MusajamEvents {

  public static class AllProjectFatched{
    private List<Project> projects;

    public AllProjectFatched(List<Project> projects) {
      this.projects = projects;
    }

    public List<Project> getProjects() {
      return projects;
    }
  }

  public static class ProjectFatchedFailed{
    private FailedResponse failedResponse;
    private String reason;

    public ProjectFatchedFailed(FailedResponse failedResponse, String reason) {
      this.failedResponse = failedResponse;
      this.reason = reason;
    }

    public FailedResponse getFailedResponse() {
      return failedResponse;
    }

    public String getReason() {
      return reason;
    }
  }
}
