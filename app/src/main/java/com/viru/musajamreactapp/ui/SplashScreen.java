package com.viru.musajamreactapp.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.viru.musajamreactapp.R;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashScreen extends AppCompatActivity {
  private ImageView logo;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_splash_screen);

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
      @Override public void run() {
        printKeyHash();
        forwardToMainScreen();
      }
    };

    logo = (ImageView) findViewById(R.id.logo);

    logo.startAnimation(AnimationUtils.loadAnimation(this,
        R.anim.loading_rotation));



    handler.postDelayed(runnable, 2000);
  }

  private void forwardToMainScreen() {
    Intent intent = new Intent(this, HomeActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    logo.clearAnimation();
    startActivity(intent);
    finish();

  }

  private void printKeyHash() {
    try {
      PackageInfo info =
          getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
      for (Signature signature : info.signatures) {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(signature.toByteArray());
        Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
      }
    } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }
}
