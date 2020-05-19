package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentSearch extends Fragment implements TextWatcher {

    private  View view;
    RecyclerView recyclerView;
    EditText editText;
    RecyclerViewAdapter adapter;

    ArrayList<String> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        editText = (EditText)view.findViewById(R.id.edittext);
        editText.addTextChangedListener(this);

        items.add("곽기태");
        items.add("박승윤");
        items.add("방찬석");
        items.add("이나형");
        items.add("오나미");
        items.add("박새로이");
        items.add("디비정보");
        items.add("끌어올곳");

        adapter = new RecyclerViewAdapter(getActivity(), items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);


        return view;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
