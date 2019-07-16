package com.android.commonlibrary.interfacer;

import android.view.KeyEvent;

/**
 * Description:Activity与Fragment交互时,对于返回键处理的监听
 *
 * Author:pei
 * Date: 2019/7/4
 */
public interface OnFragmentBackListener {

    /**返回键的处理**/
    void onBackForward(int keyCode, KeyEvent event);
}
