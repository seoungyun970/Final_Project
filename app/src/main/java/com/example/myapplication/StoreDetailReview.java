package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

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
import com.example.myapplication.Adapter.StoreDetailReviewAdapter;
import com.example.myapplication.Child.ChildMain;
import com.example.myapplication.VO.ReviewCountVO;
import com.example.myapplication.VO.ReviewRegisterVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StoreDetailReview extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView shopNameTextView,recentReviewTextView,bossReviewTextView,starCountTextView;
    RatingBar starCheck;
    RequestQueue queue;
    String a1;
    Toolbar mToolbar;
    String a;
    String a2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail_review);
        Intent intent=getIntent();
        a1=intent.getExtras().getString("storeid");
        a2=intent.getExtras().getString("shopname");
        setData();
        queue= Volley.newRequestQueue(this);
        getData();
        getData1();

        mToolbar = (Toolbar)findViewById(R.id.myinfotoolbar);
        setSupportActionBar(mToolbar);
        // 툴바 뒤로가기 버튼생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 툴바 타이틀 삭제
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
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
        shopNameTextView=findViewById(R.id.shopNameTextView);
        shopNameTextView.setText(a2);
        starCheck=findViewById(R.id.starCheck);
        starCheck.setIsIndicator(true);
        recyclerView = findViewById(R.id.recyclerview);
        recentReviewTextView=findViewById(R.id.recentReviewTextView);
        bossReviewTextView=findViewById(R.id.bossReviewTextView);
        starCountTextView=findViewById(R.id.starCountTextView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    public void getData(){
        String url="http://3.12.173.221:8080/SunhanWeb/androidreviewlist.do?userid="+a1;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("StoreData",response);
                        //response를 StoreData Class에 분류
                        //try catch 구문 사용하는 이유는 response가 json이 아닌데 사용할려고 하다보니 생기는 문제!!!!!!!!!!!!!
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArrayArticles=jsonObject.getJSONArray("reviewList");
                            List<ReviewRegisterVO> storeVOS=new ArrayList<>();
                            for(int i=0, j=jsonArrayArticles.length(); i<j; i++){
                                JSONObject object=jsonArrayArticles.getJSONObject(i);
//                                Log.d("StoreData",object.toString());
                                ReviewRegisterVO storeVO=new ReviewRegisterVO();
                                storeVO.setReview_no(object.getInt("review_no"));
                                storeVO.setReview_group(object.getInt("review_group"));
                                storeVO.setReview_depth(object.getInt("review_depth"));
                                storeVO.setReview_rno(object.getInt("review_rno"));
                                storeVO.setReview_userid(object.getString("review_userid"));
                                storeVO.setReview_storeid(object.getString("review_storeid"));
                                storeVO.setReview_score(object.getInt("review_score"));
                                storeVO.setReview_content(object.getString("review_content"));
                                storeVO.setReview_date(object.getString("review_date"));
                                storeVO.setReview_shopname(object.getString("review_shopname"));
                                storeVO.setReview_username(object.getString("review_username"));
                                System.out.println("이나형getData"+storeVO.toString());
                                shopNameTextView.setText(object.getString("review_shopname"));
                                a=object.getString("review_shopname");
                                    storeVOS.add(storeVO);
                                    System.out.println("이나형getDataTotal "+storeVOS.toString());
                            }
                            // specify an adapter (see also next example)
                            mAdapter = new StoreDetailReviewAdapter(storeVOS, StoreDetailReview.this, new View.OnClickListener(){
                                @Override
                                public void onClick(View view) {
//                                    Object obj= view.getTag();
//                                    System.out.println(obj+"번째 화면 클릭!!!!!!!!!!");
//                                    if(obj!=null){
//                                        int position=(int) obj;
//                                        ((StoreDetailReviewAdapter) mAdapter).getStoreVO(position).getReview_no();
//                                        Intent intent=new Intent(StoreDetailReview.this, StoreDetail.class);
//                                        intent.putExtra("storeInfor", ((StoreDetailReviewAdapter)mAdapter).getStoreVO(position));
//                                        System.out.println(((StoreDetailReviewAdapter)mAdapter).getStoreVO(position).toString()+"넘기기전 값");
//                                        startActivity(intent);
//                                    }
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

    public void getData1(){
        String url="http://3.12.173.221:8080/SunhanWeb/androidreviewcount.do?userid="+a1;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("StoreData",response);
                        //response를 StoreData Class에 분류
                        //try catch 구문 사용하는 이유는 response가 json이 아닌데 사용할려고 하다보니 생기는 문제!!!!!!!!!!!!!
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArrayArticles=jsonObject.getJSONArray("reviewCntList");
                            List<ReviewCountVO> storeVOS=new ArrayList<>();
                            for(int i=0, j=jsonArrayArticles.length(); i<j; i++){
                                JSONObject object=jsonArrayArticles.getJSONObject(i);
//                                Log.d("StoreData",object.toString());
                                ReviewCountVO storeVO=new ReviewCountVO();
                                storeVO.setChild_count(object.getInt("child_count"));
                                storeVO.setSupporter_count(object.getInt("supporter_count"));
                                storeVO.setStar_avg(object.getInt("star_avg"));
                                storeVOS.add(storeVO);
                                System.out.println("이나형getData1 "+storeVOS.toString());
                                starCheck.setRating(object.getInt("star_avg"));
                                recentReviewTextView.setText("총 리뷰  :  "+object.getInt("child_count"));
                                bossReviewTextView.setText("사장님 댓글 : "+object.getInt("supporter_count"));
                                starCountTextView.setText(object.getInt("star_avg")+".0 점");
                            }
                            // specify an adapter (see also next example)
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


    private class childReviewRegister extends AsyncTask<String, String, String> {

        ProgressDialog loading;
        URL register_url;

        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(StoreDetailReview.this, "리뷰를 작성중입니다.", null, true, true);
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/androidreviewadd.do";
                int admin = Integer.parseInt(params[0]);
                int review_rno=Integer.parseInt(params[1]);
                String review_userid=(params[2]);
                String review_storeid=params[3];
                int review_score=Integer.parseInt(params[4]);
                String review_content=params[5];
//
//                int result1=Integer.parseInt(params[6]);
                // 지역 13

                //                String _pic=(String) params[4];
                String url_address = URL  + "?admin=" + admin +"&review_rno="+review_rno+"&review_userid="+review_userid+ "&review_storeid="+review_storeid+"&review_score=" + review_score+ "&review_content=" + review_content;
                ;
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
            } catch (Exception e) {
                System.out.println("리뷰 등록 실패" + e.getMessage());
                return new String("Exception : " + e.getMessage());
            }
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loading.dismiss();
            System.out.println("리뷰 결과값이다!!!!!!!!!" + result);
            Intent intent = new Intent(StoreDetailReview.this, ChildMain.class);
            startActivity(intent);
        }
    }
}
