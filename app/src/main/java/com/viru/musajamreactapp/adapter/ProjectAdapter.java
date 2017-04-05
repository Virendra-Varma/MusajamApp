package com.viru.musajamreactapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.viru.musajamreactapp.R;
import com.viru.musajamreactapp.model.Project;
import com.viru.musajamreactapp.ui.DetailView;
import com.viru.musajamreactapp.utils.AppConstants;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by viru on 4/2/2017.
 *
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectHolder> {
  private List<Project> projectList ;
  private Context mContext;

  public ProjectAdapter(List<Project> projectList, Context mContext) {
    this.projectList = projectList;
    this.mContext = mContext;
  }

  @Override public ProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_project_list, parent, false);
    return new ProjectHolder(view);
  }

  @Override public void onBindViewHolder(ProjectHolder holder, int position) {

    final Project data = projectList.get(position);
    holder.txtTitle.setText(data.getTitle());
    holder.txtPleadge.setText("Pledged - "+data.getAmtPledged());
    holder.txtBackers.setText("Backers - "+data.getNumBackers());
    holder.txtNoOfDays.setText("Percentage Funded - "+data.getPercentageFunded());

    holder.ll.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String title = data.getTitle();
        String url = data.getUrl();

        Intent in = new Intent(mContext, DetailView.class);
        in.putExtra(AppConstants.POJECT_TITLE,title);
        in.putExtra(AppConstants.URL,url);
        mContext.startActivity(in);
      }
    });
  }

  @Override public int getItemCount() {
    return (projectList.size()>0)?projectList.size():0;
  }

  public class ProjectHolder extends RecyclerView.ViewHolder {

    TextView txtTitle,txtPleadge,txtBackers,txtNoOfDays;
    LinearLayout ll;
    public ProjectHolder(View itemView) {
      super(itemView);
      txtTitle = (TextView) itemView.findViewById(R.id.txtProjectName);
      txtPleadge = (TextView) itemView.findViewById(R.id.txtPleadge);
      txtBackers = (TextView) itemView.findViewById(R.id.txtBackers);
      txtNoOfDays = (TextView) itemView.findViewById(R.id.txtNoOfDays);
      ll = (LinearLayout) itemView.findViewById(R.id.itemProject);
    }
  }
}
