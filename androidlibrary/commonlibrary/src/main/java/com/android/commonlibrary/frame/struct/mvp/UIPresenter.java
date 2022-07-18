package com.android.commonlibrary.mvp_frame;

import android.content.Context;
import com.android.commonlibrary.entity.UIData;
import com.android.commonlibrary.interfacer.frame.struct.mvp.PreView;

/**
 * Description:更新主线程ui帮助类
 *
 * Author:pei
 * Date: 2019/4/12
 */
public class UIPresenter {

    /**更新ui的方法**/
    public  <T> void updateUI(Context context, PreView view, UIData<T> data, OnUIListener listener){
        ((AppActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Object obj=data.getData();
//                String className=cls.getName();
//                if((obj instanceof List)&&CheckTypeModel.ResultListBean.class.getName().equals(className)){
//                    view.checkTypeSuccess((List<CheckTypeModel.ResultListBean>)obj);
//                }
                listener.update(view,data);
            }
        });
    }

    public interface OnUIListener{
        <T>void update(PreView view, UIData<T> data);
    }

}
