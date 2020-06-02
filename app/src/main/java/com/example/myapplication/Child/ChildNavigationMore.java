package com.example.myapplication.Child;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class ChildNavigationMore extends Fragment {

    private View view;
    private Button kakaobtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.child_navigation_more,container,false);
        kakaobtn = (Button)view.findViewById(R.id.kakaobtn);

        //카카오버튼 이벤트
        kakaobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://open.kakao.com/o/sdyC3Cac"));
                startActivity(intent);
            }
        });
        return view;
    }



}
