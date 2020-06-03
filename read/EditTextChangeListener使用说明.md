## EditTextChangeListener使用说明

### 概述
`EditTextChangeListener`主要用于监听`EditText`的实时输入结果，若有相关需求，可用此类快捷处理。

### 使用说明
你可以像下面这样监听输入框的实时输入结果：
```
    /**设置监听**/
    private void setListener(){
        //输入监听
        mEditText.addTextChangedListener(new EditTextChangeListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                //注：editable就是 mEditText.getText() 的值
                //Editable是个接口，而且内容是可以改变的，但string类型的content是不能改变的
                //所以获取 输入框的值可以这样：String value=editable.toString();

                LogUtil.i("======editable=="+editable.toString());
            }
        });
    }
```
这里需要注意的是`editable`就是`mEditText.getText()`的值。要获取输入框的结果，可以这样：
```
     String value=editable.toString();
```
