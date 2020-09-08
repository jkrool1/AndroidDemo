package com.hsy.view;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //三个fragment
    //可以分别写三个fragment
    private MyFragment f1 = new MyFragment();
    private MyFragment f2 = new MyFragment();
    private MyFragment f3 = new MyFragment();

    //底部三个按钮
    private Button foot1;
    private Button foot2;
    private Button foot3;

    //判断值
    private int a_1 = 0;
    private int a_2 = 0;
    private int a_3 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foot1 = (Button) findViewById(R.id.btn1);
        foot2 = (Button) findViewById(R.id.btn2);
        foot3 = (Button) findViewById(R.id.btn3);
        foot1.setOnClickListener(this);
        foot2.setOnClickListener(this);
        foot3.setOnClickListener(this);

        //第一次初始化首页默认显示第一个fragment
        initFragment1();
    }

    //显示第一个fragment
    private void initFragment1(){
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        System.out.println("到达initFragment1");
        if (f1.getName() == null) {
            System.out.println("到达initFragment1_1");
            f1.setNum(1);
            f1.setName("李在赣什么");
        }
        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        System.out.println("11111111111111111111111111111"+f1.isAdded());

            if (!f1.isAdded()) {
                transaction.add(R.id.main_frame_layout, f1);
            }
            //隐藏所有fragment
            hideFragment(transaction);
            //显示需要显示的fragment
            transaction.show(f1);

        //第二种方式(replace)，初始化fragment
//        if(f1 == null){
//            f1 = new MyFragment("消息");
//        }
//        transaction.replace(R.id.main_frame_layout, f1);

        //提交事务
        transaction.commit();
    }

    //显示第二个fragment
    private void initFragment2(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        System.out.println("到达initFragment2");
        if (f2.getName() == null) {
            f2.setNum(2);
            f2.setName("什么恋");
        }
        System.out.println("11111111111111111111111111111"+f2.isAdded());
        if (!f2.isAdded()) {
            transaction.add(R.id.main_frame_layout, f2);
        }
            hideFragment(transaction);
            transaction.show(f2);


//        if(f2 == null) {
//            f2 = new MyFragment("联系人");
//        }
//        transaction.replace(R.id.main_frame_layout, f2);

        transaction.commit();
    }

    //显示第三个fragment
    private void initFragment3(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        System.out.println("到达initFragment3");
        if (f3.getName() == null) {
            f3.setNum(3);
            f3.setName("你是干什么的");
        }
        System.out.println("11111111111111111111111111111"+f3.isAdded());
        if (!f3.isAdded()) {
            transaction.add(R.id.main_frame_layout, f3);
        }
            hideFragment(transaction);
            transaction.show(f3);


//        if(f3 == null) {
//            f3 = new MyFragment("动态");
//        }
//        transaction.replace(R.id.main_frame_layout, f3);

        transaction.commit();
    }

    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction){


        if (f1.isAdded()) {
                System.out.println("到达hideFragment1");
                transaction.remove(f1);
        }
        if (f2.isAdded()) {
            System.out.println("到达hideFragment2");
            transaction.remove(f2);
        }
        if (f3.isAdded()) {
            System.out.println("到达hideFragment3");
            transaction.remove(f3);
        }
    }


    @Override
    public void onClick(View v) {
        if(v == foot1){
            initFragment1();
        }else if(v == foot2){
            initFragment2();
        }else if(v == foot3){
            initFragment3();
        }
    }
}
