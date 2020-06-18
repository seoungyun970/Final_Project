package com.example.myapplication.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Child.ChildMain;
import com.example.myapplication.JSON.JSON;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.VO.SunhansVO;
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
import java.util.List;

public class ManagerMyinfoUpdate extends Activity {

    private static final int REQUEST_CODE = 0;
    private ImageView imageView;
    String img_path;
    String imageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_myinfoupdate_activity);
        imageView = findViewById(R.id.imagedata);

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
        tedPermission();
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
                    imageView.setImageBitmap(img);


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
                System.out.println(img_path);
                FileInputStream mFileInputStream = new FileInputStream(img_path);
                URL connectUrl = new URL("http://3.12.173.221:8080/SunhanWeb/andImageUpload.do");
                //URL connectUrl = new URL("http://localhost:8181/SunhanWeb/android/andImageUpload.jsp");
                // HttpURLConnection 통신
                HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.connect();
                // write data
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + img_path + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

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
                dos.flush();
                // finish upload...

                // get response
                InputStream is = conn.getInputStream();

                StringBuffer b = new StringBuffer();
                for (int ch = 0; (ch = is.read()) != -1; ) {
                    b.append((char) ch);
                }
                is.close();
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