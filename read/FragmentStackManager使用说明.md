## FragmentStackManager使用说明

### 概述
FragmentStackManager是一个处理 Fragment跳转,关闭的管理类，可以方便的进行Activity与Fragment间的界面跳转,以及Fragment的关闭。其采取的Fragment管理方式为回退栈方式，若想利用Fragment的非回退栈来管理Fragment，可参考[AppFragmentManager](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/AppFragmentManager%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)

### 使用说明
1. 初始化
 ```
 FragmentStackManager fragmentStackManager=FragmentStackManager.getInstance();
 ```
一般我们都直接用：
```
FragmentStackManager.getInstance();
```
2. 跳转/打开 Fragment并将Fragment添加到回退栈
```
    /***
     * 跳转fragmnet并添加到回退栈
     *
     * @param containerId 装fragment的控件id，一般是FrameLayout的id
     * @param context 上下文
     * @param tag fragment对应的tag
     * @param bundle 传值用的bundle
     * @param listener 创建一个frgmment实例的监听
     * @return
     */
    public Fragment startFragment(int containerId, Context context, String tag, Bundle bundle, OnCreateFragmentListener listener)
```
3. 关闭当前Fragment
```
   /***
     * 关闭回退栈中最后一个Fragment
     * 即关闭当前fragment
     */
   public void finish(Context context)
```
4. 返回到指定已经打开的fragment,并关闭当前及当前与指定Fragment间的所有Fragment
```
    /***
     * 返回到指定已经打开的fragment,并关闭当前及当前与指定Fragment间的所有Fragment
     * @param tag 需要返回到指定Fragment的tag
     *            若你需要返回到FragmentOne,且FragmentOne的tag为“one”,则此处tag参数填“one”
     * @param context
     */
    public void goBackToFragmentByTag(String tag,Context context)
```
5. 关闭回退栈中所有Fragment
```
    /**关闭回退栈中所有Fragment**/
    public void finishAllFragments(Context context)
```
6. 获取回退栈中fragment个数
```
    /**获取回退栈中fragment个数**/
    public int getFragmentSize(Context context)
```
7. 获取回退栈中fragment对应的tag集合
```
    /**获取回退栈中fragment对应的tag集合**/
    public List<String> getFragmentTags(Context context)
```
