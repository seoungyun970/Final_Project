package com.example.myapplication.Child;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

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
import com.example.myapplication.Adapter.StoreRegisterAdapter;
import com.example.myapplication.R;
import com.example.myapplication.StoreDetail;
import com.example.myapplication.VO.StoreVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChildSearch extends AppCompatActivity {
    Toolbar mToolbar;
    RecyclerView recyclerView;
    EditText serchText;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_search_activity);

        queue= Volley.newRequestQueue(this);
        mToolbar = (Toolbar) findViewById(R.id.searchtoolbar);
        setSupportActionBar(mToolbar);
        // 툴바 뒤로가기 버튼생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 툴바 타이틀 삭제
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);


        serchText = (EditText) findViewById(R.id.serch);
        serchText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    System.out.println(serchText.getText().toString()+"111111111");
                    getData(serchText.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });

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
    public void getData(String serch) {
        String url = "http://3.12.173.221:8080/SunhanWeb/androidSerchgetStoreServlet.do?serch=" + serch;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("StoreData", response);
                        //response를 StoreData Class에 분류
                        //try catch 구문 사용하는 이유는 response가 json이 아닌데 사용할려고 하다보니 생기는 문제!!!!!!!!!!!!!
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArrayArticles = jsonObject.getJSONArray("storelist");
                            List<StoreVO> storeVOS = new ArrayList<>();
                            for (int i = 0, j = jsonArrayArticles.length(); i < j; i++) {
                                JSONObject object = jsonArrayArticles.getJSONObject(i);
//                                Log.d("StoreData",object.toString());
                                StoreVO storeVO = new StoreVO();
                                storeVO.setId(object.getString("userid"));
                                storeVO.setFoodCheck(object.getString("food"));
                                storeVO.setStoreName(object.getString("shopname"));
                                storeVO.setArea(object.getString("area"));
                                storeVO.setCloseTime(object.getString("closetime"));
                                storeVO.setOpenTime(object.getString("opentime"));
                                storeVO.setStorephone(object.getString("StorePhone"));
                                storeVO.setComments(object.getString("comments"));
                                storeVO.setSendData(object.getString("addr"));
                                storeVO.setPrice(object.getString("price"));
                                storeVO.setTopMessage(object.getString("topmessage"));
                                storeVO.setImage(object.getString("fileRealName"));
                                String a = storeVO.getFoodCheck();
                                storeVOS.add(storeVO);
                            }
                            // specify an adapter (see also next example)
                            mAdapter = new StoreRegisterAdapter(storeVOS, ChildSearch.this, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Object obj = view.getTag();
                                    System.out.println(obj + "번째 화면 클릭!!!!!!!!!!");
                                    if (obj != null) {
                                        int position = (int) obj;
                                        ((StoreRegisterAdapter) mAdapter).getStoreVO(position).getOpenTime();
                                        Intent intent = new Intent(ChildSearch.this, StoreDetail.class);
                                        intent.putExtra("storeInfor", ((StoreRegisterAdapter) mAdapter).getStoreVO(position));
                                        System.out.println(((StoreRegisterAdapter) mAdapter).getStoreVO(position).toString() + "넘기기전 값");
                                        startActivity(intent);
                                    }
                                }
                            });
                            recyclerView.setAdapter(mAdapter);

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