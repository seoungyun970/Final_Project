package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
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
    EditText loginStoreId,openTime,finishTime,comments,storePhone,information;
    ImageView storePic;
    private static final int REQUEST_CODE = 0;
    RadioButton foodButton1,foodButton2,foodButton3,foodButton4,foodButton5,foodButton6;
    String foodCheck;
    Spinner spinner;
    private String img_path=new String();

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
        openTime=findViewById(R.id.openTime);
        finishTime=findViewById(R.id.finishTime);
        comments=findViewById(R.id.comments);
        storePhone=findViewById(R.id.storePhone);
        information=findViewById(R.id.information);
        foodButton1=findViewById(R.id.foodButton1);
        foodButton2=findViewById(R.id.foodButton2);
        foodButton3=findViewById(R.id.foodButton3);
        foodButton4=findViewById(R.id.foodButton4);
        foodButton5=findViewById(R.id.foodButton5);
        foodButton6=findViewById(R.id.foodButton6);
        spinner=findViewById(R.id.spinner);


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
//                    img_path = getImagePathToUri(data.getData());
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
                intent.putExtra("sendData",sendData);
//                intent.putExtra("_name",_name);
//                intent.putExtra("_detail",_detail);
                startActivityForResult(intent, REQUEST_TEST);
                break;
            case R.id.storeBtn:
                // 마감시간 12
                FoodRegisterUser task = new FoodRegisterUser();
                task.execute(loginStoreId.getText().toString(),storeId.getText().toString(),
                        main_address.getText().toString(), detail_address.getText().toString(),
                        topMessage.getText().toString(), openTime.getText().toString(),
                        storePhone.getText().toString(), foodCheck.toString(),
                        comments.getText().toString(),"1000",
                        finishTime.getText().toString(),"a1",
                        information.getText().toString()
                ); //1번
                break;
            case R.id.storePic:
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
                String URL =   "http://3.12.173.221:8080/SunhanWeb/androidaddStoreServlet.do";
                String _id = (String) params[0];
                String _storeName = (String) params[1];
                String _sendData = (String) params[2];
                String _detail=(String) params[3];
                String _topMessage=(String) params[4];
                String _openTime=params[5];
                String _storephone=(String) params[6];
                String _foodCheck=params[7];
                String _comments=params[8];
                String _price=params[9];
                String _closeTime=(String) params[10];
                // 마감시간 12
                String _area=params[11];
                String _information=params[12];
                // 지역 13

                //                String _pic=(String) params[4];
                String url_address = URL + "?id=" + _id + "&storeName=" + _storeName +
                        "&sendData=" + _sendData + "&detail=" + _detail +
                        //"&pic=" + _pic
                        "&topMessage=" + _topMessage + "&opentime=" + _openTime +
                        "&storePhone="+_storephone + "&foodCheck=" + _foodCheck +
                        "&comments=" + _comments + "&price=" + _price +
                        "&closetime=" + _closeTime + "&area=" + _area +
                        "&information=" + _information ;
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
                System.out.println("가게 등록 실패"+e.getMessage());
                return new String("Exception : " + e.getMessage());
            }


        }
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            loading.dismiss();
            System.out.println("결과값이다!!!!!!!!!"+result);
            Intent intent = new Intent(FoodActivity.this, ManagerMain.class);
            startActivity(intent);
        }
    }


//    public String getImagePathToUri(Uri data) {
//        //사용자가 선택한 이미지의 정보를 받아옴
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(data, proj, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//
//        //이미지의 경로 값
//        String imgPath = cursor.getString(column_index);
//        Log.d("test", imgPath);
//
//        //이미지의 이름 값
//        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
//        Toast.makeText(FoodActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
//        this.imageName = imgName;
//
//        return imgPath;
//    }


}




