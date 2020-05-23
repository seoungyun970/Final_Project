package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Manager_main extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    ImageView list;
    ImageView reserve;
    ImageView comunity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_main);

        list = findViewById(R.id.list);
        reserve = findViewById(R.id.reserve);
        comunity = findViewById(R.id.comunity);

        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentHome()).commit();
                        return  true;
                    case R.id.action_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentSearch()).commit();
                        return  true;
                    case R.id.action_around:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentAround()).commit();
                        return  true;
                    case R.id.action_myinfo:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentMyinfo()).commit();
                        return  true;
                    case R.id.action_more:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentMore()).commit();
                        return  true;
                }
                return false;
            }
        });

        //뷰페이저

        ViewPager pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager());

        Fragment1 fragment1 = new Fragment1();
        adapter.addItem(fragment1);

        Fragment2 fragment2 = new Fragment2();
        adapter.addItem(fragment2);

        Fragment3 fragment3 = new Fragment3();
        adapter.addItem(fragment3);

        pager.setAdapter(adapter);

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



