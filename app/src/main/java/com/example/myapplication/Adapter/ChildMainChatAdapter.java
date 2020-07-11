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

import com.example.myapplication.Child.ChildMainChatDetail;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.VO.ChildChatVO;
import com.example.myapplication.VO.SunhansVO;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class ChildMainChatAdapter extends RecyclerView.Adapter<ChildMainChatAdapter.MyViewHolder> {
    private List<ChildChatVO> childChatVOS;
    private static View.OnClickListener onClickListener;
    String a;

    public ChildMainChatAdapter(List<ChildChatVO> childChatVOS, ChildMainChatDetail childMainChatDetail) {
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView contentTextView;
        public SimpleDraweeView chatImage;
        public TextView chatTimeTextView;
        public TextView peopleId;
        public View rootView;

        public MyViewHolder(View v) {
            super(v);
            chatImage=v.findViewById(R.id.chatImage);
            contentTextView=v.findViewById(R.id.contentTextView);
            chatTimeTextView=v.findViewById(R.id.chatTimeTextView);
            peopleId=v.findViewById(R.id.peopleId);
            rootView=v;

            v.setClickable(true);
            v.setEnabled(true);
            v.setOnClickListener(onClickListener);
        }
    }
    public ChildMainChatAdapter(List<ChildChatVO> myDataset, Context context, View.OnClickListener onClick) {
        childChatVOS = myDataset;
        onClickListener=onClick;
        Fresco.initialize(context);
    }
    @NonNull
    @Override
    public ChildMainChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_childmain_chat_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ChildChatVO storeData = childChatVOS.get(position);
        holder.contentTextView.setText(storeData.getChatContent());
        holder.chatTimeTextView.setText(storeData.getChatTime());
        SunhansVO u=((LoginActivity) LoginActivity.context_main).user;
        String user=u.getId();
        Uri uri;
        if(user.equals(storeData.getToID()))
        {
            holder.peopleId.setText(storeData.getFromID());
            uri = Uri.parse("http://3.12.173.221:8080/SunhanWeb/"+storeData.getToprofile());

        }
        else{
            holder.peopleId.setText(storeData.getToID());
            uri = Uri.parse("http://3.12.173.221:8080/SunhanWeb/"+storeData.getProfile());
        }
        holder.chatImage.setImageURI(uri);
        holder.rootView.setTag(position);
    }
    @Override
    public int getItemCount() {
        //3항 연산자 mDataset==null이면 0을 반환하고 아니면 뒤를 실행
        return childChatVOS ==null ? 0 : childChatVOS.size();
    }
    public ChildChatVO getStoreVO(int position){
        return childChatVOS !=null ? childChatVOS.get(position) : null;
    }
}
