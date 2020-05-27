package com.example.myapplication;

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
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Manager_main extends AppCompatActivity {

//    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    ImageView list;
    ImageView reserve;
    ImageView comunity;
    Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_main);

        list = findViewById(R.id.list);
        reserve = findViewById(R.id.reserve);
        comunity = findViewById(R.id.comunity);

        // 추가된 소스, Toolbar를 생성
        myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        // 툴바 타이틀 삭제
        getSupportActionBar().setDisplayShowTitleEnabled(false);


//        bottomNavigationView = findViewById(R.id.navigationView);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.action_home:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentHome()).commit();
//                        return  true;
//                    case R.id.action_search:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentSearch()).commit();
//                        return  true;
//                    case R.id.action_around:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentAround()).commit();
//                        return  true;
//                    case R.id.action_myinfo:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentMyinfo()).commit();
//                        return  true;
//                    case R.id.action_more:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentMore()).commit();
//                        return  true;
//                }
//                return false;
//            }
//        });

//        //뷰페이저
//
//        ViewPager pager = findViewById(R.id.pager);
//        pager.setOffscreenPageLimit(3);
//
//        MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager());
//
//        Fragment1 fragment1 = new Fragment1();
//        adapter.addItem(fragment1);
//
//        Fragment2 fragment2 = new Fragment2();
//        adapter.addItem(fragment2);
//
//        Fragment3 fragment3 = new Fragment3();
//        adapter.addItem(fragment3);
//
//        pager.setAdapter(adapter);

    }
    //추가된 소스, ToolBar에 menu.xml을 인플레이트함

    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_myinfo:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getApplicationContext(), "내정보", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, ManagerMyinfo.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);

        }
    }

    //onClick event
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.list:
                Intent intent=new Intent(Manager_main.this,FoodActivity.class);
                startActivity(intent);
                break;
            case R.id.reserve:
                Intent intent1=new Intent(Manager_main.this,ReserveActivity.class);
                startActivity(intent1);
                break;
            case R.id.comunity:
                Intent intent2=new Intent(Manager_main.this,ComunityActivity.class);
                startActivity(intent2);
                break;
            case R.id.rank:
                Intent intent3=new Intent(Manager_main.this,RankActivity.class);
                startActivity(intent3);
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



