package com.android.commonlibrary.util;

/**
 * Title:log打印自定义类
 *
 * Description:
 *   默认自定义打印是关闭状态，
 *   若要打印，需要提前设置开启打印功能：LogUtil.setDebug(true);
 *
 * Created by pei
 * Date: 2019/1/12
 */
public class LogUtil {

    private static final String TAG = "pei";
    private static final String LEVEL_I = "i";
    private static final String LEVEL_D = "d";
    private static final String LEVEL_W = "w";
    private static final String LEVEL_V = "v";
    private static final String LEVEL_E = "e";

    public static boolean LOG = false;//默认关闭自定义打印

    private LogUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**设置是否开启打印的标志**/
    public static void setDebug(boolean isDebug){
        LOG=isDebug;
    }

    public static void i(String tag, String msg) {
        if (LOG) {
            printLog(formatLog(tag), msg, LogUtil.LEVEL_I);
        }
    }

    public static void i(String msg) {
        if (LOG) {
            printLog(formatLog(TAG), msg, LogUtil.LEVEL_I);
        }
    }

    public static void d(String tag, String msg) {
        if (LOG) {
            printLog(formatLog(tag), msg, LogUtil.LEVEL_D);
        }
    }

    public static void d(String msg){
        if (LOG) {
            printLog(formatLog(TAG), msg, LogUtil.LEVEL_D);
        }
    }

    public static void w(String tag, String msg) {
        if (LOG) {
            printLog(formatLog(tag), msg, LogUtil.LEVEL_W);
        }
    }

    public static void w(String msg){
        if (LOG) {
            printLog(formatLog(TAG), msg, LogUtil.LEVEL_W);
        }
    }


    public static void v(String tag, String msg) {
        if (LOG) {
            printLog(formatLog(tag), msg, LogUtil.LEVEL_V);
        }
    }

    public static void v(String msg) {
        if (LOG) {
            printLog(formatLog(TAG), msg, LogUtil.LEVEL_V);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG) {
            printLog(formatLog(tag), msg, LogUtil.LEVEL_E);
        }
    }

    public static void e(String msg) {
        if (LOG) {
            printLog(formatLog(TAG), msg, LogUtil.LEVEL_E);
        }
    }

    private static String formatLog(String tag) {
        StackTraceElement traceElements[] = Thread.currentThread().getStackTrace();
        StackTraceElement element = traceElements[4];
        String className = element.getClassName();
        String methodName = element.getMethodName();
        String fileName = element.getFileName();
        int lineNumber = element.getLineNumber();

        if(className!=null&&className.contains(".")){
            className=className.substring(className.lastIndexOf(".")+1,className.length());
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append(tag + ":");
//        buffer.append(className + ".");
        buffer.append(methodName + "(");
        buffer.append(fileName + ":");
        buffer.append(lineNumber + ")");
        return buffer.toString();
    }

    private static void printLog(String tag, String msg, String type) {
        int count = msg.length();
        if (count > 4000) {
            for (int i = 0; i < count; i += 4000) {
                if (i + 4000 < count) {
                    printByLogType(tag, msg.substring(i, i + 4000), type);
                } else {
                    printByLogType(tag, msg.substring(i, msg.length()), type);
                }
            }
        } else {
            printByLogType(tag, msg, type);
        }
    }

    private static void printByLogType(String tag, String msg, String type) {
        switch (type) {
            case LogUtil.LEVEL_I:
                android.util.Log.i(tag, msg);
                break;
            case LogUtil.LEVEL_D:
                android.util.Log.d(tag, msg);
                break;
            case LogUtil.LEVEL_W:
                android.util.Log.w(tag, msg);
                break;
            case LogUtil.LEVEL_V:
                android.util.Log.v(tag, msg);
                break;
            case LogUtil.LEVEL_E:
                android.util.Log.e(tag, msg);
                break;
            default:
                break;
        }
    }

}
