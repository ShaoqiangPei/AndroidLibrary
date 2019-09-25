## AppHelper使用说明

### 说明
AppHelper作为Activity基类的一个辅助类，提供必要的基本方法,包括控件值的获取，非空判断，吐司显示，沉浸式状态栏和控件初始化等方法

### AppHelper使用介绍
#### 1. 设置状态栏透明,状态栏文字变黑
更多状态栏设置请参考util工具包中的 StatusBarUtil 类
```
setStatusBar(Context context)
```
#### 2. 获取控件值
```
getTextOfView(TextView textView)
```
#### 3. 获取非空字符串,默认返回 ""
```
getNotEmptyString(String str)
```
#### 4. 长吐司
```
showToast(String msg)
```
#### 5. 短吐司
```
showShortToast(String msg)
```
#### 6. 用于初始化控件的
obj 为AppCompatActivity或ViewGroup或View的实例，rId 控件id
```
  /***
     * 用于初始化控件的
     * @param obj 为AppCompatActivity或ViewGroup或View的实例
     * @param rId 控件id
     * @return
     */
    public <T> T getView(Object obj,int rId)
```
#### 7. 将字符串的id转成int类型(控件初始化)
```
    /**
     * 通过字符串"R.id.btn"获取控件对象
     * @param rId
     * @param idType
     * @param activity
     * eg : Button btn=getView("R.id.btn","id",context);
     *
     * 若获取图片id,你也可以这样:
     * int drawableId=activity.getResources().getIdentifier("R.drawable.ic_launch", "drawable", ctx.getPackageName());
     *
     * @return
     */
    public View getView(String rId,String idType,AppCompatActivity activity){
```
当你要将一个字符串类型的控件id，转成可用于初始化的int类型的id的时候，你可以这样：
```
int rId=activity.getResources().getIdentifier("R.id.btn", "id", ctx.getPackageName());
```
当你要将一个字符串类型的drawable  id，转成可用于初始化的int类型的drawable  id的时候，你可以这样：
```
int drawableId=activity.getResources().getIdentifier("R.drawable.ic_launch", "drawable", ctx.getPackageName());
```
