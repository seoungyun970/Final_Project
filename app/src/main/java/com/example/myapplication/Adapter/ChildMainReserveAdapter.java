package com.example.myapplication.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Child.ChildMain;
import com.example.myapplication.Child.ChildReview;
import com.example.myapplication.R;
import com.example.myapplication.VO.StoreMainReserveVO;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChildMainReserveAdapter extends RecyclerView.Adapter<ChildMainReserveAdapter.MyViewHolder> {
    private List<StoreMainReserveVO> storeMainReserveVOS;
    private static View.OnClickListener onClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout childreservelinear;
        public TextView TextView_title;
        public TextView TextView_Content;
        public TextView TextView_People;
        public TextView TextView_ment;
        public TextView cstatetime, cstatepeople;
        public TextView reserveHourText,reserveMinText,reservePeopleText;
        public EditText reserveHourEditText,reserveMinEditText,reservePeopleEditText;
        public Button reserveOkayBtn,reserveRefuseBtn,reserveModificationBtn,reserveStateBtn,reserveCancelBtn,reserveFuseBtn,nullBtn,finishBtn;
        public View rootView;
        public Button reviewBtn;


        public MyViewHolder(View v) {
            super(v);
            childreservelinear=v.findViewById(R.id.childreservelinear);
            TextView_title=v.findViewById(R.id.TextView_title);
            TextView_Content=v.findViewById(R.id.TextView_Content);
            TextView_People=v.findViewById(R.id.TextView_People);
            TextView_ment=v.findViewById(R.id.TextView_ment);
            cstatetime=v.findViewById(R.id.cstatetime);
            cstatepeople=v.findViewById(R.id.cstatepeople);
            reserveOkayBtn =v.findViewById(R.id.reserveOkayBtn);
            reserveRefuseBtn =v.findViewById(R.id.reserveRefuseBtn);
            reserveModificationBtn=v.findViewById(R.id.reserveModificationBtn);
            reserveStateBtn=v.findViewById(R.id.reserveStateBtn);
            reserveHourEditText=v.findViewById(R.id.reserveHourEditText);
            reserveMinEditText=v.findViewById(R.id.reserveMinEditText);
            reservePeopleEditText=v.findViewById(R.id.reservePeopleEditText);
            reserveHourText=v.findViewById(R.id.reserveHourText);
            reserveMinText=v.findViewById(R.id.reserveMinText);
            reservePeopleText=v.findViewById(R.id.reservePeopleText);
            reserveHourEditText.setVisibility(View.GONE);
            reserveMinEditText.setVisibility(View.GONE);
            reservePeopleEditText.setVisibility(View.GONE);
            reserveHourText.setVisibility(View.GONE);
            reserveMinText.setVisibility(View.GONE);
            reservePeopleText.setVisibility(View.GONE);
            reserveCancelBtn=v.findViewById(R.id.reserveCancelBtn);
            reserveFuseBtn=v.findViewById(R.id.reserveFuseBtn);
            nullBtn=v.findViewById(R.id.nullBtn);
            nullBtn.setVisibility(View.INVISIBLE);
            reserveCancelBtn.setVisibility(View.GONE);
            reserveFuseBtn.setVisibility(View.INVISIBLE);
            finishBtn=v.findViewById(R.id.finishBtn);
            finishBtn.setVisibility(View.INVISIBLE);
            reviewBtn=v.findViewById(R.id.reviewBtn);
            reviewBtn.setVisibility(View.GONE);
            rootView=v;

            v.setClickable(true);
            v.setEnabled(true);
            v.setOnClickListener(onClickListener);
        }
    }
    public ChildMainReserveAdapter(List<StoreMainReserveVO> myDataset, Context context, View.OnClickListener onClick) {
        storeMainReserveVOS = myDataset;
        onClickListener=onClick;
        Fresco.initialize(context);
    }
    @NonNull
    @Override
    public ChildMainReserveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_childmain_reserve_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final StoreMainReserveVO storeData=storeMainReserveVOS.get(position);
        holder.TextView_title.setText(storeData.getRv_shopname());
        final String shopName=holder.TextView_title.getText().toString();
        SimpleDateFormat datefm = new SimpleDateFormat("yyyyMMddhhmm");
        Date date=null;
        String time1 =storeData.getRv_time();
        try {
            date=datefm.parse(time1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat StringFm = new SimpleDateFormat("MM월dd일 hh시mm분");
        holder.TextView_Content.setText(StringFm.format(date));
        holder.TextView_People.setText(String.valueOf(storeData.getRv_personnel())+"명");
        if(storeData.getRv_status()==1){
            holder.reserveStateBtn.setText("예약을 확인하고 있습니다. 잠시만 기다려주세요!");
            holder.reserveStateBtn.setEnabled(false);
            holder.reserveFuseBtn.setVisibility(View.VISIBLE);
            holder.finishBtn.setVisibility(View.GONE);
            holder.TextView_ment.setVisibility(View.GONE);
            holder.nullBtn.setVisibility(View.VISIBLE);
            holder.nullBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    ChildMainReserveCancel task=new ChildMainReserveCancel();
                    System.out.println("result: "+"4"+"status=: "+"4"+
                            "rno: "+storeData.getRv_rno()+"status: "+"4"+"rno: "+String.valueOf(storeData.getRv_rno())+"userid: "+storeData.getRv_userid()+"storeid: "+storeData.getRv_sno());
                    task.execute("4","4",
                            String.valueOf(storeData.getRv_rno()),storeData.getRv_userid()
                            ,storeData.getRv_sno()
                    );
                    Intent intent=new Intent(view.getContext(),ChildMain.class);
                    context.startActivity(intent);
                }
            });
            holder.reserveFuseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.finishBtn.setVisibility(View.VISIBLE);
                    holder.reserveHourText.setVisibility(View.VISIBLE);
                    holder.reserveMinText.setVisibility(View.VISIBLE);
                    holder.reservePeopleText.setVisibility(View.VISIBLE);
                    holder.reserveHourEditText.setVisibility(View.VISIBLE);
                    holder.reserveMinEditText.setVisibility(View.VISIBLE);
                    holder.reservePeopleEditText.setVisibility(View.VISIBLE);
                    holder.reserveFuseBtn.setVisibility(View.GONE);
                    holder.nullBtn.setVisibility(View.GONE);
                    String time=storeData.getRv_time();
