package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

public class FoodActivity extends Activity implements View.OnClickListener {
    Button address_btn;
    TextView main_address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_register);
        address_btn=findViewById(R.id.address_btn);
        address_btn.setOnClickListener(this);

        Intent intent = getIntent();
//        String receiveStr = intent.getExtras().getString("sendData");
        main_address=findViewById(R.id.main_address);
//        if(receiveStr!=null){
//            main_address.setText(receiveStr);
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.address_btn:
                Intent intent=new Intent(FoodActivity.this,AddressActivity.class);
                startActivity(intent);
                break;
        }
    }

}
