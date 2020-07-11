package com.example.myapplication.Child;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.example.myapplication.Adapter.StoreAdapter;
import com.example.myapplication.Pager.MyPagerAdapter;
import com.example.myapplication.Pager.ViewPagerItemFragment;
import com.example.myapplication.R;
import com.example.myapplication.Server.DataManager;
import com.example.myapplication.VO.SunhansVO;
import com.example.myapplication.models.Hat;
import com.example.myapplication.resources.Hats;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ChildMain extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    ImageView list;
    ImageView reserve;
    ImageView comunity;
    ImageView rank,koreanFoodImage;
    ImageView childReserveImageView;
    ImageView chineseFoodImage,westernFoodImage,sushiImage,snackFoodImageView,dessertFoodImage,childChatList;
    Toolbar myToolbar;
    TextView mainname;
    private StoreAdapter adapter = new StoreAdapter();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RequestQueue queue;
    public static Context context_main;

    private ViewPager mMyViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_main_activity);
        SunhansVO vo=DataManager.getInstance().GetLoginUser();
        mainname = findViewById(R.id.mainName);
        mainname.setText(vo.getName().toString());
        // 추가된 소스, Toolbar를 생성
        myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        // 툴바 타이틀 삭제
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new ChildNavigationHome()).commit();
                        return  true;
                    case R.id.action_around:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new ChildNavigationAround()).commit();
                        return  true;
                    case R.id.action_more:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new ChildNavigationMore()).commit();
                        return  true;
                }
                return false;
            }
        });
        dessertFoodImage=findViewById(R.id.dessertFoodImage);
        snackFoodImageView=findViewById(R.id.snackFoodImageView);
        chineseFoodImage=findViewById(R.id.chineseFoodImage);
        koreanFoodImage=findViewById(R.id.koreanFoodImage);
        westernFoodImage=findViewById(R.id.westernFoodImage);
        sushiImage=findViewById(R.id.sushiImage);
        childReserveImageView=findViewById(R.id.childReserveImageView);
        childChatList=findViewById(R.id.childChatList);

//        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new GridLayoutManager(this,2);
//        recyclerView.setLayoutManager(layoutManager);
//        queue= Volley.newRequestQueue(this);
//        getData();
        mTabLayout = findViewById(R.id.tab_layout);
        mMyViewPager = findViewById(R.id.pager);

        init();
    }
    private void init(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        Hat[] hats = Hats.getHats();
        for(Hat hat: hats){
            ViewPagerItemFragment fragment = ViewPagerItemFragment.getInstance(hat);
            fragments.add(fragment);
        }
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        mMyViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mMyViewPager, true);
    }
    //추가된 소스, ToolBar에 menu.xml을 인플레이트함

    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.child_toolbar_menu, menu);
        return true;
    }
    // 툴바 내정보사용
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_child_myinfo:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getApplicationContext(), "내정보", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, ChildMyinfo.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                return true;

            case R.id.action_child_serarch:
                Toast.makeText(getApplicationContext(), "검색", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(this, ChildSearch.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                return true;
            // 추후에 추가될 버튼
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.childReserveImageView:
                Intent intent=new Intent(ChildMain.this, ChildMainReserve.class);
                startActivity(intent);
                break;
            case R.id.koreanFoodImage:
                Intent intent1=new Intent(ChildMain.this,ChildMainKoreanFood.class);
                startActivity(intent1);
                break;
            case R.id.chineseFoodImage:
                Intent intent2=new Intent(ChildMain.this, ChildMainChineseFood.class);
                startActivity(intent2);
                break;
            case R.id.westernFoodImage:
                Intent intent3=new Intent(ChildMain.this, ChildMainwesternFood.class);
                startActivity(intent3);
                break;
            case R.id.sushiImage:
                Intent intent4=new Intent(ChildMain.this,ChildMainsushi.class);
                startActivity(intent4);
                break;
            case R.id.snackFoodImageView:
                Intent intent5=new Intent(ChildMain.this, ChildMainsnackFood.class);
                startActivity(intent5);
                break;
            case R.id.dessertFoodImage:
                Intent intent6=new Intent(ChildMain.this, ChildMainDessert.class);
                startActivity(intent6);
                break;
            case R.id.childChatList:
                Intent intent7=new Intent(ChildMain.this, ChildMainChatList.class);
                startActivity(intent7);
                break;
        }
    }

    //뷰페이저 어댑터
    class MoviePagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();

        public MoviePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment item){
            items.add(item);
        }

        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }



}
