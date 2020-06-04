package com.example.myapplication.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.Pager.Fragment1;
import com.example.myapplication.Pager.Fragment2;
import com.example.myapplication.Pager.Fragment3;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ManagerMain extends AppCompatActivity {

    TextView mainname;
    Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_main_activity);
        mainname=findViewById(R.id.mainName);

        // 추가된 소스, Toolbar를 생성
        myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        // 툴바 타이틀 삭제
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //메인 이름
        Intent intent=getIntent();
        String username=intent.getExtras().getString("a");
        mainname.setText(username+"님");

//        //뷰페이저
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
            case R.id.msetting:
                Intent intent3=new Intent(ManagerMain.this, ManagerMore.class);
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



