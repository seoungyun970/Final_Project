package com.example.myapplication.Child;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class ChildStoreReserve extends Activity {
    EditText rv_personnel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childreserve);
    }
    public void set(){
        rv_personnel=findViewById(R.id.rv_personnel);
    }
}
