package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        Handler handler=new Handler();



        handler.postDelayed(new splashhandler(),2000);  //delay시간 2초



    }
    public class splashhandler implements Runnable{

        @Override
        public void run() {
            startActivity(new Intent(getApplication(),Register.class)); //로딩 후 메인으로 넘어감
            SplashActivity.this.finish(); // Activity stack 제거
        }
    }

}
