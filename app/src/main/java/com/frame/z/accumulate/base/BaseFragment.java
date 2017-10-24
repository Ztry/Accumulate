package com.frame.z.accumulate.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * fragment基类
 * Created by Administrator on 2017/10/23.
 */

public abstract class BaseFragment extends Fragment {

    /**
     * 贴附的activity
     */
    protected FragmentActivity mActivity;

    /**
     * 根view
     */
    protected View mRootView;

    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;

    /**
     * 是否加载完成
     * 当执行完oncreatview,View的初始化方法后方法后即为true
     */
    protected boolean mIsPrepare;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(setLayoutResourceId(), container, false);

        initView(getArguments());

        mIsPrepare = true;

        return mRootView;

    }

    /**
     * 设置根布局资源id
     */
    protected abstract int setLayoutResourceId();


    /**
     * 初始化View
     * @param arguments
     */
    protected void initView(Bundle arguments) {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.mIsVisible = isVisibleToUser;

        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }

    /**
     * 用户可见时执行的操作*/

    protected void onVisibleToUser() {
        if (mIsPrepare && mIsVisible) {
            onLazyLoad();
//            mIsPrepare = false;
//            mIsVisible = false;
        }
    }

    /**
     * 懒加载，仅当用户可见切view初始化结束后才会执行
     */
    protected void onLazyLoad() {

    }
}
