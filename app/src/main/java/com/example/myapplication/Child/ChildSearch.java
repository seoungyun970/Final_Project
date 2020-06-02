package com.example.myapplication.Child;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.myapplication.R;

import java.util.ArrayList;

public class ChildSearch extends AppCompatActivity {
    Toolbar mToolbar;
    RecyclerView recyclerView;
    EditText editText;
    RecyclerViewAdapter adapter;

    ArrayList<String> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_search_activity);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // 툴바 뒤로가기 버튼생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 툴바 타이틀 삭제
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        editText = (EditText)findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        items.add("곽기태");
        items.add("박승윤");
        items.add("방찬석");
        items.add("이나형");
        items.add("오나미");
        items.add("박새로이");
        items.add("디비정보");
        items.add("끌어올곳");

        adapter = new RecyclerViewAdapter(this, items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

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
