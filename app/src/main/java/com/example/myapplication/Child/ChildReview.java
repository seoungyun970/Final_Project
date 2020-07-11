package com.example.myapplication.Child;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ChildReview extends AppCompatActivity {
    EditText context;
    TextView tv_input_length,tv_input_length_right;
    RatingBar starCheck;
    TextView storeNameTextView;
    TextView reviewMainTextView;
    Button reviewBtn;
    int starCount,review_rno_result;
    String rv_sno_result,rv_userid_result;
    Toolbar mToolbar;
    //    LinearLayout contextBottom;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_review);
        setComp();
        click();
        Intent intent=getIntent();
        String shopname=intent.getExtras().getString("shopname");
        review_rno_result=intent.getExtras().getInt("review_rno");
        rv_userid_result=intent.getExtras().getString("rv_userid");
        rv_sno_result=intent.getExtras().getString("rv_sno");
        storeNameTextView.setText(shopname);

        mToolbar = (Toolbar) findViewById(R.id.searchtoolbar);
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
    //초기값 설정
    void setComp(){
        starCheck=findViewById(R.id.starCheck);
        storeNameTextView=findViewById(R.id.storeNameTextView);
        reviewMainTextView=findViewById(R.id.reviewMainTextView);
        context = findViewById(R.id.context);
        tv_input_length = findViewById(R.id.tv_input_length);
        tv_input_length.setVisibility(View.GONE);
        context.setVisibility(View.GONE);
        tv_input_length_right=findViewById(R.id.tv_input_length_right);
        tv_input_length_right.setVisibility(View.GONE);
        reviewBtn=findViewById(R.id.reviewBtn);
        reviewBtn.setVisibility(View.GONE);
//        contextTitle.findViewById(R.id.contextTitle);
//        contextTitle.setVisibility(View.GONE);
//        contextBottom=findViewById(R.id.contextBottom);
//        contextBottom.setVisibility(View.GONE);
    }
    void click(){
        // 별갯수 클릭시 이벤트
        starCheck.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                reviewMainTextView.setVisibility(View.GONE);
                context.setVisibility(View.VISIBLE);
                tv_input_length.setVisibility(View.VISIBLE);
                tv_input_length_right.setVisibility(View.VISIBLE);
                reviewBtn.setVisibility(View.VISIBLE);
//                contextBottom.setVisibility(View.VISIBLE);
                System.out.println("별클릭 갯수: "+v);
                starCount=(int)v;

            }
        });
        context.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                tv_input_length.setText(s.length()+"자");   //글자수 TextView에 보여주기.
            }
        });
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChildReview.this, ChildMain.class);
                startActivity(intent);

                childReviewRegister task = new childReviewRegister();
                task.execute( "0",String.valueOf(review_rno_result),rv_userid_result,
                        rv_sno_result,String.valueOf(starCount),context.getText().toString()
                );
                System.out.println("review_rno_result: "+String.valueOf(review_rno_result)+"rv_userid_result: "+rv_userid_result
                        +"rv_sno_result: "+rv_sno_result+"String.valueOf(starCount): "+String.valueOf(starCount)+
                        "context.getText().toString(): "+context.getText().toString()
                );

//                finish();
//                overridePendingTransition(R.anim.stay, R.anim.slide_down);
            }
        });
    }

    private class childReviewRegister extends AsyncTask<String, String, String> {

        ProgressDialog loading;
        URL register_url;

        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ChildReview.this, "리뷰를 작성중입니다.", null, true, true);
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
            Intent intent = new Intent(ChildReview.this, ChildMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

}