package com.example.myapplication.Child;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;

public class ChildNavigationMore extends Fragment {

    private View view;
    private Button kakaobtn;
    private Button csignoutbtn;
    private Button cprojectbtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.child_navigation_more,container,false);
        kakaobtn = (Button)view.findViewById(R.id.kakaobtn);
        csignoutbtn = (Button)view.findViewById(R.id.csignoutbtn);
        cprojectbtn = (Button)view.findViewById(R.id.cprojectbtn);


        //카카오버튼 이벤트
        kakaobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://open.kakao.com/o/sdyC3Cac"));
                startActivity(intent);
            }
        });

        csignoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity()/* 해당 액티비티를 가르킴 */)
                        .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent i = new Intent(getActivity()/*현재 액티비티 위치*/ , LoginActivity.class/*이동 액티비티 위치*/);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                        .show();
            }
        });

        cprojectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChildIntroduceProject.class);
                startActivity(intent);
            }
        });


        return view;
    }



}
