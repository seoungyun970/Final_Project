package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.Manager.ManagerMain;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class FoodActivity extends Activity implements View.OnClickListener {
    TextView main_address;
    TextInputEditText storeId,detail_address,topMessage;
    Button storeBtn;
    private int REQUEST_TEST = 1;
    EditText loginStoreId,storeTime,comments;
    ImageView storePic;
    private static final int REQUEST_CODE = 0;
    RadioButton foodButton1,foodButton2,foodButton3,foodButton4,foodButton5,foodButton6;
    String foodCheck;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_register);
        main_address=findViewById(R.id.main_address);
        storeId=findViewById(R.id.storeId);
        detail_address=findViewById(R.id.detail_address);
        storeBtn=findViewById(R.id.storeBtn);
        loginStoreId=((LoginActivity)LoginActivity.context_main).loginId;
        storePic=findViewById(R.id.storePic);
        topMessage=findViewById(R.id.topMessage);
        storeTime=findViewById(R.id.storeTime);
        comments=findViewById(R.id.comments);
        foodButton1=findViewById(R.id.foodButton1);
        foodButton2=findViewById(R.id.foodButton2);
        foodButton3=findViewById(R.id.foodButton3);
        foodButton4=findViewById(R.id.foodButton4);
        foodButton5=findViewById(R.id.foodButton5);
        foodButton6=findViewById(R.id.foodButton6);

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


        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    storePic.setImageBitmap(img);
                }catch(Exception e)
                {

                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
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
                task.execute(loginStoreId.getText().toString(),storeId.getText().toString(),
                        main_address.getText().toString(), detail_address.getText().toString(),
                        topMessage.getText().toString(),storePic.getDrawable().toString(),
                        storeTime.getText().toString(), foodCheck.toString(),
                        comments.getText().toString()); //1번
                break;
            case R.id.galleryBtn:
                Intent intent1=new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent1, REQUEST_CODE);
                break;
        }
        ////////////////////////////////////////// 카테고리 그룹체크
        if(foodButton1.isChecked()){
            foodCheck="1";
        }
        if(foodButton2.isChecked()){
            foodCheck="2";
        }
        if(foodButton3.isChecked()){
            foodCheck="3";
        }
        if(foodButton4.isChecked()){
            foodCheck="4";
        }
        if(foodButton5.isChecked()){
            foodCheck="5";
        }
        if(foodButton6.isChecked()){
            foodCheck="6";
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
                String _pic=(String) params[4];
                String _topMessage=(String) params[5];
                String _storeTime=(String) params[6];
                String _foodCheck=params[7];
                String _comments=params[8];
                String url_address = URL + "?id=" + _id + "&storeName=" + _storeName +
                        "&sendData=" + _sendData + "&detail=" + _detail +
                        "&pic=" + _pic + "&topMessage=" + _topMessage +
                        "&storeTime=" + _storeTime +
                        "&foodCheck=" + _foodCheck + "&comments=" + _comments;
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




