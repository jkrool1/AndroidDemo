package com.example.viewpagerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;


import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity<MyPagerAdapter> extends AppCompatActivity {

    private ViewPager pager = null;
    private MyPagerAdapter adapter = null;
    private View[] views = new View[4];
    private int[] viewId = {R.layout.view1, R.layout.view2, R.layout.view3, R.layout.view4};
    //数据源
    private List<View> viewList = null;
    private List<String> titleList = null;


    // Fragment适配器
    private MyFragmentPagerAdapter myFragmentPagerAdapter = null;

    private List<Fragment> fragList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        //adapter = new MyPagerAdapter(viewList, titleList);
        // pager.setAdapter(adapter);

        pager.setAdapter(myFragmentPagerAdapter);
    }

    private void initViews() {
        pager = (ViewPager) findViewById(R.id.id_viewpager);
        fragList = new ArrayList<>();
        viewList = new ArrayList<>();
        titleList = new ArrayList<>();
        for (int i = 0; i < viewId.length; i++) {
            views[i] = View.inflate(this, viewId[i], null);
            viewList.add(views[i]);
        }

        fragList.add(new fragment1());
        fragList.add(new fragment2());
        fragList.add(new fragment3());
        fragList.add(new fragment4());

        // Fragment适配器
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragList, titleList);
    }

}