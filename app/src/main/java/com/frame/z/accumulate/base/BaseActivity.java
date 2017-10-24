package com.frame.z.accumulate.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.frame.z.accumulate.R;
import com.frame.z.accumulate.view.CustomWaitDialog;

/**
 * Created by Administrator on 2017/10/11.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;

    private CustomWaitDialog customWaitDialog;

    private Toolbar toolbar;
    private FrameLayout viewContent;
    private TextView tv_title;

    OnClickListener onClickListenerTopLeft;
    OnClickListener onClickListenerTopRight;
    int menuResId;
    String menuStr;

    public interface OnClickListener {
        void onClick();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_topbar_layout);
        mContext = this;

        toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        viewContent = (FrameLayout) findViewById(R.id.viewContent);
        tv_title = (TextView) findViewById(R.id.tv_title);

        //初始化Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //设置left按钮
        setTopLeftButton(new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        //将继承BaseActicity 的布局解析到 viewContent 里面
        LayoutInflater.from(BaseActivity.this).inflate(setContentView(), viewContent);
        init(savedInstanceState);

    }

    /**
     * 设置布局
     */
    protected abstract int setContentView();

    /**
     * 初始化
     */
    protected abstract void init(Bundle savedInstanceState);


    /**
     * toolbar 设置
     */
    protected void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
        toolbar.setVisibility(View.VISIBLE);
    }

    protected void setTopLeftButton(OnClickListener onClickListener) {
        setTopLeftButton(R.drawable.ic_back, onClickListener);
    }

    protected void setTopLeftButton(int iconResId, OnClickListener onClickListener) {
        toolbar.setNavigationIcon(iconResId);
        this.onClickListenerTopLeft = onClickListener;
    }

    protected void setTopRightButton(String menuStr, OnClickListener onClickListener) {
        this.onClickListenerTopRight = onClickListener;
        this.menuStr = menuStr;
    }

    protected void setTopRightButton(String menuStr, int menuResId, OnClickListener onClickListener) {
        this.menuResId = menuResId;
        this.menuStr = menuStr;
        this.onClickListenerTopRight = onClickListener;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onClickListenerTopLeft.onClick();
        } else if (item.getItemId() == R.id.menu_1) {
            onClickListenerTopRight.onClick();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuResId != 0 || !TextUtils.isEmpty(menuStr)) {
            getMenuInflater().inflate(R.menu.menu_activity_base_top_bar, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menuResId != 0) {
            menu.findItem(R.id.menu_1).setIcon(menuResId);
        }
        if (!TextUtils.isEmpty(menuStr)) {
            menu.findItem(R.id.menu_1).setTitle(menuStr);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    /**
     * 进度加载动画
     */
    public void startLoading() {
        if (customWaitDialog == null) {
            customWaitDialog = new CustomWaitDialog(this);
        }
        if (!isLoading())
            customWaitDialog.show();
    }

    /**
     * 进度加载动画
     */
    public void startLoading(String msg) {
        if (customWaitDialog == null) {
            customWaitDialog = new CustomWaitDialog(this);
        }
        if (!isLoading())
            customWaitDialog.show(msg);
    }

    /**
     * 关闭加载动画
     */
    public void stopLoading() {
        if (customWaitDialog != null) {
            customWaitDialog.dismiss();
            customWaitDialog = null;
        }
    }

    public boolean isLoading() {
        return customWaitDialog.isShowing();
    }
}
