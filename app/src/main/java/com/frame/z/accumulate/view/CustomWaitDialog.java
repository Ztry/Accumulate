package com.frame.z.accumulate.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.frame.z.accumulate.R;


/**
 * 弹出框
 * Created by Administrator on 2017/3/20.
 */

public class CustomWaitDialog {
    private Context mContext;
    private ImageView image;
    private Dialog waitDialog;
    private AnimationDrawable mAnimation;
//    private TextView msg;
    private boolean isShowing;

    public CustomWaitDialog(Context mContext) {
        this.mContext = mContext;
        waitDialog = new Dialog(mContext, R.style.dialog);
        waitDialog.setContentView(R.layout.loading);
        waitDialog.setCanceledOnTouchOutside(false);

        /**
         * 设置幕布，也就是本dialog的背景层 dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的
         * ，1.0f时候，背景全部变黑暗。
         * 如果要达到背景全部变暗的效果，需要设置
         * dialog.getWindow().addFlags(WindowManager.LayoutParams
         * .FLAG_DIM_BEHIND); ，否则，背景无效果。
         */
        Window window = waitDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        lp.dimAmount = 0.5f;
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

//        waitDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

//        msg = (TextView)waitDialog.findViewById(R.id.message);

        image = (ImageView) waitDialog.findViewById(R.id.spinnerImageView);
        mAnimation = (AnimationDrawable) image.getDrawable();
        mAnimation.start();
//        mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.loading);
    }

    public void show(String msg) {
//        if("".equals(msg)){
//            this.msg.setText("正在加载...");
//        }else{
//            this.msg.setText(msg);
//        }

        startDialog();
    }
    public boolean isShowing(){
        return isShowing;
    }

    public void show() {
//        this.msg.setText(R.string.dialog_msg);
        startDialog();
    }

    private void startDialog(){
        isShowing = true;
//        image.startAnimation(mAnimation);
        waitDialog.show();
    }

    public void dismiss() {
        isShowing = false;
        waitDialog.dismiss();
    }

    //用于网络请求中断操作
    public void setOnDismissListener(
            DialogInterface.OnDismissListener dismissListener) {
        waitDialog.setOnDismissListener(dismissListener);
    }
}
