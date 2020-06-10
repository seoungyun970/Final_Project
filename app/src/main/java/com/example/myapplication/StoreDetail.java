package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.VO.StoreVO;
import com.facebook.drawee.view.SimpleDraweeView;


public class StoreDetail extends AppCompatActivity {
    StoreVO store;
    private TextView textCloseTime,textOpenTime,textFood,textShopName,textArea,textPhone,textComments,textTopmessage,textAddr;
    private SimpleDraweeView imageFileRealName;
    Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storedetail);
        setComp();
        getNewsDetail();
        setNews();

        mToolbar = (Toolbar)findViewById(R.id.storedetailtool);
        setSupportActionBar(mToolbar);
        // 툴바 뒤로가기 버튼생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 툴바 타이틀 삭제
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

    //기본 컴포넌트 셋팅
    public void setComp() {
        textCloseTime = findViewById(R.id.textCloseTime);
        textOpenTime=findViewById(R.id.textOpenTime);
        textFood=findViewById(R.id.textFood);
        textShopName=findViewById(R.id.textShopName);
        textArea=findViewById(R.id.textArea);
        textPhone=findViewById(R.id.textPhone);
        textComments=findViewById(R.id.textComments);
        textTopmessage=findViewById(R.id.textTopmessage);
        textAddr=findViewById(R.id.textAddr);
        imageFileRealName=findViewById(R.id.imageFileRealName);
    }

    //이전 액티비티에서 받아오는 인텐트
    public void getNewsDetail() {
        Intent intent = getIntent();
        if(intent != null) {
            Bundle bld = intent.getExtras();
            Object obj = bld.get("storeInfor");
            System.out.println(obj+"storeInfor:");
            if(obj != null && obj instanceof StoreVO) {
                this.store = (StoreVO) obj;
            }
        }
    }

    //이전 액티비티에서 받아오는 인텐트에서 정보 뿌려준다
    public void setNews() {
        if(this.store != null) {
            String closeTime = this.store.getCloseTime();
            String openTime = this.store.getOpenTime();
            String food=this.store.getFoodCheck();
            String shopName=this.store.getStoreName();
            String area=this.store.getArea();
            String phone=this.store.getStorephone();
            String comments=this.store.getComments();
            String topMessage=this.store.getTopMessage();
            String addr=this.store.getSendData();
            Uri uri = Uri.parse("http://3.12.173.221:8080/SunhanWeb/store/"+this.store.getImage());
            try {
                textCloseTime.setText(closeTime);
                textOpenTime.setText(openTime);
                textFood.setText(food);
                textShopName.setText(shopName);
                textArea.setText(area);
                textPhone.setText(phone);
                textComments.setText(comments);
                textTopmessage.setText(topMessage);
                textAddr.setText(addr);
                imageFileRealName.setImageURI(uri);
            }catch (Exception e){

            }

//            String content = this.store.getComments();
//            if(content != null) {
//                TextView_content.setText(content);
//            }
        }
    }
}
