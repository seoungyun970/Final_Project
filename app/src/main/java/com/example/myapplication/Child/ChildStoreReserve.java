package com.example.myapplication.Child;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.VO.StoreDetailVO;
import com.example.myapplication.VO.SunhansVO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChildStoreReserve extends AppCompatActivity {
    public static Context context_main;
    TextView tv;
    TimePicker timePicker;
    Calendar calendar;
    Button childReserveBtn;
    String hour1;
    String minute1;
    EditText childReservePeople;
    String nowId;
    public StoreDetailVO store;
    String storeId;
    TextView nowDay;
    String nowDayStr;
    String nowDayStrFin;
    Toolbar mToolbar;
    int childStoreReserveResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childreserve);
//        getNewsDetail();
//        setNews();
        set();
        click();
        Intent intent=getIntent();
        storeId=intent.getExtras().getString("userid");
        nowDayStr=intent.getExtras().getString("nowTime");
        childStoreReserveResult=intent.getExtras().getInt("result");
        SimpleDateFormat datefm = new SimpleDateFormat("yyyyMMdd"); // 문자열 -> date
                SimpleDateFormat stringfm = new SimpleDateFormat("yyyy-MM-dd"); // 문자열 -> date
                Date date = null;
                try {
                    date = datefm.parse(nowDayStr);
                    System.out.println("date"+date);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("nowDayStrFin"+nowDayStrFin);
                nowDayStrFin = stringfm.format(date);

        mToolbar = (Toolbar)findViewById(R.id.mreservetoolbar);
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


    public void set() {
        childReserveBtn = findViewById(R.id.childReserveBtn);
        timePicker = findViewById(R.id.timePicker);
        childReservePeople = findViewById(R.id.childReservePeople);
        nowDay=findViewById(R.id.nowDay);
        nowDay.setText(nowDayStrFin);
        SunhansVO user2 = ((LoginActivity) LoginActivity.context_main).user;
        nowId = user2.getId();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour1 = timePicker.getHour() + "";
            minute1 = timePicker.getMinute() + "";
        } else {
            hour1 = timePicker.getCurrentHour() + "";
            minute1 = timePicker.getCurrentMinute() + "";
        }
    }

    public void click() {
        childReserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(hour1 + "시" + minute1 + "분");
                System.out.println("내 아이디: "+nowId);
                System.out.println("가게 아이디: "+storeId);
                System.out.println(nowDayStr);
                childStoreRegisterUser task = new childStoreRegisterUser();
                task.execute( storeId.toString(),nowDayStr,hour1,
                        minute1,nowId.toString(),childReservePeople.getText().toString(),
                        String.valueOf(childStoreReserveResult)
                );
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int min) {
                hour1 = String.valueOf(hour);
                minute1 = String.valueOf(min);

            }
        });
    }

//    public void getNewsDetail() {
//        Intent intent = getIntent();
//        if (intent != null) {
//            Bundle bld = intent.getExtras();
//            Object obj = bld.get("news");
//            System.out.println(obj + "news:");
//            if (obj != null && obj instanceof StoreDetailVO) {
//                this.store = (StoreDetailVO) obj;
//            }
//        }
//    }

//        public void setNews() {
//            if (this.store != null) {
//                SimpleDateFormat datefm = new SimpleDateFormat("yyyyMMdd"); // 문자열 -> date
//                SimpleDateFormat stringfm = new SimpleDateFormat("yyyy-MM-dd"); // 문자열 -> date
//                Date date = null;
//                try {
//                    date = datefm.parse(nowDayStr);
//                    System.out.println("date"+date);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                System.out.println("nowDayStrFin"+nowDayStrFin);
//                nowDayStrFin = stringfm.format(date);
//            }
//            else{
//                System.out.println("null!!!!!!!!!");
//            }
//        }
       private class childStoreRegisterUser extends AsyncTask<String, String, String> {

            ProgressDialog loading;
            URL register_url;

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ChildStoreReserve.this, "예약 중입니다.", null, true, true);
            }


            @Override
            protected String doInBackground(String... params) {
                try {
                    String URL = "http://3.12.173.221:8080/SunhanWeb/androidreserveadd.do";
                    String rv_storeid = (String) params[0];
                    String now=params[1];
                    String rv_option=params[2];
                    String rv_min=params[3];
                    String  rv_userid= (String) params[4];
                    int rv_personnel = Integer.parseInt(params[5]);
                    int result1=Integer.parseInt(params[6]);
                    // 지역 13

                    //                String _pic=(String) params[4];
                    String url_address = URL  + "?storeid=" + rv_storeid +"&now="+now+"&option="+rv_option+ "&minute="+rv_min+"&id=" + rv_userid+ "&personnel=" + rv_personnel+"&result="+result1;
;
                    System.out.println(url_address);
                    register_url = new URL(url_address);
                    BufferedReader in = new BufferedReader(new InputStreamReader(register_url.openStream()));

                    String result = "";
                    String temp = "";
                    while ((temp = in.readLine()) != null) {
                        result += temp;
                    }
                    in.close();
                    return result;
                } catch (Exception e) {
                    System.out.println("예약 등록 실패" + e.getMessage());
                    return new String("Exception : " + e.getMessage());
                }


            }

            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                loading.dismiss();
                System.out.println("결과값이다!!!!!!!!!" + result);
                Intent intent = new Intent(ChildStoreReserve.this, ChildMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }

