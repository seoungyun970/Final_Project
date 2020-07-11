package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.Child.ChildMain;
import com.example.myapplication.JSON.JSON;
import com.example.myapplication.Manager.ManagerMain;
import com.example.myapplication.Server.DataManager;
import com.example.myapplication.VO.SunhansVO;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener {
    TextView registerBtn;
    Button loginBtn;
    EditText loginId, loginPwd;
    public static Context context_main; // context 변수 선언
    String name;
    public SunhansVO user;
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
        tedPermission();
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
                Intent intent1 = new Intent(LoginActivity.this, main.class);
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
                String URL = "http://3.12.173.221:8080/SunhanWeb/androidLogin.do";

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
            System.out.println(result+"값!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            loading.dismiss();

            JSON js=new JSON();
            List List =js.loginuserjsonParsing(result);

            String ad;//관리자 판단하는 스트링변수

            SunhansVO usertemp = null;


            try {
                //json값을 받아와서 선한vo로 형변환해줌 로그인유저라 list엔하나뿐.
                usertemp =(SunhansVO) List.get(0);
                ad=usertemp.getAdmin();//vo에 ad민을 위에 string ad넣어줌
                name=usertemp.getName();
                user=usertemp;
                DataManager.getInstance().SetLoginUser(user);
            }catch (Exception e)
            {
                ad="-1";//리스트에 아무것도없다면 로그인유저가없어서 -1
            }

            // 0 아동
            if(ad.equals("0")){
                Intent intent1=new Intent(LoginActivity.this,ChildMain.class);
                Toast.makeText(getApplicationContext(),"아동용입니다.",Toast.LENGTH_LONG).show();
                startActivity(intent1);
            }
            //1 후원자
            if(ad.equals("1")){
                Intent intent=new Intent(getApplicationContext(), ManagerMain.class);
//                String userName=name.toString();
//                intent.putExtra("a",userName);
//                System.out.println(userName);
                Toast.makeText(getApplicationContext(),"관리자용입니다.",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
            if(ad.equals("-1")){
                Toast.makeText(getApplicationContext(),"등록되지 않은 회원입니다.",Toast.LENGTH_LONG).show();
            }

        }
    }
    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

}