package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class RegisterActivity extends Activity implements View.OnClickListener {
    Button register_checkBtn;
    EditText register_id,register_pw,register_name,register_phone;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        // 윈도우 매니저 객체 얻어오고 디스플레이 객체 얻어오기
        int width = (int) (display.getWidth() * 0.9);
        // 얻어온 화면의 폭의 90프로만큼 width에 지정
        int height = (int) (display.getHeight() * 0.7);
        // 얻어온 화면의 높이의 70프로 만큼 height에 지정
        getWindow().getAttributes().width = width;
        //멤버십팝업 레이아웃의 폭을 width로 지정
        getWindow().getAttributes().height = height;
        //멤버십팝업 레이아웃의 폭을 height 지정
        getWindow().setGravity(Gravity.CENTER);
        //멤버십팝업 레이아웃 센터지정



        register_id=findViewById(R.id.register_id);
        register_pw=findViewById(R.id.register_pw);
        register_name=findViewById(R.id.register_name);
        register_phone=findViewById(R.id.register_phone);
        register_checkBtn=findViewById(R.id.register_checkBtn);
        register_checkBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.register_checkBtn:
                RegisterUser task = new RegisterUser();
                task.execute(register_id.getText().toString(), register_pw.getText().toString()); //1번
                break;
        }
    }

    private class RegisterUser  extends AsyncTask<String, String, String>
    {
        ProgressDialog loading;
        URL register_url;

        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(RegisterActivity.this, "Please Wait", null, true, true);
        }

        @Override
        //2번
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/regis.jsp";

                String _id = (String) params[0];
                String _pw = (String) params[1];

                System.out.println(_id);
                System.out.println(_pw);

                String url_address = URL + "?id=" + _id + "&pw=" + _pw;

                System.out.println(url_address);
                register_url = new URL(url_address);
                BufferedReader in = new BufferedReader(new InputStreamReader(register_url.openStream()));

                String result = "";
                String temp = "";
                //readLine()시 리턴값을 String으로 고정되기에 String이 아닌 다른타입으로 입력을 받을려면 형변환을 꼭 해주어야한다는 점
                while ((temp = in.readLine()) != null) {
                    result += temp;
                }
                in.close();

                return result;
            }
            catch (Exception e)
            {
                System.out.println("실패");
                return new String("Exception : " + e.getMessage());
            }

        }

        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            loading.dismiss();
            System.out.println(result);
            if(result.contains("success"))
            {
                Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        }
    }
}
