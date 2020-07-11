package com.example.myapplication.Adapter;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Manager.ManagerMain;
import com.example.myapplication.R;
import com.example.myapplication.VO.ManagerMainReserveVO;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import static androidx.core.graphics.drawable.IconCompat.getResources;

public class ManagerMainReserveAdapter  extends RecyclerView.Adapter<ManagerMainReserveAdapter.MyViewHolder> {
    private List<ManagerMainReserveVO> managerMainReserveVOS;
    private static View.OnClickListener onClickListener;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mhildreservelinear;
        public TextView TextView_title;
        public TextView TextView_Content;
        public TextView TextView_People;
        public TextView statepeople;
        public TextView statetime;
        public TextView ment;
        public EditText reserveRefuseEditText;
        public Button reserveOkayBtn,reserveVisitOkayBtn,reserveNoVisitBtn,reserveModificationBtn,reserveRefuseBtn;
        public View rootView;

        public MyViewHolder(View v) {
            super(v);
            mhildreservelinear = v.findViewById(R.id.mhildreservelinear);
            TextView_title = v.findViewById(R.id.TextView_title);
            TextView_Content = v.findViewById(R.id.TextView_Content);
            TextView_People = v.findViewById(R.id.TextView_People);
            statepeople = v.findViewById(R.id.statepeople);
            statetime = v.findViewById(R.id.statetime);
            reserveOkayBtn = v.findViewById(R.id.reserveOkayBtn);
            reserveVisitOkayBtn=v.findViewById(R.id.reserveVisitOkayBtn);
            reserveVisitOkayBtn.setVisibility(View.GONE);
            reserveNoVisitBtn=v.findViewById(R.id.reserveNoVisitBtn);
            reserveModificationBtn=v.findViewById(R.id.reserveModificationBtn);
            reserveNoVisitBtn.setVisibility(View.GONE);
            reserveRefuseBtn=v.findViewById(R.id.reserveRefuseBtn);
            reserveRefuseEditText=v.findViewById(R.id.reserveRefuseEditText);
            reserveRefuseEditText.setVisibility(View.GONE);

            rootView = v;

            v.setClickable(true);
            v.setEnabled(true);
            v.setOnClickListener(onClickListener);
        }
    }
    public ManagerMainReserveAdapter(List<ManagerMainReserveVO> myDataset, Context context, View.OnClickListener onClick) {
        managerMainReserveVOS = myDataset;
        onClickListener=onClick;
        Fresco.initialize(context);
    }
    @NonNull
    @Override
    public ManagerMainReserveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_manager_reserve_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ManagerMainReserveVO storeData=managerMainReserveVOS.get(position);

        holder.reserveModificationBtn.setVisibility(View.GONE);
        //
        if(storeData.getRv_status()==1){
            SimpleDateFormat datefm = new SimpleDateFormat("yyyyMMddHHmm");
            Date date=null;
            String time =storeData.getRv_time();
            System.out.println("타임: "+time);
            try {
                date=datefm.parse(time);
                System.out.println("date: "+date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat StringFm = new SimpleDateFormat("yyyy년MM월dd일HH시mm분");
            holder.TextView_title.setText(storeData.getRv_userid()+"님");
            holder.TextView_Content.setText(StringFm.format(date).substring(5,17));
            holder.TextView_People.setText(String.valueOf(storeData.getRv_personnel())+"명");
//                holder.reserveRefuseEditText.setText("거절 사유: "+storeData.getRv_reason());
            System.out.println("현재!!!!!!!!!!!!!!!!!!"+storeData.getRv_status());
            holder.reserveOkayBtn.setText("예약승인");
            holder.reserveRefuseBtn.setText("예약거부");
            holder.reserveOkayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("상태"+storeData.getRv_status());
                    holder.reserveNoVisitBtn.setVisibility(View.VISIBLE);
                    holder.reserveVisitOkayBtn.setVisibility(View.VISIBLE);
                    holder.reserveOkayBtn.setVisibility(View.GONE);
                    holder.reserveRefuseBtn.setVisibility(View.GONE);
                    storeData.setRv_status(2);
                    Context context = view.getContext();
                    System.out.println(storeData.getRv_status());
                    ManagerMainReserveOkay task=new ManagerMainReserveOkay();
                    task.execute("2",String.valueOf(storeData.getRv_status()),String.valueOf(storeData.getRv_rno()));
                    Intent intent=new Intent(view.getContext(), ManagerMain.class);
                    Toast.makeText(context,"예약을 승인하였습니다.",Toast.LENGTH_LONG).show();
                    context.startActivity(intent);
                }
            });
            holder.reserveRefuseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    storeData.setRv_status(3);
                    System.out.println("거부 버튼 누를시 행동:"+storeData.getRv_status());
                    holder.reserveOkayBtn.setVisibility(View.GONE);
                    holder.reserveRefuseEditText.setVisibility(View.VISIBLE);