//                    String now=time.substring(0,8);
//                    String option=time.substring(8,10);
//                    String min=time.substring(10,12);
                    holder.finishBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holder.TextView_title.setText(storeData.getRv_shopname());
                            Date date1=new Date();
                            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy년MM월dd일");
                            String a=dateFormat.format(date1);
                            String time =holder.reserveHourEditText.getText().toString()+holder.reserveMinEditText.toString();
                            System.out.println("시간!!!!!"+holder.reserveHourEditText.getText().toString()+"분: "+holder.reserveMinEditText.getText().toString());
                            Context context = view.getContext();
                            ChildMainReserveUpdate task=new ChildMainReserveUpdate();
//                            System.out.println("userid: "+storeData.getRv_userid()+"storeid: "+storeData.getRv_sno()+
//                                    "rno: "+storeData.getRv_rno()+"status: "+String.valueOf(storeData.getRv_status())+"reason: "+"0");
                            task.execute("-1",storeData.getRv_time().substring(0,8),holder.reserveHourEditText.getText().toString(),
                                    holder.reserveMinEditText.getText().toString(),holder.reservePeopleEditText.getText().toString(),
                                    String.valueOf(storeData.getRv_rno()),"1");
                            Intent intent=new Intent(view.getContext(),ChildMain.class);
                            context.startActivity(intent);
