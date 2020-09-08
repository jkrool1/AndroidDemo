package com.hsy.seekbar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private SeekBar seekBar;
    private TextView textview_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar);
        textview_1 = findViewById(R.id.textView_1);
        seekBar.setOnSeekBarChangeListener(this);
    }
    /**
     * 获取现在的滑动信息（将百分比显示到页面上）
     * */
    void show_precent(){

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        //获取现在的活动条信息
        int now = seekBar.getProgress();
        //获取现在的活动条总长度
        int max = seekBar.getMax();
        float m = (float) now/max;
        m = m*100;
        int qz = (int)m;
        textview_1.setText(qz+"%");

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
