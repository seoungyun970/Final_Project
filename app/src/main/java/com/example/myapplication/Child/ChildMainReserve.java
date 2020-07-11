package com.example.myapplication.Child;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.ChildMainReserveAdapter;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.VO.StoreMainReserveVO;
import com.example.myapplication.VO.SunhansVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChildMainReserve extends AppCompatActivity {
    RequestQueue queue;
    public SunhansVO user;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar mToolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    String a;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childmain_reserve);
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
    public void onBackPressed(){
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
        String url = "http://3.12.173.221:8080/SunhanWeb/androidreservechildlist.do";
        SunhansVO user2=((LoginActivity) LoginActivity.context_main).user;
        a=user2.getId();
        System.out.println("이나형"+a);
        String url_address=url+"?id="+a;
        System.out.println("이나형2"+url_address);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("StoreMainReserveVO",response);
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArrayArticles=jsonObject.getJSONArray("reserve");
                            List<StoreMainReserveVO> storeMainReserveVOS=new ArrayList<>();
                            for(int i=0, j=jsonArrayArticles.length(); i<j; i++){
                                JSONObject object=jsonArrayArticles.getJSONObject(i);
//                                Log.d("StoreMainReserveVO",object.toString());
                                System.out.println("이나형!!!!!!!"+object.toString());
                                StoreMainReserveVO storeMainReserveVO=new StoreMainReserveVO();
                                storeMainReserveVO.setRv_sno(object.getString("rv_sno"));
                                storeMainReserveVO.setRv_rno(object.getInt("rv_rno"));
                                storeMainReserveVO.setRv_time(object.getString("rv_time"));
                                storeMainReserveVO.setRv_userid(object.getString("rv_userid"));
                                storeMainReserveVO.setRv_personnel(object.getInt("rv_personnel"));
                                storeMainReserveVO.setRv_date(object.getString("rv_date"));
                                storeMainReserveVO.setRv_status(object.getInt("rv_status"));
                                storeMainReserveVO.setRv_visit(object.getInt("rv_visit"));
                                storeMainReserveVO.setRv_reason(object.getString("rv_reason"));
                                storeMainReserveVO.setRv_shopname(object.getString("shopname"));
                                storeMainReserveVO.setRv_reviewno(object.getInt("review_no"));
                                storeMainReserveVOS.add(storeMainReserveVO);
                                System.out.println(object.getString("rv_date"));
                            }
                            mAdapter = new ChildMainReserveAdapter(storeMainReserveVOS, ChildMainReserve.this, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    Object obj = v.getTag();
//                                    if(obj != null) {
//                                        int position = (int)obj;
//                                        ((ChildMainReserveAdapter)mAdapter).getStoreVO(position).getRv_shopname();
//                                        Intent intent = new Intent(ChildMainReserve.this, ChildMainReserveDetail.class);
//                                        intent.putExtra("news", ((ChildMainReserveAdapter)mAdapter).getStoreVO(position));
//                                        startActivity(intent);
//                                    }
                                }
                            });
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
