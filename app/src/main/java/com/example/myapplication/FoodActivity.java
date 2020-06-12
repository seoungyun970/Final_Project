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
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.myapplication.Manager.ManagerMain;
import com.example.myapplication.Manager.ManagerMyinfoUpdate;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class FoodActivity extends Activity implements View.OnClickListener {
    TextView main_address;
    TextInputEditText storeId,detail_address,topMessage;
    Button storeBtn;
    private int REQUEST_TEST = 1;
    EditText loginStoreId,openTime,finishTime,comments,storePhone,information;

    ImageView storePic;
    ImageView storePic2;
    ImageView storePic3;
    private static final int REQUEST_CODE = 0;
    private static final int REQUEST_CODE2 = 1;
    private static final int REQUEST_CODE3= 2;

    String img_path=null;
    String img_path2=null;
    String img_path3=null;
    String storeName=null;
    String storeName2=null;
    String storeName3=null;


    RadioButton foodButton1,foodButton2,foodButton3,foodButton4,foodButton5,foodButton6;
    String foodCheck;
    Spinner spinner;
    Boolean areaSet=true;
    String areaLocation;

    String storeimapath="http://3.12.173.221:8080/SunhanWeb/store/default.png";
    String storeimapathTwo="http://3.12.173.221:8080/SunhanWeb/store/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_register);
        main_address=findViewById(R.id.main_address);
        storeId=findViewById(R.id.storeId);
        detail_address=findViewById(R.id.detail_address);
        storeBtn=findViewById(R.id.storeBtn);
        loginStoreId=((LoginActivity)LoginActivity.context_main).loginId;
        storePic=findViewById(R.id.storePic);
        storePic2=findViewById(R.id.storePic2);
        storePic3=findViewById(R.id.storePic3);
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
        getSpinner();

        storePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        Glide.with(this).load(storeimapath).into(storePic);
        storePic2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE2);
            }
        });
        Glide.with(this).load(storeimapath).into(storePic2);
        storePic3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE3);
            }
        });
        Glide.with(this).load(storeimapath).into(storePic3);
    }
    public void getSpinner(){
        ArrayAdapter areaAdapter=ArrayAdapter.createFromResource(this, R.array.spinnerArea, android.R.layout.simple_spinner_dropdown_item);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(areaAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(FoodActivity.this,"선택된 아이템 : "+spinner.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
                if(spinner.getItemAtPosition(i).toString().equals("강남/역삼/삼성/논현")){
                    areaLocation="a1";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("서초/신사/방배")){
                    areaLocation="a2";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("천호/길동/둔촌")){
                    areaLocation="a3";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("화곡/까지찬/양천/목동")){
                    areaLocation="a4";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("구로/금천/오류")){
                    areaLocation="a5";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("신촌/홍대/합정")){
                    areaLocation="a6";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("영신내/불광/응암")){
                    areaLocation="a7";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("종로/대학로/신도림")){
                    areaLocation="a8";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("성신여대/성북/월곡")){
                    areaLocation="a9";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("이태원/용산/서울역")){
                    areaLocation="a10";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("동대문/동묘/신당/충무")){
                    areaLocation="a11";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("회기/고려대/청량/신설")){
                    areaLocation="a12";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("건대/군자/구의")){
                    areaLocation="a13";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("왕십리/성수/금호")){
                    areaLocation="a14";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("수유/미아")){
                    areaLocation="a15";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("상봉/중랑/면목")){
                    areaLocation="a16";
                }
                else if(spinner.getItemAtPosition(i).toString().equals("태릉/노원/도봉/창동")){
                    areaLocation="a17";
                }
                System.out.println(areaLocation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            String data1=data.getStringExtra("sendData");
            main_address.setText(data1);
        }
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);

                    in.close();

                    storePic.setImageBitmap(img);


                } catch (Exception e) {

                }

                img_path = getImagePathToUri(data.getData()); //이미지의 URI를 얻어 경로값으로 반환.


            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_CODE2) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);

                    in.close();

                    storePic2.setImageBitmap(img);


                } catch (Exception e) {

                }

                img_path2 = getImagePathToUri2(data.getData()); //이미지의 URI를 얻어 경로값으로 반환.


            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_CODE3) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);

                    in.close();

                    storePic3.setImageBitmap(img);


                } catch (Exception e) {

                }

                img_path3 = getImagePathToUri3(data.getData()); //이미지의 URI를 얻어 경로값으로 반환.


            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴


        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);

        String thePath = "no-path-found";

        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor2 = getContentResolver().query(data, filePathColumn, null, null, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            thePath = cursor.getString(columnIndex);
        }
        cursor2.close();

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        Toast.makeText(FoodActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        this.storeName = imgName;
        System.out.println(storeName);
        return imgPath;
    }
    public String getImagePathToUri2(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴


        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);

        String thePath = "no-path-found";

        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor2 = getContentResolver().query(data, filePathColumn, null, null, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            thePath = cursor.getString(columnIndex);
        }
        cursor2.close();

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        Toast.makeText(FoodActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        this.storeName2 = imgName;
        return imgPath;
    }
    public String getImagePathToUri3(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴


        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);

        String thePath = "no-path-found";

        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor2 = getContentResolver().query(data, filePathColumn, null, null, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            thePath = cursor.getString(columnIndex);
        }
        cursor2.close();

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        Toast.makeText(FoodActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        this.storeName3 = imgName;
        System.out.println(storeName);
        return imgPath;
    }

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    private class http  extends AsyncTask<String, String, String>
    {
        ProgressDialog loading;

        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(FoodActivity.this, "업로드 중입니다.", null, true, true);
        }

        @Override
        //2번
        protected String doInBackground(String... params) {
            try {
                URL connectUrl = new URL("http://3.12.173.221:8080/SunhanWeb/andStoreImageUpload.do");
                //URL connectUrl = new URL("http://localhost:8181/SunhanWeb/android/andImageUpload.jsp");
                HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("img_1",img_path);
                conn.setRequestProperty("img_2",img_path2);
                conn.setRequestProperty("img_3",img_path3);
                conn.connect();

                HttpURLConnection conn2 = (HttpURLConnection) connectUrl.openConnection();
                conn2.setDoInput(true);
                conn2.setDoOutput(true);
                conn2.setUseCaches(false);
                conn2.setRequestMethod("POST");
                conn2.setRequestProperty("Connection", "Keep-Alive");
                conn2.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn2.setRequestProperty("img_1",img_path);
                conn2.setRequestProperty("img_2",img_path2);
                conn2.setRequestProperty("img_3",img_path3);
                conn2.connect();

                HttpURLConnection conn3 = (HttpURLConnection) connectUrl.openConnection();
                conn3.setDoInput(true);
                conn3.setDoOutput(true);
                conn3.setUseCaches(false);
                conn3.setRequestMethod("POST");
                conn3.setRequestProperty("Connection", "Keep-Alive");
                conn3.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn3.setRequestProperty("img_1",img_path);
                conn3.setRequestProperty("img_2",img_path2);
                conn3.setRequestProperty("img_3",img_path3);
                conn3.connect();

                // write data
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                String filename=((LoginActivity)LoginActivity.context_main).user.getId();

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\""+filename+"\";filename=\"" + img_path + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                FileInputStream mFileInputStream = new FileInputStream(img_path);
                int bytesAvailable = mFileInputStream.available();
                int maxBufferSize = 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);

                byte[] buffer = new byte[bufferSize];
                int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);


                // read image
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = mFileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                mFileInputStream.close();
                InputStream is = conn.getInputStream();
                // finish upload...

                dos = new DataOutputStream(conn2.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\""+filename+"\"2;filename=\"" + img_path2 + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                mFileInputStream = new FileInputStream(img_path2);
                int bytesAvailable2 = mFileInputStream.available();
                int maxBufferSize2 = 1024;
                int bufferSize2 = Math.min(bytesAvailable2, maxBufferSize2);

                byte[] buffer2 = new byte[bufferSize2];
                int bytesRead2 = mFileInputStream.read(buffer2, 0, bufferSize2);


                // read image
                while (bytesRead2 > 0) {
                    dos.write(buffer2, 0, bufferSize2);
                    bytesAvailable2 = mFileInputStream.available();
                    bufferSize2 = Math.min(bytesAvailable2, maxBufferSize2);
                    bytesRead2 = mFileInputStream.read(buffer2, 0, bufferSize2);
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                mFileInputStream.close();
                InputStream is2 = conn2.getInputStream();
                // finish upload...

                dos = new DataOutputStream(conn3.getOutputStream());
                mFileInputStream = new FileInputStream(img_path3);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\""+filename+"\"3;filename=\"" + img_path3 + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                int bytesAvailable3 = mFileInputStream.available();
                int maxBufferSize3 = 1024;
                int bufferSize3 = Math.min(bytesAvailable3, maxBufferSize3);

                byte[] buffer3 = new byte[bufferSize3];
                int bytesRead3 = mFileInputStream.read(buffer3, 0, bufferSize3);


                // read image
                while (bytesRead3 > 0) {
                    dos.write(buffer3, 0, bufferSize3);
                    bytesAvailable3 = mFileInputStream.available();
                    bufferSize3 = Math.min(bytesAvailable3, maxBufferSize3);
                    bytesRead3 = mFileInputStream.read(buffer3, 0, bufferSize3);
                }

                mFileInputStream.close();
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                dos.flush();
                dos.close();
                // get response
                InputStream is3 = conn3.getInputStream();

                StringBuffer b = new StringBuffer();
                for (int ch = 0; (ch = is.read()) != -1; ) {
                    b.append((char) ch);
                }
                is.close();
                System.out.println(b.toString());
                return b.toString();
            } catch (Exception e) {
                // TODO: handle exception
            }
            return "1";
        }

        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            System.out.println(result);

            loading.dismiss();
            if(result=="1")
            {
                Toast.makeText(getApplicationContext(),"업로드 실패",Toast.LENGTH_LONG).show();

                System.out.println("Zz");
            }
            else {
                Toast.makeText(getApplicationContext(),"업로드 성공",Toast.LENGTH_LONG).show();


                System.out.println("성공");
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
                        finishTime.getText().toString(),areaLocation,
                        information.getText().toString()
                ); //1번
                http task2 = new http();
                task2.execute(img_path); //1번
                storeimapathTwo+=storeName;
                System.out.println(storeimapathTwo.replaceAll(" ","")+"선택");
                Glide.clear(storePic);
                resetGlide();

                Glide.with(this)
                        .load(storeimapathTwo.replaceAll(" ",""))
                        .signature(new StringSignature(UUID.randomUUID().toString())).into(storePic);

                break;
        }
        ////////////////////////////////////////// 카테고리 그룹체크
        if(foodButton1.isChecked()){
            foodCheck="한식";
        }
        if(foodButton2.isChecked()){
            foodCheck="중식";
        }
        if(foodButton3.isChecked()){
            foodCheck="양식";
        }
        if(foodButton4.isChecked()){
            foodCheck="일식";
        }
        if(foodButton5.isChecked()){
            foodCheck="분식";
        }
        if(foodButton6.isChecked()){
            foodCheck="디저트";
        }

    }
    private void resetGlide(){
        Glide.get(FoodActivity.this).clearMemory();
        new AsyncTask<Integer, Void, Void>(){
            @Override
            protected Void doInBackground(Integer... params) {
                Glide.get(FoodActivity.this).clearDiskCache();

                return null;
            }
        }.execute(1, 2, 3, 4, 5);
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
                String URL ="http://3.12.173.221:8080/SunhanWeb/androidaddStoreServlet.do";
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
}