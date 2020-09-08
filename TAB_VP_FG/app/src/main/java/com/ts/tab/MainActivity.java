package com.ts.tab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPagerManager mViewPagerManager;
    private ViewPager mViewPager;
    private List<String> mTilteList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.main_viewpager);
        init_TabLayout();
    }
    //初始化TabLayout
    public void init_TabLayout(){
        //将fragment装载进列表
        mFragmentList.add(new FragmentCenter());
        mFragmentList.add(new FragmentContacts());
        //设置模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout设置文字
        for (int i =0;i < mFragmentList.size();i++){
            mTilteList.add("选项"+i);
        }
        mViewPagerManager = new ViewPagerManager(getSupportFragmentManager(),1,mFragmentList,mTilteList);
        mViewPager.setAdapter(mViewPagerManager);

        mTabLayout.setupWithViewPager(mViewPager);
        //进入后默认为第二张图
        mViewPager.setCurrentItem(1);
    }
}
