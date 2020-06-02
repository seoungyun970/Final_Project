package com.example.myapplication.Child;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Manager.ManagerMyinfo;
import com.example.myapplication.Manager.ManagerMyinfoUpdate;
import com.example.myapplication.R;

public class ChildMyinfo extends AppCompatActivity {
    Toolbar mToolbar;
    TextView infoupdatetxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_myinfo_activity);


        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // 툴바 뒤로가기 버튼생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 툴바 타이틀 삭제
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        infoupdatetxt = (TextView)findViewById(R.id.cinfoupdatetxt);
        infoupdatetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChildMyinfo.this, ChildMyinfoUpdate.class);
                startActivity(intent);
            }
        });
    }

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
