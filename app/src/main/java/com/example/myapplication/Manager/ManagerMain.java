package com.example.myapplication.Manager;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Child.ChildMainChatList;
import com.example.myapplication.FoodActivity;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.Pager.MyPagerAdapter;
import com.example.myapplication.Pager.ViewPagerItemFragment;
import com.example.myapplication.R;
import com.example.myapplication.StoreDetailReview;
import com.example.myapplication.VO.SunhansVO;
import com.example.myapplication.models.Hat;
import com.example.myapplication.resources.Hats;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ManagerMain extends AppCompatActivity implements View.OnClickListener {
    ImageView storeRegister;
    ImageView storeComnuity;
    ImageView imageView5;
    ImageView managerReview;
    TextView mainname;
    Toolbar myToolbar;
    ImageView managerMainReserveBtn;
    String a;
    String id,storeid;
    private ViewPager mMyViewPager;
    private TabLayout mTabLayout;

    public static Context context_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_main_activity);
        mainname=findViewById(R.id.mainName);
        storeRegister=findViewById(R.id.storeRegister);
        storeComnuity=findViewById(R.id.storeComunity);
        imageView5=findViewById(R.id.imageView5);
        managerReview=findViewById(R.id.managerReview);
        managerMainReserveBtn=findViewById(R.id.managerMainReserveBtn);
        // 추가된 소스, Toolbar를 생성
        myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        // 툴바 타이틀 삭제
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        context_main = this;
        //메인 이름
        Intent intent=getIntent();
//        String username=intent.getExtras().getString("a");
//        mainname.setText(username);
        SunhansVO user2=((LoginActivity) LoginActivity.context_main).user;
        a=user2.getName();
        id=user2.getId();
        storeid=user2.getId();
        mainname.setText(a);
        storeRegister.setOnClickListener(this);
        storeComnuity.setOnClickListener(this);

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
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    // 툴바 내정보사용
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

                // 추후에 추가될 버튼
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
//            case R.id.list:
//                Intent intent=new Intent(ManagerMain.this, FoodActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.reserve:
//                Intent intent1=new Intent(ManagerMain.this, ReserveActivity.class);
//                startActivity(intent1);
//                break;
//            case R.id.comunity:
//                Intent intent2=new Intent(ManagerMain.this, ComunityActivity.class);
//                startActivity(intent2);
//                break;
            case R.id.storeComunity:
                Intent intent5=new Intent(ManagerMain.this, ManagerComunity.class);
                startActivity(intent5);
                break;
            case R.id.storeRegister:
                Intent intent4=new Intent(ManagerMain.this, FoodActivity.class);
                startActivity(intent4);
                break;
            case R.id.msetting:
                Intent intent3=new Intent(ManagerMain.this, ManagerMore.class);
                startActivity(intent3);
                break;
            case R.id.managerMainReserveBtn:
                Intent intent6=new  Intent(ManagerMain.this,ManagerReserve.class);
                startActivity(intent6);
                break;
            case R.id.imageView5:
                Intent intent7=new Intent(ManagerMain.this, ChildMainChatList.class);
                startActivity(intent7);
                break;
            case  R.id.managerReview:
                Intent intent8=new Intent(ManagerMain.this, StoreDetailReview.class);
                intent8.putExtra("userid",id);
                intent8.putExtra("storeid",storeid);
                startActivity(intent8);
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



