package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

public class AddressActivity extends Activity {
    private WebView daum_webView;
    public static   TextView daum_result;
    private Handler handler;
    private String s;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        daum_result = (TextView) findViewById(R.id.daum_result);
        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();


    }
    public void init_webView() {

        // WebView 설정

        daum_webView = (WebView) findViewById(R.id.daum_webview);


        // JavaScript 허용

        daum_webView.getSettings().setJavaScriptEnabled(true);


        // JavaScript의 window.open 허용
        daum_webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        daum_webView.addJavascriptInterface(new AndroidBridge(), "TestApp");
        // web client 를 chrome 으로 설정
        daum_webView.setWebChromeClient(new WebChromeClient());
        // webview url load.jsp 파일 주소
        daum_webView.loadUrl("http://3.12.173.221:8080/SunhanWeb/android/jsp.jsp");




    }
    private class AndroidBridge {

        @JavascriptInterface

        public void setAddress(final String arg1, final String arg2, final String arg3) {

            handler.post(new Runnable() {

                @Override

                public void run() {

                    daum_result.setText(String.format("%s %s %s", "", arg2, arg3));

                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    intent=new Intent();
//                    intent.putExtra("sendData",daum_result.getText().toString());

                    intent.putExtra("sendData",daum_result.getText().toString());
                    System.out.println("주소@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+daum_result.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();


                    init_webView();

                }


            });

        }

    }



}
