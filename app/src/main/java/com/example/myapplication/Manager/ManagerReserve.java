package com.example.myapplication.Manager;

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
import com.example.myapplication.Adapter.ManagerMainReserveAdapter;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.VO.ManagerMainReserveVO;
import com.example.myapplication.VO.SunhansVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManagerReserve extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager managerMainLayoutManager;
    Toolbar mToolbar;
    RequestQueue queue;
    public SunhansVO user;
    String a;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_reserve);
        // use a linear layout manager
        setData();
        queue = Volley.newRequestQueue(this);
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
        mRecyclerView =  findViewById(R.id.managerMain_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        managerMainLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(managerMainLayoutManager);
    }
    public void getData() {
        String url = "http://3.12.173.221:8080/SunhanWeb/androidreservesupporterlist.do";
        SunhansVO user2=((LoginActivity) LoginActivity.context_main).user;
        a=user2.getId();
        System.out.println("이나형"+a);
        String url_address=url+"?storeid="+a;
        System.out.println("이나형2"+url_address);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ManagerMainReserveVO",response);
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArrayArticles=jsonObject.getJSONArray("reserve");
                            List<ManagerMainReserveVO> managerMainReserveVOS=new ArrayList<>();
                            for(int i=0, j=jsonArrayArticles.length(); i<j; i++){
                                JSONObject object=jsonArrayArticles.getJSONObject(i);
//                                Log.d("ManagerMainReserveVO",object.toString());
                                if(object.getInt("rv_status")!=4){
//                                    System.out.println("이나형!!!!!!!"+object.toString());
                                    ManagerMainReserveVO managerMainReserveVO=new ManagerMainReserveVO();
                                    managerMainReserveVO.setRv_sno(object.getString("rv_sno"));
                                    managerMainReserveVO.setRv_rno(object.getInt("rv_rno"));
                                    managerMainReserveVO.setRv_time(object.getString("rv_time"));
                                    managerMainReserveVO.setRv_userid(object.getString("rv_userid"));
                                    managerMainReserveVO.setRv_personnel(object.getInt("rv_personnel"));
                                    managerMainReserveVO.setRv_date(object.getString("rv_date"));
                                    managerMainReserveVO.setRv_status(object.getInt("rv_status"));
//                                    System.out.println("예약상태입니다!!!!!!!"+object.getInt("rv_status"));
                                    managerMainReserveVO.setRv_visit(object.getInt("rv_visit"));
                                    managerMainReserveVO.setRv_reason(object.getString("rv_reason"));
                                    managerMainReserveVOS.add(managerMainReserveVO);
                                    System.out.println(object.getString("rv_date"));
                                }
                            }
                            mAdapter = new ManagerMainReserveAdapter(managerMainReserveVOS, ManagerReserve.this, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    Object obj = v.getTag();
//                                    if(obj != null) {
//                                        int position = (int)obj;
//                                        ((ManagerMainReserveAdapter)mAdapter).getStoreVO(position).getRv_userid();
//                                        Intent intent = new Intent(ManagerReserve.this, ManagerReserveDetail.class);
//                                        intent.putExtra("mainReserve", ((ManagerMainReserveAdapter)mAdapter).getStoreVO(position));
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
