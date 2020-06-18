package com.example.myapplication.Store;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.StoreDetail;
import com.example.myapplication.VO.StoreVO;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class StoreRegisterAdapter extends RecyclerView.Adapter<StoreRegisterAdapter.MyViewHolder> {
    private List<StoreVO> storeVOS;
    private static View.OnClickListener onClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tv_item_movie_genre;
        public TextView tv_item_movie_title;
        public TextView tv_item_movie_content;
        public SimpleDraweeView iv_item_movie;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            tv_item_movie_genre=v.findViewById(R.id.tv_item_movie_genre);
            tv_item_movie_title=v.findViewById(R.id.tv_item_movie_title);
            tv_item_movie_content=v.findViewById(R.id.tv_item_movie_content);
            iv_item_movie =v.findViewById(R.id.iv_item_movie);
            rootView=v;

            v.setClickable(true);
            v.setEnabled(true);
            v.setOnClickListener(onClickListener);
        }
    }

    public StoreRegisterAdapter(List<StoreVO> myDataset, Context context, View.OnClickListener onClick) {
        storeVOS = myDataset;
        onClickListener=onClick;
        Fresco.initialize(context);
    }



    @NonNull
    @Override
    public StoreRegisterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_store, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        StoreVO storeData=storeVOS.get(position);

        holder.tv_item_movie_genre.setText(storeData.getFoodCheck());
        holder.tv_item_movie_title.setText(storeData.getStoreName());
        holder.tv_item_movie_content.setText(storeData.getStorephone());
        Uri uri = Uri.parse("http://3.12.173.221:8080/SunhanWeb/store/"+storeData.getImage());
        holder.iv_item_movie.setImageURI(uri);
        holder.rootView.setTag(position);
        //이미지 삽입
//        Uri uri = Uri.parse(storeData.getUrlToImage());
//        holder.iv_item_movie.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        //3항 연산자 mDataset==null이면 0을 반환하고 아니면 뒤를 실행
        return storeVOS ==null ? 0 : storeVOS.size();
    }
    public StoreVO getStoreVO(int position){
        return storeVOS !=null ? storeVOS.get(position) : null;
    }
}
