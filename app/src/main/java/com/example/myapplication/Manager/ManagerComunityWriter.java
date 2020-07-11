package com.example.myapplication.Manager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ManagerComunityWriter extends AppCompatActivity implements View.OnClickListener {
    EditText context;
    TextView tv_input_length;
    Button storeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_comunity_writer);

        context = findViewById(R.id.context);
        tv_input_length = findViewById(R.id.tv_input_length);
        storeBtn=findViewById(R.id.storeBtn);
        storeBtn.setOnClickListener(this);
        //EditText 리스너 (입력시 반응)
        context.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                tv_input_length.setText(s.length()+"자");   //글자수 TextView에 보여주기.
            }
        });

//        //EditText Enter key 방지
//        context.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if(keyCode==event.KEYCODE_ENTER) return true;
//                return false;
//            }
//        });
    }

    @Override
    public void onClick(View view) {

    }
}