//                            String url_address = URL  + "?now_yyyy=" + now +"&option="+option+
//                                    "&min="+min+ "&personnel="+people+"&rno=" + rno;
                        }
                    });
                }
            });
        }
        else if(storeData.getRv_status()==2){
            holder.reserveStateBtn.setText("예약이 승인되었습니다.");
            holder.TextView_ment.setVisibility(View.GONE);
            if(storeData.getRv_visit()==1){
                holder.TextView_Content.setVisibility(View.GONE);
                holder.TextView_People.setVisibility(View.GONE);
                holder.finishBtn.setVisibility(View.GONE);
                holder.reserveHourText.setVisibility(View.GONE);
                holder.reserveMinText.setVisibility(View.GONE);
                holder.reservePeopleText.setVisibility(View.GONE);
                holder.reserveHourEditText.setVisibility(View.GONE);
                holder.reserveMinEditText.setVisibility(View.GONE);
                holder.reservePeopleEditText.setVisibility(View.GONE);
                holder.reserveFuseBtn.setVisibility(View.GONE);
                holder.nullBtn.setVisibility(View.GONE);
                holder.reserveCancelBtn.setVisibility(View.GONE);
                holder.cstatepeople.setVisibility(View.GONE);
                holder.cstatetime.setVisibility(View.GONE);

                holder.reserveStateBtn.setText("아이가 방문을 완료하였습니다.");
                holder.reserveStateBtn.setText("방문 완료");
                holder.childreservelinear.setBackgroundResource(R.drawable.visit_success_c);
                holder.reviewBtn.setVisibility(View.VISIBLE);
                holder.reserveStateBtn.setEnabled(false);

                holder.reviewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(storeData.getRv_reviewno()==0){
                            Context context = view.getContext();
                            Activity activity = (Activity) context;
                            Intent intent=new Intent(view.getContext(),ChildReview.class);
                            intent.putExtra("shopname",shopName);
                            intent.putExtra("review_rno",storeData.getRv_rno());
                            System.out.println("rrrrrrrrrrrrrrr" +storeData.getRv_rno());
                            intent.putExtra("rv_userid",storeData.getRv_userid());
                            intent.putExtra("rv_sno",storeData.getRv_sno());
                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.sliding_up, R.anim.stay);
                        }
                        else {
                                Context context = view.getContext();
                                Activity activity = (Activity) context;
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("이미 리뷰를 작성하셨습니다.");
                                builder.setPositiveButton("확인",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                builder.show();
                            System.out.println("리뷰를 이미 작성했어요 ㅜ");
                        }
                    }
                });
            }
            else if(storeData.getRv_visit()==2){
                holder.TextView_Content.setVisibility(View.GONE);
                holder.TextView_People.setVisibility(View.GONE);
                holder.finishBtn.setVisibility(View.GONE);
                holder.reserveHourText.setVisibility(View.GONE);
                holder.reserveMinText.setVisibility(View.GONE);
                holder.reservePeopleText.setVisibility(View.GONE);
                holder.reserveHourEditText.setVisibility(View.GONE);
                holder.reserveMinEditText.setVisibility(View.GONE);
                holder.reservePeopleEditText.setVisibility(View.GONE);
                holder.reserveFuseBtn.setVisibility(View.GONE);
                holder.nullBtn.setVisibility(View.GONE);
                holder.reserveCancelBtn.setVisibility(View.GONE);
                holder.cstatetime.setVisibility(View.GONE);
                holder.cstatepeople.setVisibility(View.GONE);
                holder.TextView_ment.setVisibility(View.GONE);
                holder.reserveStateBtn.setText("아이가 방문을 하지않았습니다.");
                holder.reserveStateBtn.setText("미방문");
                holder.childreservelinear.setBackgroundResource(R.drawable.visit_fail_c);
                holder.reserveStateBtn.setEnabled(false);
            }
        }
        else if(storeData.getRv_status()==3){
            holder.TextView_Content.setVisibility(View.GONE);
            holder.TextView_People.setVisibility(View.GONE);
            holder.finishBtn.setVisibility(View.GONE);
            holder.reserveHourText.setVisibility(View.GONE);
            holder.reserveMinText.setVisibility(View.GONE);
            holder.reservePeopleText.setVisibility(View.GONE);
            holder.reserveHourEditText.setVisibility(View.GONE);
            holder.reserveMinEditText.setVisibility(View.GONE);
            holder.reservePeopleEditText.setVisibility(View.GONE);
            holder.reserveFuseBtn.setVisibility(View.GONE);
            holder.nullBtn.setVisibility(View.GONE);
            holder.reserveCancelBtn.setVisibility(View.GONE);
            holder.cstatepeople.setVisibility(View.GONE);
            holder.cstatetime.setVisibility(View.GONE);
            holder.reserveStateBtn.setText("예약이 거절되었습니다.");
            holder.childreservelinear.setBackgroundResource(R.drawable.visit_fail_c);
//            holder.TextView_ment.setVisibility(View.GONE);
            holder.TextView_ment.setText("거절 사유: "+storeData.getRv_reason());
            holder.reserveStateBtn.setEnabled(false);
        }
        else if(storeData.getRv_status()==4){
            holder.TextView_Content.setVisibility(View.GONE);
            holder.TextView_People.setVisibility(View.GONE);
            holder.finishBtn.setVisibility(View.GONE);
            holder.reserveHourText.setVisibility(View.GONE);
            holder.reserveMinText.setVisibility(View.GONE);
            holder.reservePeopleText.setVisibility(View.GONE);
            holder.reserveHourEditText.setVisibility(View.GONE);
            holder.reserveMinEditText.setVisibility(View.GONE);
            holder.reservePeopleEditText.setVisibility(View.GONE);
            holder.reserveFuseBtn.setVisibility(View.GONE);
            holder.nullBtn.setVisibility(View.GONE);
            holder.reserveCancelBtn.setVisibility(View.GONE);
            holder.cstatepeople.setVisibility(View.GONE);
            holder.cstatetime.setVisibility(View.GONE);
            holder.TextView_ment.setVisibility(View.GONE);
            holder.reserveStateBtn.setText("취소한 예약입니다.");
            holder.childreservelinear.setBackgroundResource(R.drawable.visit_fail_c);
            holder.reserveStateBtn.setEnabled(false);
            holder.reserveStateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        //아동예약 취소 부분 4
        //정보 수정 -1
        //후원자 예약승인 2
        //후원자 예약거절 3
        //방문변경 -2
        holder.rootView.setTag(position);
    }
    //////////////////////////////////////////////////여기서부터
    @Override
    public int getItemCount() {
        //3항 연산자 mDataset==null이면 0을 반환하고 아니면 뒤를 실행
        return storeMainReserveVOS ==null ? 0 : storeMainReserveVOS.size();
    }
    public StoreMainReserveVO getStoreVO(int position){
        return storeMainReserveVOS !=null ? storeMainReserveVOS.get(position) : null;
    }

    private class ChildMainReserveUpdate extends AsyncTask<String, String, String> {
        ProgressDialog loading;
        URL register_url;
        @Override
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/androidreserveupdate.do";
                //아동예약 취소 부분 4
                //정보 수정 -1
                //후원자 예약승인 2
                //후원자 예약거절 3
                int result=Integer.parseInt(params[0]);                                          //방문변경 -2
                String now=(String)params[1];
                String option=params[2];
                String min=params[3];
                int people=Integer.parseInt(params[4]);
                int rno=Integer.parseInt(params[5]);
                int status=Integer.parseInt(params[6]);
                // 지역 13

                //                String _pic=(String) params[4];
                String url_address = URL  +"?result=" + result + "&now_yyyy=" + now +"&option="+option+
                        "&minute="+min+ "&personnel="+people+"&rno=" + rno+"&status="+status;
                //"&now_yyyy="+  "&option=" + "&min"+"&personnel"+"&rno"예약수정

                System.out.println(url_address);
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
                System.out.println("예약 등록 실패" + e.getMessage());
                return new String("Exception : " + e.getMessage());
            }
        }

        protected void onPostExecute(String result1) {
            super.onPostExecute(result1);
            System.out.println("결과값이다!!!!!!!!!" + result1);
        }
    }
    private class ChildMainReserveCancel extends AsyncTask<String, String, String> {
        URL register_url;
        @Override
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/androidreserveupdate.do";
                //아동예약 취소 부분 4
                //정보 수정 -1
                //후원자 예약승인 2
                //후원자 예약거절 3
                //방문변경 -2
                int result=Integer.parseInt(params[0]);
                int status=Integer.parseInt(params[1]);
                int rno=Integer.parseInt(params[2]);
                String userid=params[3];
                String storeid=params[4];
                // 지역 13

                //                String _pic=(String) params[4];
                String url_address = URL  + "?result=" + result +"&status="+status+
                        "&rno="+rno+ "&userid="+userid+"&storeid=" + storeid;
                System.out.println(url_address);
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
                System.out.println("예약 등록 실패" + e.getMessage());
                return new String("Exception : " + e.getMessage());
            }
        }

        protected void onPostExecute(String result1) {
            super.onPostExecute(result1);
            System.out.println("결과값이다!!!!!!!!!" + result1);
        }
    }


}
