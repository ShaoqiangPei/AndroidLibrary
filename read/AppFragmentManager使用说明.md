## AppFragmentManager使用说明

### 概述
AppFragmentManager是一个处理 Fragment跳转,移除的管理类，可以方便的进行Activity与Fragment间的界面跳转,以及Fragment的关闭。其采取的Fragment管理方式为非回退栈，若想利用Fragment的回退栈来管理Fragment，可参考[FragmentStackManager](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/FragmentStackManager%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)

### 使用说明
#### 1. 初始化
```
 AppFragmentManager appFragmentManager=AppFragmentManager.getInstance();
```
一般我们都直接用：
```
AppFragmentManager.getInstance();
```
#### 2. 获取Fragment(启动的，未销毁的)个数(默认返回个数为0)
```
int getFragmentSize()
```
#### 3.打开一个Fragment,其他Fragment隐藏,返回Fragment对象
此方法使用到FragmentTransaction的show()和hide()方法，show()和hide()方法执行的时候不走Fragment的生命周期,使用时需要注意。
```
    /***
     *  打开一个Fragment,其他Fragment隐藏
     *
     * @param containerId 加载fragment的容器id
     * @param tag fragment对应的tag
     * @param bundle 传值
     * @param listener 若fragment为空则在这个listener中创建fragment
     * @return
     */
     Fragment startFragmentOtherHide(int containerId, Context context, String tag, Bundle bundle, OnCreateFragmentListener listener)
```
#### 4.单纯的打开一个Fragment,返回Fragment对象
此方法遵循Fragment的生命周期
```
    /***
     *  单纯的打开一个Fragment
     *
     * @param containerId 加载fragment的容器id
     * @param tag fragment对应的tag
     * @param bundle 传值
     * @param listener 若fragment为空则在这个listener中创建fragment
     * @return
     */
    Fragment startFragment(int containerId, Context context, String tag, Bundle bundle, OnCreateFragmentListener listener)
```
如果你要打开一个Fragment，在Activity中你可以类似如下处理：
```
//声明跳转Fragment的tag
public static final String TAG_A="A";
//声明Fragment对象
private FragmentA mFragmentA;

//跳转Fragment
mFragmentA= (FragmentA) AppFragmentManager.getInstance().startFragment(R.id.fr_layout, mContext, TAG_A, null, new AppFragmentManager.OnCreateFragmentListener() {
   @Override
   public Fragment createFragment() {
      return new FragmentA();
   }
});
```
#### 5.根据tag关闭对应Fragment
```
/**根据tag关闭对应Fragment**/
public void finishFragmentByTag(String tag,Context context)
```
#### 6.关闭所有fragment
```
public void finishAllFrdagments(Context context)
```