//                        holder.reserveRefuseEditText.setText("거절 사유: ");//storeData.getRv_reason()
                    holder.reserveModificationBtn.setVisibility(View.VISIBLE);
                    //확인
                    holder.reserveModificationBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holder.reserveNoVisitBtn.setVisibility(View.GONE);
                            holder.reserveVisitOkayBtn.setVisibility(View.GONE);
                            holder.reserveOkayBtn.setVisibility(View.GONE);
                            holder.reserveRefuseBtn.setVisibility(View.GONE);
                            holder.reserveModificationBtn.setVisibility(View.GONE);
                            holder.reserveRefuseEditText.setVisibility(View.GONE);
                            holder.TextView_Content.setText(holder.reserveRefuseEditText.getText());
                            holder.TextView_People.setVisibility(View.GONE);
                            ManagerMainReserveRefuse task=new ManagerMainReserveRefuse();
                            Context context = view.getContext();
                            NotificationCompat.Builder builder=new NotificationCompat.Builder(view.getContext())
                                    .setSmallIcon(R.drawable.kakao).setContentTitle("d").setContentText("제목")
                                    .setDefaults(Notification.DEFAULT_VIBRATE).setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setAutoCancel(true);
                            NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(0,builder.build());
                            task.execute("3","3",holder.reserveRefuseEditText.getText().toString(),
                                    String.valueOf(storeData.getRv_rno()),storeData.getRv_userid(),storeData.getRv_sno());
                            Intent intent=new Intent(view.getContext(), ManagerMain.class);
                            Toast.makeText(context,"예약을 거부하였습니다.",Toast.LENGTH_LONG).show();
                            context.startActivity(intent);
                        }
                    });
                }
            });
        }
        //승인
        else if(storeData.getRv_status()==2){
            SimpleDateFormat datefm = new SimpleDateFormat("yyyyMMddHHmm");
            Date date=null;
            String time =storeData.getRv_time();
            try {
                date=datefm.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(storeData.getRv_visit()==0){
                SimpleDateFormat StringFm = new SimpleDateFormat("yyyy년MM월dd일HH시mm분");
                holder.TextView_title.setText(storeData.getRv_userid()+"님");
                holder.TextView_Content.setText(StringFm.format(date).substring(5,17));
                holder.TextView_People.setText(String.valueOf(storeData.getRv_personnel())+" 명 방문 예정입니다");
                holder.reserveOkayBtn.setText("예약 승인이 완료되었습니다.");
                holder.reserveOkayBtn.setEnabled(false);
                holder.reserveRefuseBtn.setVisibility(View.GONE);
                holder.reserveNoVisitBtn.setVisibility(View.VISIBLE);
                holder.reserveVisitOkayBtn.setVisibility(View.VISIBLE);
            }
            else if(storeData.getRv_visit()==1){
                holder.TextView_title.setText(storeData.getRv_userid()+"님");
                holder.statetime.setText("예약상태  ");
                holder.reserveNoVisitBtn.setVisibility(View.GONE);
                holder.reserveModificationBtn.setVisibility(View.GONE);
                holder.reserveVisitOkayBtn.setVisibility(View.GONE);
                holder.reserveOkayBtn.setVisibility(View.GONE);
                holder.TextView_People.setVisibility(View.GONE);
                holder.reserveRefuseBtn.setVisibility(View.GONE);
                holder.statepeople.setVisibility(View.GONE);
                holder.mhildreservelinear.setBackgroundResource(R.drawable.visit_success_c);
                holder.TextView_Content.setText(storeData.getRv_personnel()+"명의 학생들이 방문을 완료하였습니다.");
            }
            else if(storeData.getRv_visit()==2){
                holder.TextView_title.setText(storeData.getRv_userid()+"님");
                holder.reserveNoVisitBtn.setVisibility(View.GONE);
                holder.reserveModificationBtn.setVisibility(View.GONE);
                holder.reserveVisitOkayBtn.setVisibility(View.GONE);
                holder.reserveOkayBtn.setVisibility(View.GONE);
                holder.TextView_People.setVisibility(View.GONE);
                holder.reserveRefuseBtn.setVisibility(View.GONE);
                holder.statetime.setText("예약상태  ");
                holder.statepeople.setVisibility(View.GONE);
                holder.mhildreservelinear.setBackgroundResource(R.drawable.visit_fail_c);
                holder.TextView_Content.setText(storeData.getRv_personnel()+"명의 학생들이 방문을 하지 않았습니다.");
            }
            //방문완료
            holder.reserveVisitOkayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    storeData.setRv_visit(1);
                    System.out.println("아이 방문: "+ storeData.getRv_visit());
                    System.out.println(storeData.toString());
                    holder.reserveNoVisitBtn.setVisibility(View.GONE);
                    holder.reserveModificationBtn.setVisibility(View.GONE);
                    holder.reserveVisitOkayBtn.setVisibility(View.GONE);
                    holder.reserveOkayBtn.setVisibility(View.GONE);
                    holder.statepeople.setVisibility(View.GONE);
                    holder.statetime.setText("예약상태  ");
                    holder.mhildreservelinear.setBackgroundResource(R.drawable.visit_success_c);
                    holder.TextView_Content.setText(storeData.getRv_personnel()+"명의 학생들이 방문을 완료하였습니다.");
                    holder.TextView_People.setVisibility(View.GONE);
                    System.out.println("아이가 방문을 완료하였습니다.");
                    Context context = view.getContext();
                    ManangerReserveVisitCheck task=new ManangerReserveVisitCheck();
                    task.execute("-2",String.valueOf(storeData.getRv_visit()),
                            String.valueOf(storeData.getRv_rno()),storeData.getRv_userid(),storeData.getRv_sno(),"1");
                    Intent intent=new Intent(view.getContext(), ManagerMain.class);
                    Toast.makeText(context,"방문을 완료하였습니다.",Toast.LENGTH_LONG).show();
                    context.startActivity(intent);

                }
            });
            //미방문
            holder.reserveNoVisitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    storeData.setRv_visit(2);
                    System.out.println("아이 미방문: "+ storeData.getRv_visit());
                    System.out.println(storeData.toString());
                    holder.reserveNoVisitBtn.setVisibility(View.GONE);
                    holder.reserveModificationBtn.setVisibility(View.GONE);
                    holder.reserveVisitOkayBtn.setVisibility(View.GONE);
                    holder.reserveOkayBtn.setVisibility(View.GONE);
                    holder.statepeople.setVisibility(View.GONE);
                    holder.TextView_People.setVisibility(View.GONE);
                    holder.mhildreservelinear.setBackgroundResource(R.drawable.visit_fail_c);
                    holder.TextView_Content.setText(storeData.getRv_personnel()+"명의 학생들이 방문을 하지 않았습니다.");
                    System.out.println("아이가 방문을 하지 않았습니다.. ㅠㅠㅠ");
                    Context context = view.getContext();
                    ManangerReserveVisitCheck task=new ManangerReserveVisitCheck();
                    task.execute("-2","2",
                            String.valueOf(storeData.getRv_rno()),storeData.getRv_userid(),storeData.getRv_sno(),"2");
                    Intent intent=new Intent(view.getContext(), ManagerMain.class);
                    Toast.makeText(context,"학생이 방문을 하지 않았습니다.",Toast.LENGTH_LONG).show();
                    context.startActivity(intent);
                }
            });
        }

        else if(storeData.getRv_status()==3){
            holder.TextView_title.setText(storeData.getRv_userid()+"님");
            holder.TextView_Content.setText("후원자님이 예약을 거부하였습니다.");
            holder.TextView_People.setVisibility(View.GONE);
            holder.statetime.setText("예약상태  ");
            holder.statepeople.setVisibility(View.GONE);
            holder.reserveNoVisitBtn.setVisibility(View.GONE);
            holder.reserveModificationBtn.setVisibility(View.GONE);
            holder.reserveVisitOkayBtn.setVisibility(View.GONE);
            holder.reserveOkayBtn.setVisibility(View.GONE);
            holder.mhildreservelinear.setBackgroundResource(R.drawable.visit_fail_c);
            holder.reserveRefuseBtn.setVisibility(View.GONE);
        }
        else if(storeData.getRv_status()==4||storeData.getRv_status()==0){
            holder.TextView_title.setText(storeData.getRv_userid()+"님");
            holder.TextView_Content.setText("아이가 예약을 취소하였습니다.");
            holder.TextView_People.setVisibility(View.GONE);
            holder.reserveNoVisitBtn.setVisibility(View.GONE);
            holder.reserveModificationBtn.setVisibility(View.GONE);
            holder.reserveVisitOkayBtn.setVisibility(View.GONE);
            holder.mhildreservelinear.setBackgroundResource(R.drawable.visit_fail_c);
            holder.reserveOkayBtn.setVisibility(View.GONE);
        }
        //예약승인
        //예약거부
        holder.rootView.setTag(position);
        //이미지 삽입
