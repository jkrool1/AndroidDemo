package com.ts.hc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int UPDATE_TEST_MSG_BY_CALLBACK = 10010;
    private static final int UPDATE_TEST_MSG_BY_LOCALBROADCAST = 10011;
    private static final int PICTURE_DOWNLOAD_SUCCESS = 200;
    private static final int PICTURE_DOWNLOAD_FAILED = 404;
    private static final int THREAD_SLEEP_TIME = 10000;
    private static final String INTENT_SLEEP = "MAKE_SYSTEM_SLEEP";
    private static final String TEXT_CHANGE_BY_CALLBACK = "休眠已结束_by回调";
    private static final String TEXT_CHANGE_BY_BROADCAST = "休眠已结束_by本地广播";
    private static final String DOWNLOAD_SUCCESS_TOAST = "下载成功，请查看图片";
    private static final String DOWNLOAD_FAILED_TOAST = "下载失败,请检查原因";
    private static final String GET_MESSAGE_FAILED = "获取消息失败，请重试！";
    private static final String HANDLE_MESSAGE_DEFAULT_TOAST_TEXT = "have nothing to do";
    private static final String DOWNLOAD_PICTURE_URL = "https://timgsa.baidu.com/timg?image&quality"
            + "=80&size=b9999_10000&sec=1561035976350&di=4ffe27dea7b3d34bcf299edbf44e1a79&imgtype=0&s"
            + "rc=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1405%2F"
            + "11%2Fc0%2F34090371_34090371_1399739404419.jpg";
    private static boolean DOWNLOAD_PICTURE_STATUS = false;
    private Button mButtonCallBack;
    private Button mButtonLocalBroadcast;
    private Button mButtonShowImageView;
    private ProgressBar mProgressBar;
    private ImageView mImageView;
    private TextView mTextView;
    private TestThread mTestThread = null;
    private PictureAsyncTask mPictureAsyncTask;
    IntentFilter mIntentFilter;
    LocalBroadcastManager mLocalBroadcastManager;

    private BroadcastReceiver mLocalBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String actionString = intent.getAction();
            if (INTENT_SLEEP.equals(actionString)) {
                if (mTestThread != null) {
                    mTestThread.interrupt();
                }
                mHandler.sendEmptyMessageDelayed(UPDATE_TEST_MSG_BY_LOCALBROADCAST, THREAD_SLEEP_TIME);
            } else {
                //nothing to do
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonCallBack = findViewById(R.id.btn_callBack);
        mButtonLocalBroadcast = findViewById(R.id.btn_broadcast);
        mButtonShowImageView = findViewById(R.id.btnShowImageView);
        mProgressBar = findViewById(R.id.image_download_progress);
        mTextView = findViewById(R.id.show_system_status);
        mImageView = findViewById(R.id.imageView);
        //获取实例
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mButtonCallBack.setOnClickListener(this);
        mButtonShowImageView.setOnClickListener(this);
        mButtonLocalBroadcast.setOnClickListener(this);
        //注册本地广播
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTENT_SLEEP);
        mLocalBroadcastManager.registerReceiver(mLocalBroadcastReceiver, mIntentFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_callBack:
                sleepByCallBack();
                break;
            case R.id.btn_broadcast:
                sleepByBroadcast();
                break;
            case R.id.btnShowImageView:
                showOrHidePicture();
                break;
            default:
                //nothing to do
        }
    }

    private void sleepByCallBack() {
        mTextView.setText(getString(R.string.text));
        if (mHandler.hasMessages(UPDATE_TEST_MSG_BY_LOCALBROADCAST)) {
            mHandler.removeMessages(UPDATE_TEST_MSG_BY_LOCALBROADCAST);
        }


        mTestThread = new TestThread(mTestCallBack, MainActivity.this);
        if (mTestThread != null) {
            mTestThread.start();
        }
    }

    private void sleepByBroadcast() {
        mTextView.setText(getString(R.string.text));
        Intent intent = new Intent(INTENT_SLEEP);
        //发送本地广播
        if (mLocalBroadcastManager != null) {
            mLocalBroadcastManager.sendBroadcast(intent);
        }
    }

    private void showOrHidePicture() {
        mPictureAsyncTask = new PictureAsyncTask();
        //传入下载的url
        mPictureAsyncTask.execute(DOWNLOAD_PICTURE_URL);

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // 当不为空且Running：
        if (mPictureAsyncTask != null && mPictureAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            // cancel方法只是将对应的AsyncTask标记为cancel状态，并不是真正的取消线程的执行。
            mPictureAsyncTask.cancel(true);
        }
    }

    class PictureAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mImageView.getDrawable() == null) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // 第一步：获取传递进来的参数
            // String... params代表可变长数组，也就是说我们可以传递不止一个参数进来，
            // 由于我们只传进来一个参数，所以取对应的url即可。
            String url = params[0];
            System.out.println("getURL" + url);
            Bitmap bitmap = null;
            HttpsURLConnection connection;
            InputStream is = null; // 用于获取数据的输入流。
            try {
                //获取网络连接对象
                connection = (HttpsURLConnection) new URL(url).openConnection();
                connection.connect();   //建立实际链接
                if (connection.getResponseCode() == PICTURE_DOWNLOAD_SUCCESS) {
                    is = connection.getInputStream();      // 获取输入流
                    BufferedInputStream bis = new BufferedInputStream(is);
                    // 第二步：通过decodeStream解析输入流为bitmap对象：
                    bitmap = BitmapFactory.decodeStream(bis);
                    is.close();     // 关闭输入流
                    bis.close();
                } else {
                    //返回空对象
                    return null;
                }
            } catch (MalformedURLException exception) {
                exception.printStackTrace();
            } catch (IOException exception) {
                exception.printStackTrace();
            } finally {
                //nothing to do
            }
            // 将Bitmap作为返回值返回给后面调用的方法(onPostExecute)：
            return bitmap;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // 将进度值传给我们布局中的进度条
            mProgressBar.setProgress(values[0]);
        }

        //第三阶段，拿到结果bitmap图片，更新ui
        /*
         * 在doInBackground方法之后执行onPostExecute方法：
         * 可以在这里操作UI，设置图像，
         * */
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            // 取消进度条显示：
            mProgressBar.setVisibility(View.GONE);
            // 设置图像显示内容：
            if (result != null) {
                mHandler.sendEmptyMessage(PICTURE_DOWNLOAD_SUCCESS);
                mImageView.setImageBitmap(result);
            } else {
                mHandler.sendEmptyMessage(PICTURE_DOWNLOAD_FAILED);
            }
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                switch (msg.what) {
                    case UPDATE_TEST_MSG_BY_CALLBACK:
                        //更新页面
                        mTextView.setText(TEXT_CHANGE_BY_CALLBACK);
                        break;
                    case UPDATE_TEST_MSG_BY_LOCALBROADCAST:
                        //更新页面
                        mTextView.setText(TEXT_CHANGE_BY_BROADCAST);
                        break;
                    case PICTURE_DOWNLOAD_SUCCESS:
                        //下载成功
                        Toast.makeText(MainActivity.this, DOWNLOAD_SUCCESS_TOAST, Toast.LENGTH_LONG).show();
                        break;
                    case PICTURE_DOWNLOAD_FAILED:
                        //下载失败
                        Toast.makeText(MainActivity.this, DOWNLOAD_FAILED_TOAST, Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), HANDLE_MESSAGE_DEFAULT_TOAST_TEXT, Toast.LENGTH_LONG);
                        break;
                }

            } else {
                Toast.makeText(MainActivity.this, GET_MESSAGE_FAILED, Toast.LENGTH_LONG).show();
            }
        }
    };

    TestCallBack mTestCallBack = new TestCallBack() {
        @Override
        public void startSleep() {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(UPDATE_TEST_MSG_BY_CALLBACK);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocalBroadcastManager != null) {
            mLocalBroadcastManager.unregisterReceiver(mLocalBroadcastReceiver);
        }
    }
}
