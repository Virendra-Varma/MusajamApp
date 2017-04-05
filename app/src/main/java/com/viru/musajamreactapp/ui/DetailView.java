package com.viru.musajamreactapp.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;
import com.viru.musajamreactapp.R;
import com.viru.musajamreactapp.utils.AppConstants;

public class DetailView extends AppCompatActivity {

  private WebView browser;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_view);

    Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(mToolbar);
    if (getSupportActionBar() != null) {
      ActionBar ab = getSupportActionBar();
      ab.setDefaultDisplayHomeAsUpEnabled(true);
      ab.setDisplayHomeAsUpEnabled(true);
      ab.setTitle(getIntent().getStringExtra(AppConstants.POJECT_TITLE));
    }

    String url = "https://www.kickstarter.com"+getIntent().getStringExtra(AppConstants.URL);

    Toast.makeText(this,"Page is Loading",Toast.LENGTH_LONG).show();
    browser = (WebView) findViewById(R.id.webView);
    browser.getSettings().setLoadsImagesAutomatically(true);
    browser.getSettings().setJavaScriptEnabled(true);
    browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    if (savedInstanceState == null)
    {
      browser.loadUrl(url);
    }

  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState )
  {
    super.onSaveInstanceState(outState);
    browser.saveState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState)
  {
    super.onRestoreInstanceState(savedInstanceState);
    browser.restoreState(savedInstanceState);
  }
}