//        Uri uri = Uri.parse(storeData.getUrlToImage());
//        holder.iv_item_movie.setImageURI(uri);
    }
    @Override
    public int getItemCount() {
        //3항 연산자 mDataset==null이면 0을 반환하고 아니면 뒤를 실행
        return managerMainReserveVOS ==null ? 0 : managerMainReserveVOS.size();
    }
    public ManagerMainReserveVO getStoreVO(int position){
        return managerMainReserveVOS !=null ? managerMainReserveVOS.get(position) : null;
    }


    private class ManagerMainReserveOkay extends AsyncTask<String, String, String> {
        URL register_url;
        @Override
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/androidreserveupdate.do";
                //방문변경 -2
                //후원자 예약승인 2
                //후원자 예약거절 3
                int result=Integer.parseInt(params[0]);
                int status=Integer.parseInt(params[1]);
                int rno=Integer.parseInt(params[2]);
                String url_address = URL  +"?result=" + result + "&status=" + status +"&rno="+rno;


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


    private class ManagerMainReserveRefuse extends AsyncTask<String, String, String> {
        URL register_url;
        @Override
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/androidreserveupdate.do";
                //방문변경 -2
                //후원자 예약승인 2
                //후원자 예약거절 3
                int result=Integer.parseInt(params[0]);
                int status=Integer.parseInt(params[1]);
                String reason=params[2];
                int rno=Integer.parseInt(params[3]);
                String userid=params[4];
                String storeid=params[5];

                String url_address = URL  +"?result=" + result + "&status=" + status +"&reason="+reason+"&rno="+rno+"&userid="+userid+"&storeid="+storeid;


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

    private class ManangerReserveVisitCheck extends AsyncTask<String, String, String> {
        URL register_url;
        @Override
        protected String doInBackground(String... params) {
            try {
                String URL = "http://3.12.173.221:8080/SunhanWeb/androidreserveupdate.do";
                //방문변경 -2
                //후원자 예약승인 2
                //후원자 예약거절 3
                int result=Integer.parseInt(params[0]);
                int visit=Integer.parseInt(params[1]);
                int rno=Integer.parseInt(params[2]);
                String userid=params[3];
                String storeid=params[4];
                int status=Integer.parseInt(params[5]);
                String url_address = URL  +"?result=" + result + "&visit=" + visit +"&rno="+rno+"&userid="+userid+"&storeid="+storeid+"&status="+status;


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
