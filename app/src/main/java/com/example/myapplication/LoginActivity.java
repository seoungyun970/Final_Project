package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class LoginActivity extends Activity implements View.OnClickListener {
    Button registerBtn,loginBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerBtn=findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerBtn:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;

        }
    }
}
