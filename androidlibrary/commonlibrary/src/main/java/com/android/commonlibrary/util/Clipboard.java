package com.android.commonlibrary.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.text.method.ArrowKeyMovementMethod;
import android.widget.TextView;

/**
 * Title:剪切板帮助类
 * description:
 * autor:pei
 * created on 2019/11/14
 */
public class Clipboard{

    private ClipboardManager mClipboardManager;
    private ClipboardManager.OnPrimaryClipChangedListener mOnPrimaryClipChangedListener;

    /**
     * 注册剪切板复制、剪切事件监听
     *
     * @param context
     * @param listener
     */
    public void registerClipEvents(Context context,OnClipboardListener listener) {
        if(mClipboardManager==null){
            mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        }
        mOnPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                if (mClipboardManager.hasPrimaryClip() && mClipboardManager.getPrimaryClip().getItemCount() > 0) {
                    // 获取复制、剪切的文本内容
                    CharSequence content = mClipboardManager.getPrimaryClip().getItemAt(0).getText();
                    if (listener != null&&content!=null) {
                        listener.clipboard(content.toString());
                    }
                }
            }
        };
        mClipboardManager.addPrimaryClipChangedListener(mOnPrimaryClipChangedListener);
    }

    /**注销剪切板事件**/
    public void unRegisterClipEvents(){
        if (mClipboardManager != null && mOnPrimaryClipChangedListener != null) {
            mClipboardManager.removePrimaryClipChangedListener(mOnPrimaryClipChangedListener);
        }
    }

    /**
     * 复制口令,调用此方法，将value复制到剪切板上
     * 使用场景：点击按钮，调用此方法，将value设置到剪切板中。
     *
     * @param context
     * @param value
     */
    public void setClipboardContent(Context context,String value){
        if(mClipboardManager==null){
            mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        }
        if (StringUtil.isNotEmpty(value)) {
            mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, value));//参数一：标签，可为空，参数二：要复制到剪贴板的文本
        }
    }

    /**
     * 获取剪切板内容
     * 适用场景：从别的app复制到本app时获取复制内容
     *         如你复制了微信中的聊天内容，在打开你app的时候，要获取到你复制的内容，就可使用本方法
     *         一般你app中要判断app是否在前台，或者将此方法放到你需要获取的界面的onResume()周期中
     *         便于一打开此界面就能获取内容。
     *
     * 注：需要判断app回到前台
     */
    public void getClipboardContent(Context context,OnClipboardListener listener){
        //注：需要判断app回到前台
        // 获取剪贴板数据
        String content = null;
        if(mClipboardManager==null){
            mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        }
        try {
            ClipData data = mClipboardManager.getPrimaryClip();
            ClipData.Item item = data.getItemAt(0);
            content = item.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (content != null) {
            // 执行我们的操作
            if(listener!=null){
                listener.clipboard(content);
            }
            // 清除剪贴板
            ClipData clip = ClipData.newPlainText("", "");
            mClipboardManager.setPrimaryClip(clip);
        }
    }

    /**设置textView可被复制,剪切和粘贴**/
    public void canBePasted(TextView textView){
        if(textView==null){
            return;
        }
        //设置mTv中的内容可复制
        int sdkLevel = Build.VERSION.SDK_INT;
        if (sdkLevel >= 11) {
            textView.setTextIsSelectable(true);
        } else {
            textView.setFocusableInTouchMode(true);
            textView.setFocusable(true);
            textView.setClickable(true);
            textView.setLongClickable(true);
            textView.setMovementMethod(ArrowKeyMovementMethod.getInstance());
            textView.setText(textView.getText(), TextView.BufferType.SPANNABLE);
        }
    }

    public interface OnClipboardListener{
        void clipboard(String content);
    }

}

