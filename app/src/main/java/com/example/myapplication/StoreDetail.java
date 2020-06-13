package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Child.ChildMain;
import com.example.myapplication.Store.StoreRegisterAdapter;
import com.example.myapplication.VO.StoreImageVO;
import com.example.myapplication.VO.StoreVO;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class StoreDetail extends AppCompatActivity {
    StoreVO store;
    List<StoreImageVO> storeVOS;
    private TextView textCloseTime,textOpenTime,textFood,textShopName,textArea,textPhone,textComments,textTopmessage,textAddr;
    SimpleDraweeView imageFileRealName[]=new SimpleDraweeView[3];
    Button callbtn,chatbtn;
    Toolbar mToolbar;
    RequestQueue queue;
    String id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storedetail);
        setComp();
        getNewsDetail();
        getData();
        setNews();
        queue= Volley.newRequestQueue(this);
        mToolbar = (Toolbar)findViewById(R.id.storedetailtool);
        setSupportActionBar(mToolbar);
        // 툴바 뒤로가기 버튼생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 툴바 타이틀 삭제
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

    //기본 컴포넌트 셋팅
    public void setComp() {
        textCloseTime = findViewById(R.id.textCloseTime);
        textOpenTime=findViewById(R.id.textOpenTime);
        textFood=findViewById(R.id.textFood);
        textShopName=findViewById(R.id.textShopName);
        textArea=findViewById(R.id.textArea);
        textPhone=findViewById(R.id.textPhone);
        textComments=findViewById(R.id.textComments);
        textTopmessage=findViewById(R.id.textTopmessage);
        textAddr=findViewById(R.id.textAddr);
        imageFileRealName[0]=findViewById(R.id.imageFileRealName);
        imageFileRealName[1]=findViewById(R.id.imageFileRealName1);
        imageFileRealName[2]=findViewById(R.id.imageFileRealName2);
        callbtn=findViewById(R.id.callbtn);
        chatbtn=findViewById(R.id.chatbtn);
        queue= Volley.newRequestQueue(this);
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                System.out.println(store.getStorephone());
                intent.setData(Uri.parse("tel:"+store.getStorephone()));
                startActivity(intent);
            }
        });


        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(StoreDetail.this,StoreChatting.class);
                startActivity(intent1);
            }
        });

    }

    //이전 액티비티에서 받아오는 인텐트
    public void getNewsDetail() {
        Intent intent = getIntent();
        if(intent != null) {
            Bundle bld = intent.getExtras();
            Object obj = bld.get("storeInfor");
            System.out.println(obj+"storeInfor:");
            if(obj != null && obj instanceof StoreVO) {
                this.store = (StoreVO) obj;
            }
        }
    }

    //이전 액티비티에서 받아오는 인텐트에서 정보 뿌려준다
    public void setNews() {
        if(this.store != null) {

            String closeTime = this.store.getCloseTime();
            String openTime = this.store.getOpenTime();
            String food=this.store.getFoodCheck();
            String shopName=this.store.getStoreName();
            String area=this.store.getArea();
            String phone=this.store.getStorephone();
            String comments=this.store.getComments();
            String topMessage=this.store.getTopMessage();
            String addr=this.store.getSendData();
            String location=null;
            if(area.equals("a1")){
                location="강남/역삼/삼성/논현";
            }
            else if(area.equals("a2")){
                location="서초/신사/방배";
            }
            else if(area.equals("a3")){
                location="천호/길동/둔촌";
            }
            else if(area.equals("a4")){
                location="화곡/까지찬/양천/목동";
            }
            else if(area.equals("a5")){
                location="구로/금천/오류";
            }
            else if(area.equals("a6")){
                location="신촌/홍대/합정";
            }
            else if(area.equals("a7")){
                location="영신내/불광/응암";
            }
            else if(area.equals("a8")){
                location="종로/대학로/신도림";
            }
            else if(area.equals("a9")){
                location="성신여대/성북/월곡";
            }
            else if(area.equals("a10")){
                location="이태원/용산/서울역";
            }
            else if(area.equals("a11")){
                location="동대문/동묘/신당/충무";
            }
            else if(area.equals("a12")){
                location="회기/고려대/청량/신설";
            }
            else if(area.equals("a13")){
                location="건대/군자/구의";
            }
            else if(area.equals("a14")){
                location="왕십리/성수/금호";
            }
            else if(area.equals("a15")){
                location="수유/미아";
            }
            else if(area.equals("a16")){
                location="상봉/중랑/면목";
            }
            else if(area.equals("a17")){
                location="태릉/노원/도봉/창동";
            }

            try {
                textCloseTime.setText(closeTime);
                textOpenTime.setText(openTime);
                textFood.setText(food);
                textShopName.setText(shopName);
                textArea.setText(location);
                textPhone.setText(phone);
                textComments.setText(comments);
                textTopmessage.setText(topMessage);
                textAddr.setText(addr);

            }catch (Exception e){

            }

//            String content = this.store.getComments();
//            if(content != null) {
//                TextView_content.setText(content);
//            }
        }

        if(this.storeVOS!=null){
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa"+storeVOS.toString());



            try{



            }catch (Exception e){

            }
        }

    }
    public void getData(){
        String url="http://3.12.173.221:8080/SunhanWeb/androidgetStoreImageServlet.do?id="+store.getId();
        System.out.println("sssssssssssssssssssssssssssssssssssss"+url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        Log.d("storeImagelist",response);
                        //response를 StoreData Class에 분류
                        //try catch 구문 사용하는 이유는 response가 json이 아닌데 사용할려고 하다보니 생기는 문제!!!!!!!!!!!!!
                        try {
                            JSONObject jsonObject1=new JSONObject(response);
                            JSONArray jsonArrayArticles=jsonObject1.getJSONArray("storeImagelist");
                            storeVOS=new ArrayList<>();
                             for(int i=0, j=jsonArrayArticles.length(); i<j; i++){
                                JSONObject object=jsonArrayArticles.getJSONObject(i);
                                Log.d("storeImagelist",object.toString());
                                StoreImageVO storeVO=new StoreImageVO();
//                                System.out.println(object.getString("fileRealName"));
                                storeVO.setFileRealName(object.getString("fileRealName"));
                                storeVOS.add(storeVO);
                                 Uri uri1 = Uri.parse("http://3.12.173.221:8080/SunhanWeb/store/"+storeVOS.get(i).getFileRealName());
                                 System.out.println("uri11111111111111111111111"+uri1);
                                 imageFileRealName[i].setImageURI(uri1);
//                                 Uri uri2 = Uri.parse("http://3.12.173.221:8080/SunhanWeb/store/"+storeVOS.get(1).getFileRealName());
//                                 System.out.println(uri2);
//                                 imageFileRealName1.setImageURI(uri2);
//                                 Uri uri3 = Uri.parse("http://3.12.173.221:8080/SunhanWeb/store/"+storeVOS.get(2).getFileRealName());
//                                 System.out.println(uri3);
//                                 imageFileRealName2.setImageURI(uri3);
                            }
                            // specify an adapter (see also next example)
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }
}
