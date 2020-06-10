package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapplication.VO.StoreVO;


public class StoreDetail extends Activity {
    private StoreVO store;
    private TextView TextView_title, TextView_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storedetail);
        setComp();
        getNewsDetail();
        setNews();
    }

    //기본 컴포넌트 셋팅
    public void setComp() {
        TextView_title = findViewById(R.id.TextView_title);
        TextView_content = findViewById(R.id.TextView_content);
    }

    //이전 액티비티에서 받아오는 인텐트
    public void getNewsDetail() {
        Intent intent = getIntent();
        if(intent != null) {
            Bundle bld = intent.getExtras();

            Object obj = bld.get("news");
            if(obj != null && obj instanceof StoreVO) {
                this.store = (StoreVO) obj;
            }
        }
    }

    //이전 액티비티에서 받아오는 인텐트에서 정보를 확인하여 뉴스표시
    public void setNews() {
        if(this.store != null) {
            String title = this.store.getOpenTime();
            System.out.println(title+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@title");
            if(title != null) {
                TextView_title.setText(title);
            }
            String content = this.store.getComments();
            if(content != null) {
                //전체 본문은 url 값에 있고 해당 전체 본문을 불러오기 위해서는 스크래핑 (크롤링) 사용
                TextView_content.setText(content);
            }

        }
    }
}
