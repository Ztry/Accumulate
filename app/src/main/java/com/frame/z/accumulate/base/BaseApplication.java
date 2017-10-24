package com.frame.z.accumulate.base;

import android.app.Application;

import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * Created by Administrator on 2017/9/22.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.addLogAdapter(new DiskLogAdapter());
    }
}
