package com.ts.dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private static final String DOWMLOAD_MESSAGE = "https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=276844021,506351893&fm=26&gp=0.jpg";
    private Button mDownloadButton;
    private ImageView mImageView;
    private DownloadPicture mDownloadPicture = new DownloadPicture();
    private int mLoopCreate = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDownloadButton = (Button) findViewById(R.id.picture_download);
        mImageView = findViewById(R.id.picture_image);
        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }


    private void showDialog() {
        final Dialog mDownloadDialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.download_dialog, null);
        mDownloadDialog.setContentView(view);
        TextView textView = mDownloadDialog.findViewById(R.id.download_message);
        textView.setText(getString(R.string.download_text) + DOWMLOAD_MESSAGE);
        mDownloadDialog.setCanceledOnTouchOutside(false);
        TextView buttonTrue = mDownloadDialog.findViewById(R.id.download_sure);
        TextView buttonFalse = mDownloadDialog.findViewById(R.id.download_false);
        mDownloadDialog.show();
        buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDownloadPicture.start();
                mDownloadDialog.dismiss();
            }
        });
        buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDownloadDialog.dismiss();
            }
        });
    }

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        mImageView.setImageBitmap(mDownloadPicture.bitmap);
                }
            }
        }
    };

    private class DownloadPicture extends Thread {
        private Bitmap bitmap = null;

        @Override
        public void run() {
            InputStream is = null;
            try {
                //获取URL连接
                HttpsURLConnection connection = (HttpsURLConnection) new URL(DOWMLOAD_MESSAGE).openConnection();
                //打开URL链接
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    is = connection.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bitmap = BitmapFactory.decodeStream(bis);
                    if (bitmap != null) {
                        mHandler.sendEmptyMessage(1);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "获取图片成功", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        mImageView.setImageBitmap(null);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "获取图片失败", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            } catch (IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "获取图片失败", Toast.LENGTH_LONG).show();
                    }
                });
                e.printStackTrace();
            }

        }
    }
}
