package com.viru.musajamreactapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by viru on 4/2/2017.
 */

public class PreferenceUtils {

  private static final String APP_TAG = "MJApp";
  private static SharedPreferences shared;

  public static SharedPreferences getSharedPreference(Context context) {
    if (shared == null) {
      shared = context.getSharedPreferences(APP_TAG, Context.MODE_PRIVATE);
    }
    return shared;
  }

  public static SharedPreferences.Editor getPrefEditor(Context context) {
    return getSharedPreference(context).edit();
  }

  public static void clearSharedPref(Context context) {
    getPrefEditor(context).clear().apply();
  }

  public static void setSorting(Context context,String sortBy){
    getPrefEditor(context).putString(AppConstants.SHORT_BY,sortBy).apply();
  }

  public static String getSortBy(Context context){
    return getSharedPreference(context).getString(AppConstants.SHORT_BY,null);
  }
}
