## AgreementDefaultHelper使用说明

### 概述
`AgreementDefaultHelper`是一个用于建立一个简单的`用户协议`功能的帮助类。它可以帮助开发者快速创建一个具有固定模板内容的`用户协议`功能。需要注意的是，此类只能建立一个固定模板内容的`用户协议`和`隐私协议`,适用于那些需要`用户协议`功能，但对`用户协议`内容没有特定要求的场景。

### 使用
#### 一.启动页弹框
一般会在`app`首次启动的时候，弹出一个`用户协议`的`Dialog`,那么我们可以在启动页需要用到的地方，调用以下代码：
```
    /**用户协议弹框**/
    private void showAgreementDialog() {
        AgreementDefaultHelper.showDefaultAgreementDialog(this,
                AppUtil.getAppName(),
                R.color.blue,
                null,
                R.color.blue,
                R.color.color_969696,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击《用户协议》,跳转用户协议界面
                        startParameterAct(AgreementActivity.class, AgreementActivity.AGREEMENT_TAG, AgreementActivity.USER_TYPE);
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击《隐私协议》,跳转隐私协议界面
                        startParameterAct(AgreementActivity.class, AgreementActivity.AGREEMENT_TAG, AgreementActivity.PRIVACY_TYPE);
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消按钮监听,退出app
                        LogUtil.i("======退出app======");
                        finish();
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //存储用户协议标志
                        //......
                        //确定按钮的操作,进入app的流程
                        LogUtil.i("======进入app======");
                    }
                });
    }
```
#### 二.用户协议/隐私协议 界面显示详细协议内容
这里一般我们是将`用户协议`和`隐私协议`显示在同一个`activity`(假设此界面为`AgreementActivity`)中，则`AgreementActivity`中接收并显示内容代码如下：
```
public class AgreementActivity extends AppActivity {

    private TextView mTvContent;

    public static final String AGREEMENT_TAG = "agreement_tag"; //跳转tag
    public static final String USER_TYPE = "用户服务协议"; //用户协议
    public static final String PRIVACY_TYPE = "隐私权政策"; //隐私协议

    @Override
    public int getContentViewId() {
        return R.layout.activity_agreement;
    }

    @Override
    public void initData() {
        mTvContent=findViewById(R.id.mTvContent);

        //获取上一界面数据
        String tag= getStringParameter(AGREEMENT_TAG);
        String appName= AppUtil.getAppName();
        String content=null;
        switch (tag) {
            case USER_TYPE:
                content=AgreementDefaultHelper.getUserContent(appName);
                break;
            case PRIVACY_TYPE:
                content=AgreementDefaultHelper.getUserContent(appName);
                break;
            default:
                break;
        }
        mTvContent.setText(content);

    }

    @Override
    public void setListener() {

    }

}
```
这里需要注意的是,在`AgreementActivity`界面的协议内容，我们是通过一个可上下滑动的`TexyView`来显示的，则在布局中`TexyView`需要用`NestedScrollView`包裹使用
类似下面这样：
```
    <androidx.core.widget.NestedScrollView
        android:id="@+id/nsv"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent">
        <TextView
            android:id="@+id/mTvContent"
            style="@style/tv_text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:paddingStart="@dimen/dp_10"
            android:paddingEnd="@dimen/dp_10"
            android:textColor="@color/black"
            android:textSize="@dimen/sp_16"/>
    </androidx.core.widget.NestedScrollView>
```
