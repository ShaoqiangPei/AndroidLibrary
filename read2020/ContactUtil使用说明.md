## ContactUtil使用说明

### 概述
`ContactUtil`是一个获取`手机短信`,`联系人`以及`发送短信`的工具类。其作用是便于快捷的实现`获取手机短信信息`，`获取手机联系人信息`以及`发送短信`功能。

### 说明
#### 一. ContactUtil 类方法简介
`ContactUtil`主要方法如下：
```
    /***
     * 获取手机短信
     *
     * 需要在 androidManifast.xml中添加以下权限
     * <uses-permission android:name="android.permission.READ_SMS"/>
     * android 6.0+还需要在代码中手动添加以上权限。
     *
     * @param context
     * @return
     */
    public static List<SmsData> getSMSDataList(Context context)

    /**
     * 获取手机联系人
     *
     * 需要在 androidManifast.xml中添加以下权限
     * <uses-permission android:name="android.permission.READ_CONTACTS"/>
     * android 6.0+还需要在代码中手动添加以上权限。
     *
     *
     * 通过ContactsContract.Contacts.CONTENT_URI来获取_ID和DISPLAY_NAME
     * _ID 该联系人的索引
     * 通过这个ID可以在ContactsContract.CommonDataKinds.Phone.CONTENT_URI 中找到该联系人的电话号码
     * 通过这个ID可以在ContactsContract.CommonDataKinds.Email.CONTENT_URI 找到该联系人的邮箱
     * DISPLAY_NAME 是该联系人的姓名
     */
    public static List<ContactData> getContactDataList(Context context)

    /****
     * 发送短信
     *
     * 需要在 androidManifast.xml中添加以下权限
     * <uses-permission android:name="android.permission.SEND_SMS"/>
     * android 6.0+还需要在代码中手动添加以上权限。
     *
     * @param phoneNumber：发送短信时对方电话号码
     * @param message：要发送的信息
     * @param sendSmsAction: 发送短信时需要处理发送事件的广播action,要设置成唯一标识字符串,如：com.test.kk.send
     * @param receiveSmsAction: 接收短信时需要处理接收事件的广播action,要设置成唯一标识字符串,如：com.test.kk.receive
     * @param context:上下文
     */
    public static void sendSmsMessage(String phoneNumber,String message,
                                      String sendSmsAction,
                                      String receiveSmsAction,
                                      Context context)
```
#### 二. 设置运行权限
读取手机短信，联系人信息，以及发送短信据需要设置手机权限。你需要在`Androidmanifast.xml`中添加以下权限:
```
    <uses-permission android:name="android.permission.READ_SMS" />
    <uses-permission android:name="android.permission.READ_CONTACTS"/>
    <uses-permission android:name="android.permission.SEND_SMS"/>
```
当然，你还需要添加`Android 6.0+`手动权限和`FileProvider`文件权限，具体可参考文章
[PermissionsDispatcher动态权限申请kotlin版](https://www.jianshu.com/p/c3da2f4aff34)
接着需要添加`FileProvider`相关处理，大家可参考以下文章
[SpUtil多样加密存储，兼容android9.0](https://www.jianshu.com/p/bbf057ccbcff)
#### 三.ContactUtil 在 Activity 中使用样例
下面给出`ContactUtil`在`TempActivity`中使用代码：
```
public class TempActivity extends AppCompatActivity{

    private TextView mTvTest;
    private Button mBtnTest;
    private Button mBtnTest2;
    private Button mBtnTest3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        //初始化控件
        initView();
        //初始化数据
        initData();
        //控件监听
        setListener();
    }

    /**初始化控件**/
    private void initView(){
        mTvTest=findViewById(R.id.mTvTest);
        mBtnTest=findViewById(R.id.mBtnTest);
        mBtnTest2=findViewById(R.id.mBtnTest2);
        mBtnTest3=findViewById(R.id.mBtnTest3);
    }

    private void initData() {
        //设置mTvTest垂直滚动
        mTvTest.setMovementMethod(ScrollingMovementMethod.getInstance());//主要是这句话在起作用
    }

    /**控件监听**/
    private void setListener() {
        //获取短信信息
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询短信
                List<SmsData> list = ContactUtil.getSMSDataList(TempActivity.this);
                StringBuffer buffer=new StringBuffer();
                for (SmsData data : list) {
                    String message= "address="+data.getAddress()
                            +" person="+data.getPerson()
                            +" body="+data.getBody()
                            +" date="+data.getDate()
                            +" type="+data.getType();
                    buffer.append(message+"\n");
                }
                LogUtil.i(buffer.toString());
                mTvTest.setText(buffer.toString());
            }
        });

        //获取联系人信息
        mBtnTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询联系人
                List<ContactData> clist = ContactUtil.getContactDataList(TempActivity.this);

                StringBuffer buffer=new StringBuffer();
                for (ContactData cd : clist) {
                    String message="姓名: " + cd.getName() + "  手机号: " + cd.getNumber() + "  邮箱: " + cd.getEmail()+"\n";
                    buffer.append(message);
                }
                LogUtil.i(buffer.toString());
                mTvTest.setText(buffer.toString());
            }
        });

        //发送短信
        mBtnTest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发起短信
                ContactUtil.sendSmsMessage("18372951357", "我是测试信息","com.test.kk.send","com.test.kk.receive", TempActivity.this);
            }
        });

    }

}
```
