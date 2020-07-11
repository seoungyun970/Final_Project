package com.example.myapplication.Child;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.ChildMainChatContentAdapter;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.VO.ChildChatDetailVO;
import com.example.myapplication.VO.SunhansVO;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChildMainChatDetail extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    Button chatSubmitBtn;
    RequestQueue queue;
    EditText chatContentEditText;
    public ChildChatDetailVO ChildChatDetailVO;

    String fromId;
    String toID;
    SimpleDraweeView chatImage;
    TextView peopleId;

    ChildMainChatContentAdapter Adapter;//어뎁
    List<ChildChatDetailVO> childChatVOS;//프로필

    Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childmain_chat_detail);
        setComp();
        getToID();


        queue= Volley.newRequestQueue(this);
        getChatData();
        testStart();

        mToolbar = (Toolbar)findViewById(R.id.mreservetoolbar);
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
        chatImage=findViewById(R.id.chatImage);
        peopleId=findViewById(R.id.peopleId);

        chatSubmitBtn=findViewById(R.id.chatSubmitBtn);
        SunhansVO user2=((LoginActivity) LoginActivity.context_main).user;
        fromId=user2.getId();

        chatSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {;
                ChildMainChatSubmit task=new ChildMainChatSubmit();
                task.execute(fromId,toID,chatContentEditText.getText().toString());
                chatContentEditText.setText("");
                getChatData();
            }
        });
        chatContentEditText=findViewById(R.id.chatContentEditText);
        recyclerView = findViewById(R.id.chatRecyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
    }
    //이전 액티비티에서 받아오는 인텐트
    public void getToID() {
        Intent intent = getIntent();
        if(intent != null) {
            Bundle bld = intent.getExtras();
            Object obj = bld.get("ToID");
            if(obj != null && obj instanceof String) {
                this.toID = (String)obj;
            }
        }
        peopleId.setText(toID+"과의 채팅방");
    }
    public void getChatData(){
        String url="http://3.12.173.221:8080/SunhanWeb/androidchatlist.do?UserID="+fromId+"&toID="+toID;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      try {
                            JSONObject jsonObject=new JSONObject(response);

                            JSONArray jsonArrayArticles=jsonObject.getJSONArray("chating");

                            childChatVOS =new ArrayList<>();

                            JSONObject object1=jsonArrayArticles.getJSONObject(0);

                            ChildChatDetailVO profile=new ChildChatDetailVO();

                             profile.setMyProfile(object1.getString("myprofile"));
                             profile.setTotProfile(object1.getString("toprofile"));
                             Uri uri;
                             uri = Uri.parse("http://3.12.173.221:8080/SunhanWeb/"+object1.getString("toprofile"));
                             chatImage.setImageURI(uri);
                            for(int i=1, j=jsonArrayArticles.length(); i<j; i++){
                                JSONObject object=jsonArrayArticles.getJSONObject(i);
                                ChildChatDetailVO setingVO=new ChildChatDetailVO();
                                setingVO.setToID(object.getString("toid"));
                                setingVO.setFromID(object.getString("userid"));
                                setingVO.setChatContent(object.getString("chatcontent").replace("&nbsp;"," "));
                                setingVO.setChatTime(object.getString("chattime"));
                                setingVO.setTotProfile(profile.getTotProfile());
                                setingVO.setMyProfile(profile.getMyProfile());
                                childChatVOS.add(setingVO);
                            }
                            Adapter = new ChildMainChatContentAdapter(childChatVOS, ChildMainChatDetail.this);
                            recyclerView.scrollToPosition(Adapter.getItemCount() - 1);
                            recyclerView.setAdapter(Adapter);
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

//    전송 버튼 누를시
    private class ChildMainChatSubmit extends AsyncTask<String, String, String> {
        URL register_url;
        @Override
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/androidchatSubmit.do";
                String fromID=(String)params[0];    //유저아이디
                String toID =params[1];             //보낼상대
                String chatContent =params[2];
                String url_address = URL  +"?fromID=" + fromID + "&toID=" + toID +"&chatContent="+chatContent;

                register_url = new URL(url_address);
                BufferedReader in = new BufferedReader(new InputStreamReader(register_url.openStream()));

                String result1 = "";
                String temp = "";
                while ((temp = in.readLine()) != null) {
                    result1 += temp;
                }
                in.close();
                return result1;
            } catch (Exception e) {
                System.out.println("채팅 전송 실패" + e.getMessage());
                return new String("Exception : " + e.getMessage());
            }
        }
        protected void onPostExecute(String result1) {
            super.onPostExecute(result1);
            System.out.println("결과값이다!!!!!!!!!" + result1);
        }
    }
    private TimerTask second;
    private final Handler handler = new Handler();
    public void testStart() {
        second = new TimerTask() {
            @Override
            public void run() {
                Update();
            }
        };
        Timer timer = new Timer();
        timer.schedule(second, 0, 4000);
    }
    protected void Update() {
        Runnable updater = new Runnable() {
            public void run() {
                getChatData();
            }
        };
        handler.post(updater);
    }
}
