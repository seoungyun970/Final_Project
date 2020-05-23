package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LoginActivity extends Activity implements View.OnClickListener {
    Button registerBtn, loginBtn;
    EditText loginId, loginPwd;
    public static Context context_main; // context 변수 선언

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerBtn = findViewById(R.id.registerBtn);
        loginBtn = findViewById(R.id.loginBtn);
        loginId = findViewById(R.id.loginId);
        loginPwd = findViewById(R.id.loginPwd);
        registerBtn.setOnClickListener(this);
        context_main = this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerBtn:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.loginBtn:
                String _id  = loginId.getText().toString();
                String _pw = loginPwd.getText().toString();

                Intent intent1 = new Intent( LoginActivity.this, main.class );
                intent1.putExtra("_id",_id);
                intent1.putExtra("_pw",_pw);
                LoginActivity.RegisterUser task = new LoginActivity.RegisterUser();
                task.execute(_id, _pw); //1번
                break;
        }
        }
    private class RegisterUser  extends AsyncTask<String, String, String>
    {
        ProgressDialog loading;
        URL register_url;

        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(LoginActivity.this, "로그인 중입니다.", null, true, true);
        }

        @Override
        //2번
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/android/andlogin.jsp";

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
            String result1=result.replaceAll("\\s","");
            System.out.println(result1);
            if(result1.equals("0")){
                Toast.makeText(getApplicationContext(),"비밀번호가 맞지 않습니다.",Toast.LENGTH_LONG).show();
            }
            // 1 아동
            if(result1.equals("1")){
                Intent intent1=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent1);
            }
            //2 후원자
            if(result1.equals("2")){
                System.out.println(result1);
                Intent intent=new Intent(LoginActivity.this, Manager_main.class);
                startActivity(intent);
            }

            if(result1.equals("-1")){
                Toast.makeText(getApplicationContext(),"등록되지 않은 회원입니다.",Toast.LENGTH_LONG).show();
            }
        }
    }

}