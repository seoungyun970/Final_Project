package com.example.myapplication.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.VO.ChildChatDetailVO;
import com.example.myapplication.VO.SunhansVO;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class ChildMainChatContentAdapter extends RecyclerView.Adapter<ChildMainChatContentAdapter.MyViewHolder>  {
    private List<ChildChatDetailVO> childChatDetailVOS;
    public static Context context_main;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView chatToID;
        public TextView chatUserContent;
        public TextView chatTime;
        public SimpleDraweeView chatProfileImage;
        public View rootView;


        public MyViewHolder(View v) {
            super(v);
            chatToID=v.findViewById(R.id.chatUserId);
            chatUserContent=v.findViewById(R.id.chatUserContent);
            chatProfileImage=v.findViewById(R.id.chatProfileImage);
            chatTime=v.findViewById(R.id.chatTime);
            rootView=v;

            v.setClickable(true);
            v.setEnabled(true);
        }
    }
    public ChildMainChatContentAdapter(List<ChildChatDetailVO> myDataset, Context context) {
        childChatDetailVOS = myDataset;
        Fresco.initialize(context);
    }
    @NonNull
    @Override
    public ChildMainChatContentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_childmain_chat_content_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChildChatDetailVO storeData=childChatDetailVOS.get(position);
        holder.chatUserContent.setText(storeData.getChatContent());
        holder.chatTime.setText(storeData.getChatTime());

        SunhansVO u=((LoginActivity) LoginActivity.context_main).user;
        String user=u.getId();
        Uri uri;
        if(user.equals(storeData.getFromID()))
        {
            holder.chatToID.setText("본인");
            uri = Uri.parse("http://3.12.173.221:8080/SunhanWeb/"+storeData.getMyProfile());

        }
        else{
            holder.chatToID.setText(storeData.getFromID());
            uri = Uri.parse("http://3.12.173.221:8080/SunhanWeb/"+storeData.getTotProfile());
        }
        holder.chatProfileImage.setImageURI(uri);

    }
    @Override
    public int getItemCount() {
        //3항 연산자 mDataset==null이면 0을 반환하고 아니면 뒤를 실행
        return childChatDetailVOS ==null ? 0 : childChatDetailVOS.size();
    }
    public ChildChatDetailVO getStoreVO(int position){
        return childChatDetailVOS !=null ? childChatDetailVOS.get(position) : null;
    }
}
