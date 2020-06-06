package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapplication.Manager.ManagerMain;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class FoodActivity extends Activity implements View.OnClickListener {
    TextView main_address;
    TextInputEditText storeId,detail_address;
    Button storeBtn;
    private int REQUEST_TEST = 1;
    EditText loginStoreId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_register);
//        String receiveStr = intent.getExtras().getString("sendData");
        main_address=findViewById(R.id.main_address);
        storeId=findViewById(R.id.storeId);
        detail_address=findViewById(R.id.detail_address);
        storeBtn=findViewById(R.id.storeBtn);
        loginStoreId=((LoginActivity)LoginActivity.context_main).loginId;
//        if(receiveStr!=null){
//            main_address.setText(receiveStr);
//        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            String data1=data.getStringExtra("sendData");
            main_address.setText(data1);

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_address:
                Intent intent=new Intent(getApplicationContext(),AddressActivity.class);
                String sendData  = main_address.getText().toString();
//                String _name=storeId.getText().toString();  //가게명
//                String _detail=detail_address.getText().toString();
                intent.putExtra("sendData",sendData);
//                intent.putExtra("_name",_name);
//                intent.putExtra("_detail",_detail);
                startActivityForResult(intent, REQUEST_TEST);
                break;
            case R.id.storeBtn:
                FoodRegisterUser task = new FoodRegisterUser();
                task.execute(loginStoreId.getText().toString(),storeId.getText().toString(), main_address.getText().toString(),
                        detail_address.getText().toString()); //1번
                break;

        }

    }
    private class FoodRegisterUser extends AsyncTask<String, String, String>
    {

        ProgressDialog loading;
        URL register_url;
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(FoodActivity.this, "가게를 등록중입니다.", null, true, true);
        }


        @Override
        protected String doInBackground(String... params) {
            try{
                String URL = "http://3.12.173.221:8080/SunhanWeb/android/addstore.jsp";
                String _id = (String) params[0];
                String _storeName = (String) params[1];
                String _sendData = (String) params[2];
                String _detail=(String) params[3];
                System.out.println(_storeName);
                System.out.println(_sendData);
                System.out.println(_detail);
                String url_address = URL + "?id=" + _id + "&storeName=" + _storeName + "&sendData=" + _sendData + "&detail=" + _detail;
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
            }catch (Exception e){
                System.out.println("가게 등록 실패");
                return new String("Exception : " + e.getMessage());
            }


        }
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            loading.dismiss();
            System.out.println(result);
            Intent intent = new Intent(FoodActivity.this, ManagerMain.class);
            startActivity(intent);
        }

    }



}




