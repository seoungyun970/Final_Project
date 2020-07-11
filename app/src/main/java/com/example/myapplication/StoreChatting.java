package com.example.myapplication;//package com.example.myapplication;
//
//import android.app.Activity;
//
//import java.util.ArrayList;
//import java.util.List;
////import org.apache.http.util.ByteArrayBuffer;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.myapplication.Adapter.ChildMainChatAdapter;
//import com.example.myapplication.Adapter.ChildMainReserveAdapter;
//import com.example.myapplication.Child.ChildMainReserve;
//import com.example.myapplication.Child.ChildMainReserveDetail;
//import com.example.myapplication.VO.ChildChatVO;
//import com.example.myapplication.VO.SunhansVO;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//public class StoreChatting extends Activity {
//    RequestQueue queue;
//    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
//    String a;
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chatting);
//
//        setData();
//        queue= Volley.newRequestQueue(this);
//        getData();
//
//
//    }
//    public void setData(){
//        mRecyclerView =  findViewById(R.id.my_recycler_view);
//
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//
//        mRecyclerView.setLayoutManager(mLayoutManager);
//    }
//
//    public void getData() {
//        String url = "http://3.12.173.221:8080/SunhanWeb/androidchatBox.do";
//        SunhansVO user2=((LoginActivity)LoginActivity.context_main).user;
//        a=user2.getId();
//        String url_address=url+"?id="+a;
//        System.out.println("방찬석2"+url_address);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_address,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("ChildChatVO",response);
//                        try{
//                            JSONObject jsonObject=new JSONObject(response);
//                            JSONArray jsonArrayArticles=jsonObject.getJSONArray("chatlist");
//                            List<ChildChatVO> childChatVOS=new ArrayList<>();
//                            for(int i=0, j=jsonArrayArticles.length(); i<j; i++){
//                                JSONObject object=jsonArrayArticles.getJSONObject(i);
////                                Log.d("ChildChatVO",object.toString());
//                                System.out.println("이나형!!!!!!!"+object.toString());
//                                ChildChatVO childChatVO=new ChildChatVO();
//                                childChatVO.setToImageView(object.getString("profile"));
//                                childChatVO.setSubstance(object.getString("ChatContent"));
//                                childChatVO.setToId(object.getString("ToID"));
//                                childChatVO.setChatTime(object.getString("ChatTime"));
//                                childChatVOS.add(childChatVO);
//                            }
//                            mAdapter = new ChildMainChatAdapter(childChatVOS, StoreChatting.this, new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
////                                    Object obj = v.getTag();
////                                    if(obj != null) {
////                                        int position = (int)obj;
////                                        ((ChildMainChatAdapter)mAdapter).getStoreVO(position).getSubstance();
////                                        Intent intent = new Intent(StoreChatting.this, ChildMainReserveDetail.class);
////                                        intent.putExtra("news", ((ChildMainReserveAdapter)mAdapter).getStoreVO(position));
////                                        startActivity(intent);
////                                    }
//                                }
//                            });
//
//                            mRecyclerView.setAdapter(mAdapter);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        queue.add(stringRequest);
//    }
//
//}
