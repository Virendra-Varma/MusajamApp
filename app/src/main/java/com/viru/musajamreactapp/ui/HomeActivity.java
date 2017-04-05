package com.viru.musajamreactapp.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.otto.Subscribe;
import com.viru.musajamreactapp.R;
import com.viru.musajamreactapp.adapter.ProjectAdapter;
import com.viru.musajamreactapp.events.BusProvider;
import com.viru.musajamreactapp.events.MusajamEvents;
import com.viru.musajamreactapp.model.Project;
import com.viru.musajamreactapp.network.RestDataService;
import com.viru.musajamreactapp.utils.AppConstants;
import com.viru.musajamreactapp.utils.PreferenceUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

  private LinearLayout linearLayout;
  private LayoutInflater mInflater;
  private RecyclerView rvProjects;
  private ProgressBar progressBar;
  private Context context;
  ArrayAdapter<String> adapter;
  private AutoCompleteTextView searchView;
  private ProjectAdapter projAdapter;
  private List<Project> projectList = new ArrayList<>();
  private List<String> projectName = new ArrayList<>();
  private TextView mSort, mFilter;
  private int width;
  private SwipeRefreshLayout swipeRefreshLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    progressBar = (ProgressBar) findViewById(R.id.progressbar);
    context = this;
    linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
    mInflater = (LayoutInflater) context.getSystemService
        (Context.LAYOUT_INFLATER_SERVICE);

    mSort = (TextView) findViewById(R.id.sort);
    mFilter = (TextView) findViewById(R.id.filter);
    swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipToRefresh);

    width = getScreenWidth();
    searchView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
    adapter = new ArrayAdapter<String>
        (this, android.R.layout.select_dialog_item, projectName);
    searchView.setThreshold(0);
    searchView.setAdapter(adapter);
    searchView.setTextColor(getResources().getColor(R.color.textcolor));

    rvProjects = (RecyclerView) findViewById(R.id.rvProjects);
    projAdapter = new ProjectAdapter(projectList, context);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
    rvProjects.setLayoutManager(mLayoutManager);
    rvProjects.setHasFixedSize(true);
    rvProjects.setItemAnimator(new DefaultItemAnimator());
    rvProjects.setAdapter(projAdapter);

    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        initiateServiceCall();
      }
    });
    if (projectList.isEmpty()) {
      searchView.setEnabled(false);
      initiateServiceCall();
    } else {
      searchView.setEnabled(true);
      //            swipeRefreshLayout.setEnabled(false);
    }

    searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String title = searchView.getText().toString().trim();
        String url = null;
        for(int i=0;i<projectList.size();i++){
          if(title.equalsIgnoreCase(projectList.get(i).getTitle())){
            url = projectList.get(i).getUrl();
          }
        }


        Intent in = new Intent(context, DetailView.class);
        in.putExtra(AppConstants.POJECT_TITLE,title);
        in.putExtra(AppConstants.URL,url);
        startActivity(in);
      }
    });
    mSort.setOnClickListener(this);
    mFilter.setOnClickListener(this);
  }

  public int getScreenWidth() {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    //int height = displayMetrics.heightPixels;
    int width = displayMetrics.widthPixels;
    return width;
  }

  private void initiateServiceCall() {
    PackageManager pm = context.getPackageManager();
    int hasPerm = pm.checkPermission(
        android.Manifest.permission.INTERNET,
        context.getPackageName());
    if (hasPerm == PackageManager.PERMISSION_GRANTED) {
      RestDataService services = new RestDataService();
      PreferenceUtils.setSorting(context,AppConstants.NONE);
      progressBar.setVisibility(View.VISIBLE);
      services.getAllProjects();
    }else{
      Toast.makeText(this,"Internet Permission is not given",Toast.LENGTH_LONG).show();
    }

  }

  @Subscribe
  public void onAllProjectFatched(MusajamEvents.AllProjectFatched fatched) {
    if (swipeRefreshLayout.isRefreshing()) {
      swipeRefreshLayout.setRefreshing(false);
    }
    progressBar.setVisibility(View.GONE);
    if (fatched != null) {
      if (projectList != null) {
        projectList.clear();
        projectList.addAll(fatched.getProjects());
        projAdapter.notifyDataSetChanged();

        for (int i = 0; i < projectList.size(); i++) {
          projectName.add(projectList.get(i).getTitle());
        }
      }

      if (!projectName.isEmpty() && adapter != null) {
        searchView.setEnabled(true);
        adapter.notifyDataSetChanged();
      }
    }else {
      searchView.setEnabled(true);
    }
  }

  private void filterSnakeBar() {

    /*final Snackbar filter = Snackbar.make(linearLayout, "", Snackbar.LENGTH_INDEFINITE);

    Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) filter.getView();
    TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
    textView.setVisibility(View.INVISIBLE);
    View snackView = mInflater.inflate(R.layout.item_filter_snackbar, null);
    snackView.setMinimumWidth(width);
    snackView.setBackgroundColor(Color.WHITE);
    final EditText noOfBackers = (EditText) snackView.findViewById(R.id.etFilter);
    TextView addFilter = (TextView) snackView.findViewById(R.id.btnGo);

    addFilter.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        filter.dismiss();
        rvProjects.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        List<Project> newList = new ArrayList<Project>();
        for (int i = 0; i < projectList.size(); i++) {
          if (projectList.get(i)
              .getNumBackers()
              .equalsIgnoreCase(noOfBackers.getText().toString().trim())) {
            newList.add(projectList.get(i));
          }
        }

        progressBar.setVisibility(View.GONE);
        rvProjects.setVisibility(View.VISIBLE);
        if(projectList != null && projAdapter!=null){
          projectList.clear();
          projectList.addAll(newList);
          projAdapter.notifyDataSetChanged();
        }
      }
    });

    layout.addView(snackView);
    // Show the Snackbar
    filter.show();*/
    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
    LayoutInflater inflater = this.getLayoutInflater();
    final View dialog = inflater.inflate(R.layout.item_filter_snackbar, null);
    dialog.setBackgroundColor(Color.TRANSPARENT);
    dialogBuilder.setView(dialog);

    final EditText noOfBackers = (EditText) dialog.findViewById(R.id.etFilter);
    TextView addFilter = (TextView) dialog.findViewById(R.id.btnGo);


    final AlertDialog b = dialogBuilder.create();
    b.show();

    addFilter.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        b.dismiss();
        rvProjects.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        List<Project> newList = new ArrayList<Project>();
        for (int i = 0; i < projectList.size(); i++) {
          if (projectList.get(i)
              .getNumBackers()
              .equalsIgnoreCase(noOfBackers.getText().toString().trim())) {
            newList.add(projectList.get(i));
          }
        }

        progressBar.setVisibility(View.GONE);
        rvProjects.setVisibility(View.VISIBLE);
        if(projectList != null && projAdapter!=null){
          projectList.clear();
          projectList.addAll(newList);
          projAdapter.notifyDataSetChanged();
        }
      }
    });

  }

  private void sortBySnakeBar() {
    final Snackbar sortBy = Snackbar.make(linearLayout, "", Snackbar.LENGTH_INDEFINITE);

    Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) sortBy.getView();
    TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
    textView.setVisibility(View.INVISIBLE);
    View snackView = mInflater.inflate(R.layout.shortby_snakebar_item, null);
    snackView.setBackgroundColor(Color.WHITE);
    RelativeLayout sortByTime = (RelativeLayout) snackView.findViewById(R.id.sortByTime);
    RelativeLayout sortByAlpha =
        (RelativeLayout) snackView.findViewById(R.id.sortByAlphabetically);
    RadioButton rbByAlpha = (RadioButton) snackView.findViewById(R.id.rbByAlpha);
    RadioButton rbByTime = (RadioButton) snackView.findViewById(R.id.rbByTime);
    String sorted = PreferenceUtils.getSortBy(context);
    if(!sorted.equalsIgnoreCase(AppConstants.NONE)){
      if(sorted.equalsIgnoreCase(AppConstants.BY_ALPHA)){
        rbByTime.setChecked(false);
        rbByAlpha.setChecked(true);
      }else if(sorted.equalsIgnoreCase(AppConstants.BY_TIME)){
        rbByAlpha.setChecked(false);
        rbByTime.setChecked(true);
      }
    }
    sortByTime.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        sortBy.dismiss();
        PreferenceUtils.setSorting(context,AppConstants.BY_TIME);
        Collections.sort(projectList, new Comparator<Project>() {
          @Override public int compare(Project o1, Project o2) {
            return o1.getEndTime().compareTo(o2.getEndTime());
          }
        });

        if(projectList!=null && projAdapter!=null){
          projAdapter.notifyDataSetChanged();
        }
      }
    });

    sortByAlpha.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        sortBy.dismiss();
        PreferenceUtils.setSorting(context,AppConstants.BY_ALPHA);
        Collections.sort(projectList, new Comparator<Project>() {
          @Override public int compare(Project o1, Project o2) {
            return o1.getTitle().compareTo(o2.getTitle());
          }
        });
        if(projectList!=null && projAdapter!=null){
          projAdapter.notifyDataSetChanged();
        }
      }
    });



    layout.addView(snackView);
    // Show the Snackbar
    sortBy.show();
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.sort:
        sortBySnakeBar();
        break;
      case R.id.filter:
        filterSnakeBar();
        break;
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    BusProvider.getInstance().register(this);
  }

  @Override
  protected void onStop() {
    super.onStop();
    BusProvider.getInstance().unregister(this);
  }

}
