package com.android.commonlibrary.service.accessibility_service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.annotation.RequiresApi;
import com.android.commonlibrary.util.CollectionUtil;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.StringUtil;
import java.util.List;

/**
 * Title: 无障碍服务帮助类
 * description:
 * autor:pei
 * created on 2021/4/16
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class AccessibilityHelper {

    private AccessibilityHelper(){}

    private static class Holder {
        private static AccessibilityHelper instance = new AccessibilityHelper();
    }

    public static AccessibilityHelper getInstance() {
        return Holder.instance;
    }


    /**获取根AccessibilityNodeInfo**/
    private AccessibilityNodeInfo getRootInfo(AccessibilityService service){
        if(service!=null){
            //拿到根节点
            AccessibilityNodeInfo rootInfo = service.getRootInActiveWindow();
            if(rootInfo!=null){
                //开始找目标节点，这里拎出来细讲，直接往下看正文
                if(rootInfo!=null&&rootInfo.getChildCount() > 0){
                    if(!TextUtils.isEmpty(rootInfo.getClassName())){
                        return rootInfo;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 有些节点不可点击 点击交给父级甚至父级的父级...来做的
     *
     * @param info
     * @return
     */
    private AccessibilityNodeInfo getClickable(AccessibilityNodeInfo info) {
        if(info!=null) {
            LogUtil.i(info.getClassName() + ": " + info.isClickable());
            if (info.isClickable()) {
                return info;//如果可以点击就返回
            } else {//不可点击就检查父级 一直递归
                return getClickable(info.getParent());
            }
        }
        return null;
    }

    /***
     * 根据控件内容找到控件的 AccessibilityNodeInfo
     *
     * 界面中可能出现多个控件显示同样的内容,则根据text获取的控件不止一个
     * 这时,则需要控件id做辅助筛选,当无viewId做筛选条件时,默认取找到第一个含内容的view返回
     *
     * @param service AccessibilityService对象
     * @param text 视图文字
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     *               当 viewId为null时,默认取找到第一个含内容的view作为查找的返回结果
     *
     * @return
     */
    private AccessibilityNodeInfo findByText(AccessibilityService service, String text, String viewId) {
        //检测数据合法性
        if(service==null|| StringUtil.isEmpty(text)){
            LogUtil.e("======service或text为null===");
            return null;
        }
        AccessibilityNodeInfo rootInfo = getRootInfo(service);
        if(rootInfo==null){
            LogUtil.e("======根节点rootInfo为null===");
            return null;
        }
        List<AccessibilityNodeInfo> list=rootInfo.findAccessibilityNodeInfosByText(text);
        if (CollectionUtil.isNotEmpty(list)) {
            for (AccessibilityNodeInfo info : list) {
                if(info!=null) {
                    CharSequence charSequence = info.getText();
                    if (charSequence != null) {
                        String msg = charSequence.toString();
                        //先判断控件中内容一致
                        if (msg.equals(text)) {
                            if(StringUtil.isNotEmpty(viewId)){
                                //含viewId判断时,还要比对viewId是否一致
                                String findViewId=info.getViewIdResourceName();
                                if(viewId.equals(findViewId)){
                                    return info;
                                }
                            }else{
                                //不含viewId辅助判断时,直接返回
                                return info;
                            }
                        }
                    } else {
                        info.recycle();
                    }
                }
            }
        }
        return null;
    }

    /***
     * 根据控件id找到控件的 AccessibilityNodeInfo
     *
     * 当界面中是一个列表的时候,根据viewId查找可能会得到一个控件列表
     * 而所啊哟寻找的不一定是默认的第一项,这时则需要文字即text辅助查找
     *
     * @param service AccessibilityService对象
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     * @param text 控件上显示的内容
     *
     */
    private AccessibilityNodeInfo findByID(AccessibilityService service, String viewId, String text) {
        //检测数据合法性
        if(service==null|| StringUtil.isEmpty(viewId)){
            LogUtil.e("======service或text为null===");
            return null;
        }
        AccessibilityNodeInfo rootInfo = getRootInfo(service);
        if(rootInfo==null){
            LogUtil.e("======根节点rootInfo为null===");
            return null;
        }
        List<AccessibilityNodeInfo> list=rootInfo.findAccessibilityNodeInfosByViewId(viewId);
        if (CollectionUtil.isNotEmpty(list)) {
            if(StringUtil.isNotEmpty(text)){
                //有文字内容时,根据文字内容去检索控件
                for (AccessibilityNodeInfo info : list) {
                    if(info!=null) {
                        CharSequence charSequence = info.getText();
                        if (charSequence != null) {
                            String msg = charSequence.toString();
                            if (msg.equals(text)) {
                                return info;
                            }
                        } else {
                            info.recycle();
                        }
                    }
                }
            }else {
                //无文字内容加持的时候,默认取下标为0的,然后剩下的遍历回收掉
                for (int i = list.size() - 1; i > -1; i--) {
                    AccessibilityNodeInfo info = list.get(i);
                    if (info != null) {
                        if (i > 0) {
                            //回收掉
                            info.recycle();
                        } else {
                            //返回index=0的item
                            return info;
                        }
                    }
                }
            }
        }
        return null;
    }

    /***
     * 改变editText的内容
     *
     * @param info
     * @param message
     */
    private boolean changeInput(AccessibilityNodeInfo info, String message) {//改变editText的内容
        if(StringUtil.isNotEmpty(message)) {
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    message);
            return info.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        }else{
            LogUtil.e("=======editText中设置的message不能为空========");
        }
        return false;
    }




    /**点击动作**/
    public boolean performClick(AccessibilityNodeInfo targetInfo) {
        if(targetInfo!=null){
            LogUtil.i("=====点击对象不为空====");
            return targetInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }else{
            LogUtil.i("=====点击对象为空====");
        }
        return false;
    }

    /**点击返回键**/
    public boolean clickBackKey(AccessibilityService service) {
        if(service!=null){
            return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        }
        return false;
    }

    /**点击Home键**/
    public boolean clickHomeKey(AccessibilityService service) {
        if(service!=null){
            return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
        }
        return false;
    }

    /**点击最近任务**/
    public boolean clickLastTaskKey(AccessibilityService service) {
        if(service!=null){
            return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
        }
        return false;
    }

    /**点击通知栏**/
    public boolean clickNotificationKey(AccessibilityService service) {
        if(service!=null){
            return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
        }
        return false;
    }


    /***
     * 根据控件显示内容text找到的控件是否存在
     *
     * 界面中可能出现多个控件显示同样的内容,则根据text获取的控件不止一个
     * 这时,则需要控件id做辅助筛选,当无viewId做筛选条件时,默认取找到第一个含内容的view返回
     *
     * @param service AccessibilityService对象
     * @param text 视图文字
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     *               当 viewId为null时,默认取找到第一个含内容的view作为查找的返回结果
     *
     * @return  true:该控件存在    false:该控件不存在
     */
    public boolean isExistViewByText(AccessibilityService service, String text, String viewId) {
        AccessibilityNodeInfo info = findByText(service, text,viewId);
        if (info != null) {
            LogUtil.i("====找到的viewId为: viewIdResource=" + info.getViewIdResourceName() + "  viewText=" + text);
            return true;
        }
        return false;
    }


    /***
     * 根据控件ViewId找到的控件是否存在
     *
     * 当界面中是一个列表的时候,根据viewId查找可能会得到一个控件列表
     * 而所要寻找的不一定是默认的第一项,这时则需要文字即text辅助查找
     *
     * @param service AccessibilityService对象
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     * @param text 控件上显示的内容,当text为null时,默认取根据id获取到的列表的第一个
     *
     * @return  true:该控件存在    false:该控件不存在
     */
    public boolean isExistViewById(AccessibilityService service, String viewId, String text) {
        AccessibilityNodeInfo info = findByID(service, viewId, text);
        if (info != null) {
            LogUtil.i("====找到的viewId为: viewIdResource=" + info.getViewIdResourceName() + "  viewText=" + text);
            return true;
        }
        return false;
    }

    /***
     * 根据 EditText中的内容找到 EditText 对象,并改变EditText中的内容
     *
     * @param service AccessibilityService对象
     * @param viewText EditText原来显示的内容
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     *               当 viewId为null时,默认取找到第一个含内容的view作为查找的返回结果
     * @param message  EditText中要设置的内容
     * @return
     */
    public boolean changeInputByViewText(AccessibilityService service, String viewText, String viewId, String message){
        //控件是否存在
        if(isExistViewByText(service,viewText,viewId)) {
            AccessibilityNodeInfo info = findByText(service, viewText, viewId);
            return changeInput(info,message);
        }
        return false;
    }

    /***
     * 根据 EditText中的ViewId找到 EditText 对象,并改变EditText中的内容
     *
     * @param service  AccessibilityService对象
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     * @param viewText 控件上显示的内容,当text为null时,默认取根据id获取到的列表的第一个
     * @param message EditText中要设置的内容
     * @return
     */
    public boolean changeInputByViewId(AccessibilityService service, String viewId, String viewText, String message){
        //控件是否存在
        if(isExistViewById(service,viewId,viewText)) {
            AccessibilityNodeInfo info = findByID(service,viewId,viewText);
            return changeInput(info,message);
        }
        return false;
    }


    /***
     * 根据控件上显示的文字找到该控件,并执行点击事件
     *
     * 注：若该控件不可点击,则找其父控件甚至父级的父级...来执行点击
     *
     * 当界面中是一个列表的时候,根据viewId查找可能会得到一个控件列表
     * 而所啊哟寻找的不一定是默认的第一项,这时则需要文字即text辅助查找
     *
     * @param service AccessibilityService对象
     * @param text 控件上显示的内容
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     *               当 viewId为null时,默认取找到第一个含内容的view作为查找的返回结果
     * @return
     */
    public boolean performClickByText(AccessibilityService service, String text, String viewId){
        //控件是否存在
        if(isExistViewByText(service,text,viewId)){
            AccessibilityNodeInfo info = findByText(service,text,viewId);
            //层级递进,直到找到可点击的父控件
            AccessibilityNodeInfo perInfo=getClickable(info);
            return performClick(perInfo);
        }
        return false;
    }

    /***
     * 根据控件ViewId找到该控件,并执行点击事件
     *
     * 注：若该控件不可点击,则找其父控件甚至父级的父级...来执行点击
     *
     * 当界面中是一个列表的时候,根据viewId查找可能会得到一个控件列表
     * 而所啊哟寻找的不一定是默认的第一项,这时则需要文字即text辅助查找
     *
     * @param service AccessibilityService对象
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     * @param text 控件上显示的内容,当text为null时,默认取根据id获取到的列表的第一个
     *
     * @return
     */
    public boolean performClickById(AccessibilityService service, String viewId, String text){
        //控件是否存在
        if (isExistViewById(service,viewId,text)){
            AccessibilityNodeInfo info = findByID(service, viewId, text);
            //层级递进,直到找到可点击的父控件
            AccessibilityNodeInfo perInfo=getClickable(info);
            return performClick(perInfo);
        }
        return false;
    }


    /***
     * 手势滑动
     *
     * 注: 开始滑动时间与滑动延长时间参考值如下：
     *     startTime=100L,
     *     duration=500L
     *
     * @param service  AccessibilityService对象
     * @param startX 起始 x 坐标
     * @param startY 起始 Y 坐标
     * @param endX  结束 X 坐标
     * @param endY  结束 Y 坐标
     * @param startTime  滑动开始时间(单位毫秒)
     * @param duration  滑动持续时间(单位毫秒)
     * @param callback 监听
     * @return
     */
    public boolean performGestureSliding(AccessibilityService service,
                                         float startX,
                                         float startY,
                                         float endX,
                                         float endY,
                                         long startTime,
                                         long duration,
                                         AccessibilityService.GestureResultCallback callback) {//仿滑动
        if (service!=null&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if(startTime<0){
                LogUtil.i("=======滑动开始时间不能小于零=========");
                return false;
            }
            if(duration<0||duration==0||duration>1000*59){
                LogUtil.i("=======滑动持续时间需要在 （0,59000]毫秒之间=========");
                return false;
            }

            Path path = new Path();
            path.moveTo(startX, startY);//滑动起点
            path.lineTo(endX, endY);//滑动终点
            GestureDescription.Builder builder = new GestureDescription.Builder();
            //100L 第一个是开始的时间,第二个是持续时间(持续时间最好在500毫秒到59秒之间,太大或太小都不行)
            GestureDescription.StrokeDescription strokeDescription=new GestureDescription.StrokeDescription(path, 100L, 500L);
            GestureDescription description = builder.addStroke(strokeDescription).build();
            if(callback==null){
                callback=new DefaultGestureResultCallback();
            }
            return service.dispatchGesture(description, callback, null);
        }
        return false;
    }

    /***
     * 手势执行点击事件
     *
     * 注: 开始点击时间与点击延长时间参考值如下：
     *     startTime=50L,
     *     duration=500L
     *
     * @param service  AccessibilityService对象
     * @param x 点击屏幕的 x 坐标
     * @param y 点击屏幕的 y 坐标
     * @param startTime  滑动开始时间(单位毫秒)
     * @param duration  滑动持续时间(单位毫秒)
     * @param callback 监听
     * @return
     */
    public boolean performClickByGesture(AccessibilityService service,
                                         float x, float y,
                                         long startTime, long duration,
                                         AccessibilityService.GestureResultCallback callback){
        if(service!=null){
            if(startTime<0){
                LogUtil.i("=======滑动开始时间不能小于零=========");
                return false;
            }
            if(duration<0||duration==0||duration>1000*59){
                LogUtil.i("=======滑动持续时间需要在 （0,59000]毫秒之间=========");
                return false;
            }

            //线性的path代表手势路径,点代表按下,封闭的没用
            Path path=new Path();
            path.moveTo(x, y);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            //100L 第一个是开始的时间,第二个是持续时间(持续时间最好在500毫秒到59秒之间,太大或太小都不行)
            GestureDescription.StrokeDescription strokeDescription=new GestureDescription.StrokeDescription(path, startTime, duration);
            GestureDescription description = builder.addStroke(strokeDescription).build();
            if(callback==null){
                callback=new DefaultGestureResultCallback();
            }
            return service.dispatchGesture(description, callback, null);
        }
        return false;
    }

    /***
     * 线程休眠时间
     *
     * @param miao:double类型, 单位秒
     */
    public void waitTime(double miao) {
        long time = (long) (miao * 1000);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class DefaultGestureResultCallback extends AccessibilityService.GestureResultCallback {

        @Override
        public void onCompleted(GestureDescription gestureDescription) {
            super.onCompleted(gestureDescription);
            LogUtil.i("=====DefaultGestureResultCallback===onCompleted====");
        }

        @Override
        public void onCancelled(GestureDescription gestureDescription) {
            super.onCancelled(gestureDescription);
            LogUtil.i("=====DefaultGestureResultCallback===onCancelled====");
        }
    }

}
