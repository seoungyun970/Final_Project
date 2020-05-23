package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentMyinfo extends Fragment {

    private View view;
    private TextView infoupdatetxt;
    private Button donabtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_myinfo,container,false);

        infoupdatetxt = (TextView)view.findViewById(R.id.infoupdatetxt);
        //정보수정
        infoupdatetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyinfoUpdate.class);
                startActivity(intent);
            }
        });
        donabtn = (Button)view.findViewById(R.id.donabtn);
        donabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), DonaUpdate.class);
                startActivity(intent1);
            }
        });

        return view;
    }

}