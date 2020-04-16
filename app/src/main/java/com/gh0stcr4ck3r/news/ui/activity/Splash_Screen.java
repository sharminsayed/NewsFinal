package com.gh0stcr4ck3r.news.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.gh0stcr4ck3r.news.R;
import com.gh0stcr4ck3r.news.ui.activity.MainActivity;

public class Splash_Screen extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        handler=new Handler();
      handler.postDelayed(new Runnable() {
          @Override
          public void run() {
              Intent intent=new Intent(getApplicationContext(), MainActivity.class);
              startActivity(intent);
              Animatoo.animateSpin(Splash_Screen.this);
              finish();

          }
      },4000);
    }
}
