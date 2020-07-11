package com.example.myapplication.Manager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManagerDonaUpdate extends AppCompatActivity {
    ImageView storePic;
    Button galleryBtn,cameraBtn;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private File tempFile;
    private Boolean isPermission = true;
    private Boolean isCamera = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_donaupdate_activity);
        storePic=findViewById(R.id.storePic);
        galleryBtn=findViewById(R.id.galleryBtn);
        cameraBtn=findViewById(R.id.cameraBtn);

        findViewById(R.id.galleryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tedPermission();
            // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if(isPermission) goToAlbum();
                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.cameraBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if(isPermission) takePhoto();
                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }
    // 앨범에서 이미지 가져오기
    private void goToAlbum() {
        isCamera = false;

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        tempFile = null;
                    }
                }
            }
            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {

            Uri photoUri = data.getData();

            setImage();

        } else if (requestCode == PICK_FROM_CAMERA) {

            setImage();

        }

    }
    ////////갤러리에서 이미지 가져오기!!!!!!!!
    private void setImage() {
        isCamera = true;
        ImageView storePic = findViewById(R.id.storePic);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        storePic.setImageBitmap(originalBm);

    }
    //카메라이미지 가져오기!!!!!!!!
    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile != null) {

            Uri photoUri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }
    //카메라에서 찍은 사진 저장 파일!!!!!!!!
    private File createImageFile() throws IOException {
        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "blackJin_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/blackJin/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }
}
