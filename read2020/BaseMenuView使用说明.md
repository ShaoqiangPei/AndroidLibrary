## BaseMenuView使用说明

### 概述
`BaseMenuView`作为一个侧滑菜单栏基类，继承它可以方便开发者迅速实现一个自己的侧滑菜单栏。该类实现基于`androidx.drawerlayout:drawerlayout:1.1.0-alpha03`库，**但你并不需要在你的
项目中引用**   
```
implementation "androidx.drawerlayout:drawerlayout:1.1.0-alpha03"
```
**依赖，你要做的仅仅是继承`BaseMenuView`写自己的侧滑菜单栏，并按此说明编写代码即可。**

### 使用说明
#### 一. 继承`BaseMenuView`写一个自定义侧滑菜单栏
这里我继承`BaseMenuView`自定义了一个侧滑菜单栏控件`MenuView`,需要注意的是`MenuView`对应布局的根布局要为`androidx.constraintlayout.widget.ConstraintLayout`，因为父类`BaseMenuView`
是基于`androidx.constraintlayout.widget.ConstraintLayout`编写的。`MenuView`代码如下：
```
public class MenuView extends BaseMenuView{

    private TextView mTextView;

    public MenuView(Context context, AttributeSet attrs){
        super(context,attrs);

    }

    @Override
    public int getLayoutId() {
        return R.layout.menu_layout;
    }

    @Override
    public void initView() {
        mTextView=getView(R.id.tv);
    }

    @Override
    public void initData() {

    }

    /**获取TextView控件**/
    public TextView getTextView(){
        return mTextView;
    }

}
```
`MenuView`对应布局`menu_layout.xml`代码如下(`此处再次声明 menu_layout 根布局要为 androidx.constraintlayout.widget.ConstraintLayout`)：
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"

    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/green">

    <TextView
        android:id="@+id/tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_marginTop="30dp"
        android:textSize="16sp"
        android:textColor="@color/black"
        android:text="我是侧滑菜单"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```
#### 二.在 activity 中做 侧滑菜单的布局
作为要添加`侧滑菜单栏`的`activity`,其对应布局`activity.xml`中根布局要设置为`androidx.drawerlayout.widget.DrawerLayout`,然后`DrawerLayout`中最多包含三个子布局：`界面内容布局`,
`左侧滑菜单栏布局`和`右侧滑菜单栏布局`。最少包含两个子布局：`界面内容布局`和一个侧滑菜单栏布局。  
**这里需要注意的是：DrawerLayout的子布局中，从上往下排列，`界面内容布局`必须是第一个，若侧滑菜单栏的布局放到了第一个，会导致菜单栏功能异常**  
下面给出`MainActivity`中有两个侧滑菜单时，布局示例(`注：项目中引用实际的自定义菜单栏MenuView的全路径`)：
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.drawerlayout.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"

    android:id="@+id/mDrawerLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <!--  内容页布局  -->
    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/white">

       //其他代码省略
       //......        

    </androidx.constraintlayout.widget.ConstraintLayout>

    <!-- 侧滑菜单栏 (左侧菜单栏)-->
    <com.demo.MenuView
        android:id="@+id/menuLayout"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"/>

    <!-- 侧滑菜单栏 (右侧菜单栏)-->
    <com.demo.MenuView
        android:id="@+id/menuLayout"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="end"/>

</androidx.drawerlayout.widget.DrawerLayout>
```
然后给出`MainActivity`中含一个侧滑菜单栏时的布局代码(注：项目中引用实际的自定义菜单栏MenuView的全路径)：
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.drawerlayout.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"

    android:id="@+id/mDrawerLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <!--  内容页布局  -->
    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/white">

       //其他代码省略
       //......        

    </androidx.constraintlayout.widget.ConstraintLayout>

    <!-- 侧滑菜单栏 -->
    <com.demo.MenuView
        android:id="@+id/menuLayout"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"/>

