package com.android.commonlibrary.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.commonlibrary.R;
import com.android.commonlibrary.app.LibraryConfig;

/**
 * Created by codeest on 2016/8/4.
 */
public class ToastUtil {

    private static ToastUtil TD;

    private Context mContext;
    private Toast mToast;
    private String msg;

    public ToastUtil(Context context) {
//        this.mContext = context;
        this.mContext = LibraryConfig.getInstance().getApplication();
    }

    public static void show(int resId){
        show(LibraryConfig.getInstance().getApplication().getString(resId));
    }

    public static void show(String msg) {
        if (TD == null) {
            TD = new ToastUtil(LibraryConfig.getInstance().getApplication());
        }
        TD.setText(msg);
        TD.create().show();
    }

    public static void shortShow(String msg) {
        if (TD == null) {
            TD = new ToastUtil(LibraryConfig.getInstance().getApplication());
        }
        TD.setText(msg);
        TD.createShort().show();
    }

    private Toast create() {
        View contentView = View.inflate(mContext, R.layout.dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        mToast = new Toast(mContext);
        mToast.setView(contentView);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_LONG);
        tvMsg.setText(msg);
        return mToast;
    }

    private Toast createShort() {
        View contentView = View.inflate(mContext, R.layout.dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        mToast = new Toast(mContext);
        mToast.setView(contentView);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        tvMsg.setText(msg);
        return mToast;
    }

    private void show() {
        if (mToast != null){
            mToast.show();
        }
    }

    public void setText(String text) {
        msg = text;
    }
}
