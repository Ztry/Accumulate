package com.frame.z.accumulate.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frame.z.accumulate.R;
import com.frame.z.accumulate.base.BaseFragment;
import com.orhanobut.logger.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment1 extends BaseFragment {

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_blank;
    }


    @Override
    protected void initView(Bundle arguments) {
        Log.i("llll","BlankFragment1");
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        Log.i("llll","onLazyLoad1");
    }
}
