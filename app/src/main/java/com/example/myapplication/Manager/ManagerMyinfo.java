package com.example.myapplication.Manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.VO.SunhansVO;

public class ManagerMyinfo extends AppCompatActivity {
    TextView infoupdatetxt;
    TextView textName;
    Button donabtn;
    Toolbar mToolbar;
    public static Context context_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_myinfo_activity);

        textName = (TextView)findViewById(R.id.textName);
        String a1=((ManagerMain)ManagerMain.context_main).a;
        textName.setText(a1);
        mToolbar = (Toolbar)findViewById(R.id.mmyinfotoolbar);
        setSupportActionBar(mToolbar);
        // 툴바 뒤로가기 버튼생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 툴바 타이틀 삭제
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        infoupdatetxt = (TextView)findViewById(R.id.minfoupdatetxt);
        //정보수정
        infoupdatetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManagerMyinfo.this, ManagerMyinfoUpdate.class);
                startActivity(intent);
            }
        });
        donabtn = (Button)findViewById(R.id.donabtn);
        donabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ManagerMyinfo.this, ManagerDonaUpdate.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
