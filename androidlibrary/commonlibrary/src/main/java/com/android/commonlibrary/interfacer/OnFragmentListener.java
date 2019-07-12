package com.android.commonlibrary.interfacer;

/**
 * Description:Fragmnet与Activity交互回调监听接口
 *             (fragment给activity回传值的回调接口)
 *
 * Author:pei
 * Date: 2019/7/4
 */
public interface OnFragmentListener {

    /**object需要实现Serializable或Parcelable接口**/
    void onFragment(String clsNameDetail, Object object);
}