</androidx.drawerlayout.widget.DrawerLayout>
```
然后菜单栏可以为做左侧滑菜单栏，也可以为右侧滑菜单栏，位置的设定的话，你可以在`xml`布局中的自定义菜单控件`MenuView`上设置`layout_gravity`属性,
就像下面这样(注：项目中引用实际的自定义菜单栏MenuView的全路径)：
```
    <com.demo.MenuView
        android:id="@+id/menuLayout"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="end"/>
```
其中：
```
android:layout_gravity="start"  //表示设置左侧滑菜单栏
android:layout_gravity="end"    //表示设置右侧滑菜单栏
```
当然，你也可以在代码中设置`MenuView`显示位置，就像下面这样：
```
        //设置为左侧显示(此设置可以在xml中设置)
        menuView.setLeftMenu();
        //设置为右侧显示(此设置可以在xml中设置)
        menuView.setRightMenu();
```
#### 三.MenuView 在 Activity 中使用
现在我们已经继承`BaseMenuView`写了一个自定义侧滑菜单栏`MenuView`，那么在`Activity`中使用只需写以下代码即可实现侧滑菜单功能：
```
   //声明DrawerLayout布局  和 侧滑菜单控件
    private DrawerLayout mDrawerLayout;
    private MenuView menuView;

    //初始化布局和控件
    mDrawerLayout = findViewById(R.id.mDrawerLayout);
    menuView = findViewById(R.id.menuLayout);

    //侧滑菜单栏相关设置
    private void initData() {
        //初始化设置
        menuView.init(mDrawerLayout, new BaseMenuView.OnDrawerListener() {
            @Override
            public void opened(View drawerView) {
                LogUtil.i("=====打开菜单======");
            }

            @Override
            public void closed(View drawerView) {
                LogUtil.i("=====关闭菜单======");
            }
        });
        //设置侧滑菜单栏显示屏幕宽度(不设置则默认为屏幕宽度0.75)
        menuView.setLayoutWidth(0.5);
        //设置为左侧显示(此设置可以在xml中设置)
        menuView.setLeftMenu();
        //此为 menuView 控件内部功能示例
        menuView.getTextView().setText("大家好");
        menuView.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.shortShow("为啥点击我");
            }
        });
    }
    
    //重写activity的 onPostCreate 和 onConfigurationChanged 方法并调用menuView相关方法
    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        menuView.onPostCreate();
    }
    
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        menuView.onConfigurationChanged(newConfig);
    }
```
#### 四.Activity中使用示例代码
```
public class MainActivity extends AppCompatActivity {

    //声明DrawerLayout布局
    private DrawerLayout mDrawerLayout;
    private TextView mTv;
    private Button mBtn;

    //声明侧滑菜单控件
    private MenuView menuView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtil.setDebug(true);

        initView();
        initData();
        setListener();
    }

    private void initView() {
        //mDrawerLayout布局初始化
        mDrawerLayout = findViewById(R.id.mDrawerLayout);
        mTv = findViewById(R.id.tv);
        mBtn = findViewById(R.id.btn);
        //侧滑菜单控件初始化
        menuView = findViewById(R.id.menuLayout);
    }

    private void initData() {
        //初始化设置
        menuView.init(mDrawerLayout, new BaseMenuView.OnDrawerListener() {
            @Override
            public void opened(View drawerView) {
                LogUtil.i("=====打开菜单======");
            }

            @Override
            public void closed(View drawerView) {
                LogUtil.i("=====关闭菜单======");
            }
        });
        //设置侧滑菜单栏显示屏幕宽度(不设置则默认为屏幕宽度0.75)
        menuView.setLayoutWidth(0.5);

        //设置为左侧显示(此设置可以在xml中设置)
        menuView.setLeftMenu();


        //此为 menuView 控件内部功能示例
        menuView.getTextView().setText("大家好");
        menuView.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.shortShow("为啥点击我");
            }
        });

    }

    private void setListener() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开左侧菜单
                menuView.openDrawer();
            }
        });
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        menuView.onPostCreate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        menuView.onConfigurationChanged(newConfig);
    }

}
```
#### 五.其他
更多`侧滑菜单栏`的方法了解，请参见`BaseMenuView`源码
