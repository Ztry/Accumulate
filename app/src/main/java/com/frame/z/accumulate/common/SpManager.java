package com.frame.z.accumulate.common;

import android.content.Context;
import android.os.Environment;

import com.frame.z.accumulate.util.SpUtil;

/**
 * sharedpreferences管理类
 * Created by Administrator on 2017/9/22.
 */

public class SpManager {

    /**
     * 配置文件
     */
    private static SpManager sInstance;
    private static SpUtil sSharedPreferences;

    /**
     * 单例模式
     */
    private final static String APP_CONFIG = "APP_CONFIG";

    public static SpManager getInstance(Context context) {
        if (null == sInstance) {
            sInstance = new SpManager();
            sSharedPreferences = new SpUtil(context, APP_CONFIG, Context.MODE_PRIVATE);
        }
        return sInstance;
    }


    /**
     * 标记是否第一次登录
     */
    public final static String APP_FIRST_START = "app_first_start";

    public void setFirstStart(boolean firstStart) {
        sSharedPreferences.put(APP_FIRST_START, firstStart);
    }

    public boolean getFirstStart() {
        return (boolean) sSharedPreferences.get(APP_FIRST_START, true);
    }

    public void removeFirstStart() {
        sSharedPreferences.remove(APP_FIRST_START);
    }


}
