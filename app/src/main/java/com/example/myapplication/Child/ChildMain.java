package com.example.myapplication.Child;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Pager.Fragment1;
import com.example.myapplication.Pager.Fragment2;
import com.example.myapplication.Pager.Fragment3;
import com.example.myapplication.R;
import com.example.myapplication.Store.StoreAdapter;
import com.example.myapplication.Store.SampleData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ChildMain extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    ImageView list;
    ImageView reserve;
    ImageView comunity;
    ImageView rank;
    Toolbar myToolbar;
    private StoreAdapter adapter = new StoreAdapter();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_main_activity);

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


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);

        //아이템 로드
        adapter.setItems(new SampleData().getItems());



        //뷰 페이저
        ViewPager pager = findViewById(R.id.pager1);
        pager.setOffscreenPageLimit(3);

        ChildMain.MoviePagerAdapter adapter = new ChildMain.MoviePagerAdapter(getSupportFragmentManager());

        Fragment1 fragment1 = new Fragment1();
        adapter.addItem(fragment1);

        Fragment2 fragment2 = new Fragment2();
        adapter.addItem(fragment2);

        Fragment3 fragment3 = new Fragment3();
        adapter.addItem(fragment3);

        pager.setAdapter(adapter);
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
                startActivity(intent);
                overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                return true;

            case R.id.action_child_chat:
                Toast.makeText(getApplicationContext(), "채팅", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(this, ChildChat.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                return true;

            case R.id.action_child_serarch:
                Toast.makeText(getApplicationContext(), "검색", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(this, ChildSearch.class);
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

    //onClick event
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.list:
//                Intent intent=new Intent(ChildMain.this, ChildList.class);
//                startActivity(intent);
//                break;
//            case R.id.reserve:
//                Intent intent1=new Intent(ChildMain.this, ReserveActivity.class);
//                startActivity(intent1);
//                break;
//            case R.id.comunity:
//                Intent intent2=new Intent(ChildMain.this, ComunityActivity.class);
//                startActivity(intent2);
//                break;
//            case R.id.rank:
//                Intent intent3=new Intent(ChildMain.this, RankActivity.class);
//                startActivity(intent3);
//                break;
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

