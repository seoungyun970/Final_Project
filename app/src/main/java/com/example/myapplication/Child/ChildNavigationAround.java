package com.example.myapplication.Child;

import android.Manifest;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapView;

public class ChildNavigationAround extends Fragment {
    TMapView tMapView;
    TMapGpsManager tMapGps;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.child_navigation_around, container, false);

        LinearLayout linearLayoutTmap = v.findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1); //위치권한 탐색 허용 관련 내용
        }
        tMapView.setSKTMapApiKey( "3f208364-4dd5-40b5-8267-c2fe51d464c4" );
        linearLayoutTmap.addView( tMapView );
        tMapView.setIconVisibility(true); //현재위치로 표시될 아이콘을 표시할지 여부를 설정합니다
        tMapView.setZoom(23);

        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);

        ////////////////////////////////////////////////

        tMapGps=new TMapGpsManager(getActivity());
        tMapGps.setMinTime(1000);
        tMapGps.setMinDistance(5);
        tMapGps.setProvider(tMapGps.NETWORK_PROVIDER);  //연결된 인터넷으로 현위치 받기

        tMapGps.OpenGps();




        return v;
    }
}
