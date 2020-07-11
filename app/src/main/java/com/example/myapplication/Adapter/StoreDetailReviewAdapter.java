package com.example.myapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Child.ChildMain;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.Manager.ManagerMain;
import com.example.myapplication.R;
import com.example.myapplication.Server.DataManager;
import com.example.myapplication.VO.ReviewRegisterVO;
import com.example.myapplication.VO.SunhansVO;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StoreDetailReviewAdapter extends RecyclerView.Adapter<StoreDetailReviewAdapter.MyViewHolder>  {

    private List<ReviewRegisterVO> storeVOS;
    private static View.OnClickListener onClickListener;
    public static Context context_main;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView peopleNameTextView;
        public RatingBar starCheck;
        public TextView contextTitle;
        public TextView tv_input_length;
        public TextView tv_input_length_right,reviewTime;
        public EditText context1;
        public Button reviewFuseBtn,reviewDeleteBtn,reviewBossBtn,reviewBossSaveBtn;
        public LinearLayout bossLiner;
        public EditText bossReviewEditText;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            peopleNameTextView = v.findViewById(R.id.peopleNameTextView);
            tv_input_length = v.findViewById(R.id.tv_input_length);
            tv_input_length_right = v.findViewById(R.id.tv_input_length_right);
            context1 = v.findViewById(R.id.context1);
            starCheck = v.findViewById(R.id.starCheck);
            reviewFuseBtn=v.findViewById(R.id.reviewFuseBtn);
            reviewDeleteBtn=v.findViewById(R.id.reviewDeleteBtn);
            reviewTime=v.findViewById(R.id.reviewTime);
            reviewFuseBtn.setVisibility(View.GONE);
            reviewDeleteBtn.setVisibility(View.GONE);
            reviewBossBtn=v.findViewById(R.id.reviewBossBtn);
            bossLiner=v.findViewById(R.id.bossLiner);
            bossLiner.setVisibility(View.GONE);

            bossReviewEditText=v.findViewById(R.id.bossReviewEditText);
            reviewBossSaveBtn=v.findViewById(R.id.reviewBossSaveBtn);
            rootView = v;
            v.setClickable(true);
            v.setEnabled(true);
            v.setOnClickListener(onClickListener);
        }
    }
    public StoreDetailReviewAdapter(List<ReviewRegisterVO> myDataset, Context context, View.OnClickListener onClick) {
        storeVOS = myDataset;
        onClickListener=onClick;
        Fresco.initialize(context);
    }

    @NonNull
    @Override
    public StoreDetailReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {


        final ReviewRegisterVO storeData=storeVOS.get(position);
        holder.reviewBossBtn.setVisibility(View.GONE);
        holder.tv_input_length.setVisibility(View.GONE);
        holder.tv_input_length_right.setVisibility(View.GONE);
        System.out.println("@@@@@@@@@@"+storeData.toString());
        SimpleDateFormat datefm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date=null;
        String time =storeData.getReview_date();
        System.out.println("타임: "+time);
        try {
            date=datefm.parse(time);
            System.out.println("date: "+date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat StringFm = new SimpleDateFormat("yyyy년MM월dd일HH시mm분");
        holder.reviewTime.setText(StringFm.format(date).substring(5,17));
        holder.peopleNameTextView.setText(storeData.getReview_username()+"님");
        holder.tv_input_length.setText("0자");
        holder.tv_input_length_right.setText("/50자");
        holder.context1.setText(storeData.getReview_content());
        holder.starCheck.setRating(storeData.getReview_score());
        holder.starCheck.setIsIndicator(true);
        SunhansVO user2=((LoginActivity) LoginActivity.context_main).user;
        String a=user2.getId();
        System.out.println("아이디: "+a);
        SunhansVO Sv= DataManager.getInstance().GetLoginUser();

        if(storeData.getReview_userid().equals(a)){
            holder.reviewBossBtn.setVisibility(View.GONE);
            holder.starCheck.setIsIndicator(false);
            holder.context1.setEnabled(true);
            holder.reviewFuseBtn.setVisibility(View.VISIBLE);
            holder.reviewFuseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    childReviewUpdate task=new childReviewUpdate();
                    Context context = view.getContext();
                    task.execute("0",String.valueOf((int)holder.starCheck.getRating()),holder.context1.getText().toString(),String.valueOf(storeData.getReview_no()));
                    Intent intent=new Intent(view.getContext(), ChildMain.class);
                    context.startActivity(intent);
                }
            });
            holder.reviewDeleteBtn.setVisibility(View.VISIBLE);
            holder.reviewDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Context context = view.getContext();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("삭제 하시겠습니까?");
                    builder.setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    childReviewDelete task=new childReviewDelete();
                                    Context context=view.getContext();
                                    task.execute("0",String.valueOf(storeData.getReview_group()));
                                    Intent intent=new Intent(view.getContext(),ChildMain.class);
                                    context.startActivity(intent);
                                }
                            });
                    builder.show();
                }
            });
        }


        /////사장
        if(storeData.getReview_storeid().equals(a)){
            holder.reviewBossBtn.setVisibility(View.VISIBLE);
            holder.rootView.setTag(position);
            //////////////////////////////////boss
            holder.reviewBossBtn.setOnClickListener(new View.OnClickListener() {
                //                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                //답글달기 누를시
                public void onClick(View view) {
                    Context context=view.getContext();
                    holder.reviewBossBtn.setVisibility(View.GONE);
                    holder.bossLiner.setVisibility(View.VISIBLE);
//                    holder.bossLiner.setBackground(context.getResources().getDrawable(R.drawable.patch));
                    holder.reviewBossSaveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bossReviewAdd task=new bossReviewAdd();
                            Context context = view.getContext();
                            task.execute(String.valueOf(storeData.getReview_group()),"1",String.valueOf(storeData.getReview_rno()),
                                    storeData.getReview_userid(),storeData.getReview_storeid(),holder.bossReviewEditText.getText().toString());

                            System.out.println("");
                            Intent intent=new Intent(view.getContext(), ManagerMain.class);
                            context.startActivity(intent);

                        }
                    });
                }

            });}

        if(storeData.getReview_depth()==1){
            holder.context1.setEnabled(true);
            holder.tv_input_length.setVisibility(View.GONE);
            holder.tv_input_length_right.setVisibility(View.GONE);
            holder.reviewBossBtn.setVisibility(View.GONE);
            holder.peopleNameTextView.setText("ㄴ사장님");
            holder.starCheck.setVisibility(View.GONE);


            holder.reviewFuseBtn.setVisibility(View.VISIBLE);
            holder.reviewFuseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bossReviewUpdate task = new bossReviewUpdate();
                    Context context = view.getContext();
                    task.execute("1", holder.context1.getText().toString(), String.valueOf(storeData.getReview_no()));
                    Intent intent = new Intent(view.getContext(), ManagerMain.class);
                    context.startActivity(intent);
                }
            });
            holder.reviewDeleteBtn.setVisibility(View.VISIBLE);
            holder.reviewDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final Context context = view.getContext();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("답글을 삭제하시겠습니까?");
                    builder.setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    bossReviewDelete task = new bossReviewDelete();
                                    task.execute("1", String.valueOf(storeData.getReview_no()));
                                    Intent intent = new Intent(view.getContext(), ManagerMain.class);
                                    context.startActivity(intent);
                                }
                            });
                    builder.show();

                }
            });
            int sd=Integer.parseInt(Sv.getAdmin());

            if(sd==0)
            {
                System.out.println("들어옴!!");
                holder.reviewFuseBtn.setVisibility(View.GONE);
                holder.reviewDeleteBtn.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        //3항 연산자 mDataset==null이면 0을 반환하고 아니면 뒤를 실행
        return storeVOS ==null ? 0 : storeVOS.size();
    }
    public ReviewRegisterVO getStoreVO(int position){
        return storeVOS !=null ? storeVOS.get(position) : null;
    }


    private class childReviewUpdate extends AsyncTask<String, String, String> {
        URL register_url;
        @Override
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/androidreviewupdate.do";
                int admin = Integer.parseInt(params[0]);
                int review_score=Integer.parseInt(params[1]);
                String review_content=params[2];
                int review_no=Integer.parseInt(params[3]);


//
//                int result1=Integer.parseInt(params[6]);
                // 지역 13

                //                String _pic=(String) params[4];
                String url_address = URL  + "?admin=" + admin +"&review_score="+review_score+"&review_content="+review_content+ "&review_no="+review_no;
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
                System.out.println("리뷰 업데이트 실패" + e.getMessage());
                return new String("Exception : " + e.getMessage());
            }


        }

    }


    private class childReviewDelete extends AsyncTask<String, String, String> {
        URL register_url;
        @Override
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/androidreviewdel.do";
                int admin = Integer.parseInt(params[0]);
                int review_group=Integer.parseInt(params[1]);
//
//                int result1=Integer.parseInt(params[6]);
                // 지역 13

                //                String _pic=(String) params[4];
                String url_address = URL  + "?check=" + admin +"&review_no="+review_group;
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
                System.out.println("리뷰 삭제 실패" + e.getMessage());
                return new String("Exception : " + e.getMessage());
            }


        }

    }

    private class bossReviewAdd extends AsyncTask<String, String, String> {
        URL register_url;
        @Override
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/androidreviewadd.do";

                int review_group=Integer.parseInt(params[0]);
                int admin = Integer.parseInt(params[1]);
                int review_rno = Integer.parseInt(params[2]);
                String review_userid = (params[3]);
                String review_storeid = (params[4]);
                String review_content = (params[5]);
//                int result1=Integer.parseInt(params[6]);
                // 지역 13

                //                String _pic=(String) params[4];
                String url_address = URL  + "?review_group=" + review_group +"&admin="+admin
                        +"&review_rno="+review_rno+"&review_userid="+review_userid
                        +"&review_storeid="+review_storeid+"&review_content="+review_content;
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
                System.out.println("보스 리뷰등록 실패" + e.getMessage());
                return new String("Exception : " + e.getMessage());
            }


        }

    }

    private class bossReviewUpdate extends AsyncTask<String, String, String> {
        URL register_url;
        @Override
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/androidreviewupdate.do";
                int admin= Integer.parseInt(params[0]);
                int review_no = Integer.parseInt(params[2]);
                String review_content = (params[1]);
//                int result1=Integer.parseInt(params[6]);
                // 지역 13

                //                String _pic=(String) params[4];
                String url_address = URL  +"?admin="+admin+
                        "&review_content="+review_content+"&review_no="+review_no;
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
                System.out.println("보스 리뷰업데이트 실패" + e.getMessage());
                return new String("Exception : " + e.getMessage());
            }


        }

    }


    private class bossReviewDelete extends AsyncTask<String, String, String> {
        URL register_url;
        @Override
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/androidreviewdel.do";
                int check= Integer.parseInt(params[0]);
                int review_no = Integer.parseInt(params[1]);
//                int result1=Integer.parseInt(params[6]);
                // 지역 13

                //                String _pic=(String) params[4];
                String url_address = URL  +"?check="+check+ "&review_no="+review_no;
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
                System.out.println("보스 리뷰삭제 실패" + e.getMessage());
                return new String("Exception : " + e.getMessage());
            }


        }

    }

}