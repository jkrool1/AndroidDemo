package com.ts.hc;

import android.content.Context;
import android.util.Log;

public class TestThread extends Thread {
    private TestCallBack mTestCallBack;
    private static final  int THREAD_SLEEP_TIME = 10000;

    public TestThread(TestCallBack testCallBack, Context context) {
        this.mTestCallBack = testCallBack;
    }

    @Override
    public void run() {
        //sleep and start the callback!
        try {
            if (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(THREAD_SLEEP_TIME);
                if (mTestCallBack != null) {
                    mTestCallBack.startSleep();

                }
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

    }

}
