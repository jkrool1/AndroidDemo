package com.hsy.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {

    //匹配我们fragment中的TextView显示框
    private TextView tv;
    //fragment的传值
    private String name;
    //判断值
    private int num;

    private MyFragment myFragment;

    public MyFragment() {
    }

    //全局View
    private View view;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveIstanceState){
        System.out.println("传过来num的值："+num);
        switch (num){
            case 1:
                 view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment,container,false);
                break;
            case 2:
                view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_two,container,false);
                break;
            case 3:
                view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_three,container,false);
                break;
        }

        tv = (TextView) view.findViewById(R.id.fragment_tv);
        tv.setText(name);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("我变了-"+name);
            }
        });
        return view;
    }
}
