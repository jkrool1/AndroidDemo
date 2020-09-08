package com.hsy.anim_try;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView text_1;
    private Button but_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_1 = findViewById(R.id.text);
        but_1 = findViewById(R.id.button1);
        but_1.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                pingyi(view);
                break;
//            case R.id.button2:
//                suofang(view);
//                break;
//            case R.id.button3:
//                xuanzhuan(view);
//                break;
//            case R.id.button4:
//                toumingdu(view);
//                break;
        }

    }
    public void pingyi(View view){
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.view_anim);
        text_1.startAnimation(animation);
    }
}
