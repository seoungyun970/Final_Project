package com.example.myapplication.Manager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.Server.DataManager;
import com.example.myapplication.VO.SunhansVO;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ManagerMyinfoUpdate extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    public SimpleDraweeView myImage;
    String img_path;
    String imageName;
    Toolbar mToolbar;

    EditText name,phone,email,adress;
    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.manager_myinfoupdate_activity);
        myImage = findViewById(R.id.chatImage1);

        myImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
        tedPermission();

        final SunhansVO loginuser= DataManager.getInstance().GetLoginUser();

        updateBtn=findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SunhansRegisterUser task = new SunhansRegisterUser();
                task.execute(name.getText().toString()
                        ,phone.getText().toString()
                        ,email.getText().toString()
                        ,adress.getText().toString()
                        ,loginuser.getId());
            }
        });

        name=findViewById(R.id.MGname); name.setText(loginuser.getName());
        phone=findViewById(R.id.MGPhone); phone.setText(loginuser.getPhone());
        email=findViewById(R.id.MGemail); email.setText(loginuser.getEmail());
        adress=findViewById(R.id.MGaddr); adress.setText(loginuser.getAddr());




        mToolbar = (Toolbar)findViewById(R.id.updatetoolbar);
        setSupportActionBar(mToolbar);
        // 툴바 뒤로가기 버튼생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 툴바 타이틀 삭제
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Uri uri;
        uri = Uri.parse("http://3.12.173.221:8080/SunhanWeb/"+loginuser.getProfile());
        myImage.setImageURI(uri);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    myImage.setImageURI(data.getData());


                } catch (Exception e) {

                }
                img_path = getImagePathToUri(data.getData()); //이미지의 URI를 얻어 경로값으로 반환.
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }

    }
    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴

        System.out.println(data+"ddd");

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
        System.out.println(thePath);
        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        Toast.makeText(ManagerMyinfoUpdate.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        this.imageName = imgName;
        System.out.println(imageName);
        return imgPath;
    }
    public void DoFileUpload(String absolutePath) {
        http task = new http();
        task.execute(absolutePath); //1번
    }
    public void upload(View v){
        DoFileUpload(img_path);  //해당 함수를 통해 이미지 전송.
    }

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    private class http  extends AsyncTask<String, String, String>
    {
        ProgressDialog loading;
        URL register_url;

        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ManagerMyinfoUpdate.this, "업로드 중입니다.", null, true, true);
        }
        @Override
        //2번
        protected String doInBackground(String... params) {
            try {
                URL connectUrl = new URL("http://3.12.173.221:8080/SunhanWeb/andImageUpload.do");
                HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("img_1",img_path);
                conn.connect();

                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                String filename=((LoginActivity) LoginActivity.context_main).user.getId();

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\""+filename+"\";filename=\"" + img_path + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                FileInputStream mFileInputStream = new FileInputStream(img_path);


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
                InputStream is = conn.getInputStream();

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
                final SunhansVO loginuser=DataManager.getInstance().GetLoginUser();
                loginuser.setProfile("profile/"+imageName);
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

    private class SunhansRegisterUser extends AsyncTask<String, String, String>
    {
        ProgressDialog loading;
        URL register_url;
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ManagerMyinfoUpdate.this, "정보를 업데이트 중입니다.!", null, true, true);
        }
        @Override
        protected String doInBackground(String... params) {
            try{
                String URL ="http://3.12.173.221:8080/SunhanWeb/androidSunhansUpdateServlet.do";
                String _name = (String) params[0];
                String _phone = (String) params[1];
                String _email = (String) params[2];
                String _addr=(String) params[3];
                String _userid=(String) params[4];

                String url_address = URL
                        + "?name=" + _name
                        + "&phone=" + _phone
                        + "&email=" + _email
                        + "&addr=" + _addr
                        + "&userid=" + _userid ;
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
                return new String("Exception : " + e.getMessage());
            }
        }
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            loading.dismiss();
            if(result=="1")
            {
                Toast.makeText(getApplicationContext(),"업로드 실패",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"업로드 성공",Toast.LENGTH_LONG).show();
                final SunhansVO loginuser=DataManager.getInstance().GetLoginUser();
                loginuser.setName(name.getText().toString());
                loginuser.setPhone(phone.getText().toString());
                loginuser.setEmail(email.getText().toString());
                loginuser.setAddr(adress.getText().toString());
            }
            Intent intent = new Intent(ManagerMyinfoUpdate.this, ManagerMyinfo.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
    }
}