package com.example.myapplication.Child;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.Store.StoreRegisterAdapter;
import com.example.myapplication.StoreDetail;
import com.example.myapplication.VO.StoreVO;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.TMapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChildNavigationAround extends Fragment {
    TMapGpsManager tMapGps;
    private TMapView tMapView;
    RequestQueue queue;
    Bitmap bitmap;
    String foodCategory;
    Double d1,d2;
    Geocoder geocoder;
    LinearLayout linearLayoutTmap;
    Handler mHandler = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.child_navigation_around, container, false);
        mHandler = new Handler();
        Thread t = new Thread(new Runnable(){
            @Override public void run() {
                // UI 작업 수행 X
                 mHandler.post(new Runnable(){
                     @Override public void run() {
                         // UI 작업 수행 O
                            addMarker();
                            getData();
                          }
                    });
            }
        });
        t.start();
        tMapGps=new TMapGpsManager(getActivity());

        linearLayoutTmap = v.findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(getActivity());
        geocoder = new Geocoder(getActivity(), Locale.KOREAN);
        tMapView.setSKTMapApiKey("3f208364-4dd5-40b5-8267-c2fe51d464c4");

//        tMapView.setIconVisibility(true); //현재위치로 표시될 아이콘을 표시할지 여부를 설정합니다
        double lat = 37.5025595;
        double lng = 126.8423617;
        tMapView.setCenterPoint(lng, lat);
        tMapView.setZoomLevel(15);
        linearLayoutTmap.addView(tMapView);
        queue= Volley.newRequestQueue(getActivity());
        return v;
    }


    private void addMarker() {
        double lat = 37.5025595;        //지오코딩
        double lng = 126.8423617;       //지오코딩할것
        TMapPoint point = new TMapPoint(lat,lng);
        TMapMarkerItem marker = new TMapMarkerItem();
        marker.setPosition(0.5f, 1.0f);
        marker.setTMapPoint(point);
        tMapView.addMarkerItem("marker",marker);
    }
    public void setMarker(){

    }

    public void getData() {
        String url="http://3.12.173.221:8080/SunhanWeb/androidgetStoreServlet.do";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("StoreData",response);
                        //response를 StoreData Class에 분류
                        //try catch 구문 사용하는 이유는 response가 json이 아닌데 사용할려고 하다보니 생기는 문제!!!!!!!!!!!!!
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArrayArticles=jsonObject.getJSONArray("storelist");
                            List<StoreVO> storeVOS=new ArrayList<>();
                            List<Address> list=null;
                            for(int i=0, j=jsonArrayArticles.length(); i<j; i++){
                                final JSONObject object=jsonArrayArticles.getJSONObject(i);
                                Log.d("StoreData",object.toString());
                                StoreVO storeVO=new StoreVO();
                                storeVO.setId(object.getString("userid"));
                                storeVO.setFoodCheck(object.getString("food"));
                                System.out.println("음식"+object.getString("food"));
                                if(object.getString("food").equals("한식")){
                                    System.out.println("한식이다!!!");
                                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rice);
                                    int height=bitmap.getHeight();
                                    int width=bitmap.getWidth();
                                    bitmap = Bitmap.createScaledBitmap(bitmap, 120, height/(width/120), true);
                                }
                                else if(object.getString("food").equals("중식")){
                                    System.out.println("중식이다!!!");
                                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jajangmyeon);
                                    int height=bitmap.getHeight();
                                    int width=bitmap.getWidth();
                                    bitmap = Bitmap.createScaledBitmap(bitmap, 120, height/(width/120), true);
                                }
                                else if(object.getString("food").equals("양식")){
                                    System.out.println("양식이다!!!");
                                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sushi);
                                    int height=bitmap.getHeight();
                                    int width=bitmap.getWidth();
                                    bitmap = Bitmap.createScaledBitmap(bitmap, 120, height/(width/120), true);
                                }
                                else if(object.getString("food").equals("일식")){
                                    System.out.println("일식이다!!!");
                                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sushi);
                                    int height=bitmap.getHeight();
                                    int width=bitmap.getWidth();
                                    bitmap = Bitmap.createScaledBitmap(bitmap, 120, height/(width/140), true);
                                }
                                else if(object.getString("food").equals("분식")){
                                    System.out.println("분식이다!!!");
                                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rice);
                                    int height=bitmap.getHeight();
                                    int width=bitmap.getWidth();
                                    bitmap = Bitmap.createScaledBitmap(bitmap, 120, height/(width/120), true);
                                }
                                else if(object.getString("food").equals("디저트")){
                                    System.out.println("디저트다!!!");
                                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.desert);
                                    int height=bitmap.getHeight();
                                    int width=bitmap.getWidth();
                                    bitmap = Bitmap.createScaledBitmap(bitmap, 120, height/(width/120), true);
                                }
                                storeVO.setStoreName(object.getString("shopname"));
                                storeVO.setArea(object.getString("area"));
                                storeVO.setCloseTime(object.getString("closetime"));
                                storeVO.setOpenTime(object.getString("opentime"));
                                storeVO.setStorephone(object.getString("StorePhone"));
                                storeVO.setComments(object.getString("comments"));
                                storeVO.setImage(object.getString("fileRealName"));
                                //11
                                try {
//                                    String a=object.getString("addr");
                                    list=geocoder.getFromLocationName(
                                            object.getString("addr"),
                                            object.length() //읽은개수
                                    );
                                }catch (Exception e){
                                    e.printStackTrace();
                                    System.out.println("주소 변환시 에러발생");
                                }
                                if(list!=null){
                                    if(list.size()==0){
                                        System.out.println("길이가 없음!!!!!!!!!");
                                    }
                                    else{
                                        Address addr=list.get(0);
                                        float lat=(float)addr.getLatitude();
                                        float lon=(float)addr.getLongitude();
                                        TMapPoint point = new TMapPoint(lat,lon);
                                        final TMapMarkerItem marker = new TMapMarkerItem();
                                        marker.setPosition(0.5f, 1.0f);
                                        marker.setIcon(bitmap);
                                        marker.setTMapPoint(point);
                                        marker.setName(object.getString("shopname"));
                                        marker.setVisible(TMapMarkerItem.VISIBLE);

                                        //풍선뷰
                                        marker.setCalloutTitle(object.getString("shopname"));
                                        marker.setCanShowCallout(true);
                                        marker.setAutoCalloutVisible(true);
                                        //kakao image 라고 되어있는 부분 json이미지로 받아와야됨...
                                        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.find);
                                        int height=bitmap1.getHeight();
                                        int width=bitmap1.getWidth();
                                        bitmap1 = Bitmap.createScaledBitmap(bitmap1, 200, height/(width/100), true);
                                        marker.setCalloutRightButtonImage(bitmap1);
                                        final float lat1=lat;
                                        final float lon1=lon;


                                        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
                                            @Override
                                            public boolean onPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
                                                for (TMapMarkerItem item : arrayList) {
//                                                    Toast.makeText(getActivity(), String.valueOf(item.getTMapPoint()), Toast.LENGTH_LONG).show();

                                                    final TMapPoint tMapPointStart = new TMapPoint(37.5025595, 126.8423617);
                                                    final TMapPoint tMapPointEnd=new TMapPoint(item.latitude,item.longitude);
                                                    System.out.println(item.getName());
                                                    new Thread(){
                                                        @Override
                                                        public void run() {
                                                            try {
                                                                TMapPolyLine tMapPolyLine = new TMapData().findPathData(tMapPointStart, tMapPointEnd);
                                                                tMapPolyLine.setLineColor(Color.BLUE);
                                                                tMapPolyLine.setLineWidth(5);
                                                                tMapView.addTMapPolyLine("Line1", tMapPolyLine);
                                                                double distance = tMapPolyLine.getDistance()/1000;
                                                                System.out.println(distance);
                                                                Double dis=Math.round(distance*100)/100.0;   //거리
                                                                System.out.println(dis);
                                                                String disTime=String.format("%.0f",distance/40*50);    //분
                                                                Looper.prepare();
                                                                Toast.makeText(getActivity(),"예상 거리는 "+dis+"km이고 예상 시간은 "+disTime+"분 입니다.",Toast.LENGTH_LONG).show();
                                                                Looper.loop();
                                                            }catch (Exception e){
                                                                e.printStackTrace();
                                                            }
                                                            super.run();
                                                        }
                                                    }.start();
                                                    tMapView.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
                                                        @Override
                                                        public void onCalloutRightButton(TMapMarkerItem tMapMarkerItem) {
                                                            System.out.println("풍선뷰클릭");
                                                            System.out.println(tMapMarkerItem.longitude);
                                                            System.out.println(tMapMarkerItem.latitude);
                                                            String x = (String.valueOf(tMapMarkerItem.longitude));
                                                            String y = (String.valueOf(tMapMarkerItem.latitude));
                                                            TMapTapi tMapTapi = new TMapTapi(getActivity());
                                                            HashMap pathInfo = new HashMap();
                                                            pathInfo.put("rGoName", tMapMarkerItem.getName());
                                                            pathInfo.put("rGoX", x );
                                                            pathInfo.put("rGoY", y);

                                                            pathInfo.put("rStName", "출발지");
                                                            pathInfo.put("rStX", "126.8423617");
                                                            pathInfo.put("rStY", "37.5025595");
                                                            tMapTapi.invokeRoute(pathInfo);
                                                        }
                                                    });
                                                }
                                                Log.d("EndTest", " EndTest");
                                                return false;

                                            }

                                            @Override
                                            public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
                                                for (TMapMarkerItem item : arrayList) {
//                                                    TMapTapi tMapTapi = new TMapTapi(getActivity());
//
//                                                    tMapTapi.invokeRoute("목적지",(float) item.longitude , (float)item.latitude);


//                                                    TMapTapi tMapTapi = new TMapTapi(getActivity());
//
                                                }
                                                return false;
                                            }

                                        });
                                        tMapView.addMarkerItem("marker"+i,marker);
                                    }
                                }
                                storeVO.setTopMessage(object.getString("topmessage"));
                                storeVOS.add(storeVO);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }
}
