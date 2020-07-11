package com.example.myapplication.Child;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.ChildMainChatAdapter;
import com.example.myapplication.R;
import com.example.myapplication.Server.DataManager;
import com.example.myapplication.VO.ChildChatVO;
import com.example.myapplication.VO.SunhansVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChildMainChatList extends AppCompatActivity {
    RequestQueue queue;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String a;
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        setData();
        queue= Volley.newRequestQueue(this);
        getData();

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

    public void setData(){
        mRecyclerView =  findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void getData() {
        String url = "http://3.12.173.221:8080/SunhanWeb/androidchatBox.do";

        SunhansVO LoginUser=DataManager.getInstance().GetLoginUser();

        a=LoginUser.getId();
        String url_address=url+"?id="+a;
        System.out.println("방찬석2"+url_address);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ChildChatVO",response);
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArrayArticles=jsonObject.getJSONArray("chatlist");
                            List<ChildChatVO> childChatVOS=new ArrayList<>();
                            for(int i=0, j=jsonArrayArticles.length(); i<j; i++){
                                JSONObject object=jsonArrayArticles.getJSONObject(i);
//                                Log.d("ChildChatVO",object.toString());
                                ChildChatVO childChatVO=new ChildChatVO();
                                childChatVO.setProfile(object.getString("profile"));
                                childChatVO.setToprofile(object.getString("toprofile"));
                                childChatVO.setChatContent(object.getString("ChatContent").replace("&nbsp;"," "));
//                                System.out.println((object.getString("ChatContent").replace("&nbsp;"," ")).toString());
                                childChatVO.setToID(object.getString("ToID"));
                                childChatVO.setFromID(object.getString("FromID"));
                                childChatVO.setChatTime(object.getString("ChatTime"));
//                                System.out.println("상대방 아이디: "+object.getString("ToID")+"내아이디: "+object.getString("FromID"));
                                childChatVOS.add(childChatVO);
                            }
                            mAdapter = new ChildMainChatAdapter(childChatVOS, ChildMainChatList.this, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Object obj = v.getTag();
                                    if (obj != null) {
                                        int position = (int) obj;
                                        ((ChildMainChatAdapter) mAdapter).getStoreVO(position).getChatContent();
                                        Intent intent = new Intent(ChildMainChatList.this, ChildMainChatDetail.class);
                                        if (((ChildMainChatAdapter) mAdapter).getStoreVO(position).getToID().equals(a)) {
                                            intent.putExtra("ToID", ((ChildMainChatAdapter) mAdapter).getStoreVO(position).getFromID());
                                            startActivity(intent);
                                        } else {
                                            intent.putExtra("ToID", ((ChildMainChatAdapter) mAdapter).getStoreVO(position).getToID());
                                            startActivity(intent);
                                        }
                                    }
                                }
                            });
                            //풀고실행해보면 뒤집기댐 최근 메시지순으로 뒤집기
//                            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//                            linearLayoutManager.setReverseLayout(true);
//                            mRecyclerView.setLayoutManager(linearLayoutManager);
                            mRecyclerView.setAdapter(mAdapter);
                        }catch (Exception e){
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
