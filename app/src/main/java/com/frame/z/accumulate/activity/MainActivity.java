package com.frame.z.accumulate.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.frame.z.accumulate.R;
import com.frame.z.accumulate.view.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.ll_bg)
    LoadingLayout llBg;

    @BindView(R.id.iv_show)
    PhotoView iv_show;
    @BindView(R.id.parent)
    FrameLayout parent;
    @BindView(R.id.bg)
    ImageView bg;
    @BindView(R.id.pv_img)
    PhotoView pv_img;

    Info mRectF;

    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        llBg.setStatus(LoadingLayout.Success);
        llBg.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {

            }
        });

        iv_show.disenable();
        iv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.setVisibility(View.VISIBLE);

                pv_img.setImageResource(R.mipmap.ic_launcher);
                //获取img1的信息
                mRectF = iv_show.getInfo();
                //让img2从img1的位置变换到他本身的位置
                pv_img.animaFrom(mRectF);
            }
        });

        // 需要启动缩放需要手动开启
        pv_img.enable();
        pv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 让img2从自身位置变换到原来img1图片的位置大小
                pv_img.animaTo(mRectF, new Runnable() {
                    @Override
                    public void run() {
                        parent.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (parent.getVisibility() == View.VISIBLE) {
            pv_img.animaTo(mRectF, new Runnable() {
                @Override
                public void run() {
                    parent.setVisibility(View.GONE);
                }
            });
        } else {
            super.onBackPressed();
        }
    }
}
