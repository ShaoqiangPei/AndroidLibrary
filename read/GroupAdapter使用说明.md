## GroupAdapter使用说明
### 概述
GroupAdapter是一个列表分组显示的适配器基类，里面集成了adapter创建需要的一些基本方法，包括控件初始化,获取布局对象，加载动画，列表数据加载，控件点击事件监听等方法(具体可参见代码)。
当你要船创建一个分组显示功能的adapter，继承GroupAdapter可方便快捷实现。

### 使用说明
#### 一. javaBean继承SectionEntity类
GroupAdapter显示的数据，都需要用javaBean进行封装。并且JavaBean需要继承SectionEntity类,然后重写含(boolean isHeader, String header)参数的构造方法。
以Person对象为例，你需要这样处理：
```
public class Person extends SectionEntity {

    private String name;
    private int age;

    public Person(boolean isHeader, String header) {
        super(isHeader,header);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```
#### 二. 继承GroupAdapter,写自己的adapter
然后写一个自己的adapter，继承自GroupAdapter，以PersonAdapter为例：
```
public class PersonAdapter<T> extends GroupAdapter {

    //header
    private TextView mTvHeader;

    //item
    private TextView mTvItem;


    public PersonAdapter(List<T> data, Context context) {
        super(R.layout.item_layout, R.layout.header_layout, data,context);
    }

    @Override
    protected void convertHead(BaseViewHolder viewHolder, SectionEntity item) {
        //标题相关所有都在此处理
        //标题初始化
        mTvHeader=viewHolder.getView(R.id.tv_title);
        //标题数据处理
        mTvHeader.setText(item.header);
        //标题点击事件
        addOnClickListener(mTvHeader,viewHolder,item);
    }

    @Override
    public <T>void initView(BaseViewHolder viewHolder, T obj) {
        //item初始化
        mTvItem=viewHolder.getView(R.id.tv);

    }
   
    @Override
    public <T>void initData(BaseViewHolder viewHolder, T obj) {
        //item数据处理
        Person person= (Person) obj;
        mTvItem.setText("姓名:"+person.getName()+"     年龄:"+person.getAge());
    }

    @Override
    public <T>void setListener(BaseViewHolder viewHolder, T obj) {
        //item中控件监听
        addOnClickListener(mTvItem,viewHolder,obj);
    }
}
```
其中PersonAdapter(List<T> data, Context context)构造方法的super方法中，要引入两个布局id，一个是item的一个是header的。
Header相关方法都在convertHead中处理。item的相关处理在initView,initData和setListener方法中进行。
#### 三. GroupAdapter的使用
以PersonAdapter为例，在继承完 GroupAdapter以后，在MainActivity中使用如下:
##### 3.1 线性布局
示例代码如下:
 ```
        mPersonList=new ArrayList<>();
        //for (int i = 0; i < 10; i++) {
        //Person person;
        //if(i%7==0){//header
        //   person=new Person(true,"标题"+i);
        //}else{//item
        //   person=new Person(false,null);
        //   person.setName("小黑"+i);
        //   person.setAge(25+i);
        // }
        //   mPersonList.add(person);
        //}
        mPersonAdapter=new PersonAdapter<>(mPersonList,MainActivity.this);
        mPersonAdapter.setRecyclerLinearManager(mRecyclerView);
```
##### 3.2 九宫格布局
示例代码如下
```
        mPersonList=new ArrayList<>();
        //for (int i = 0; i < 10; i++) {
        //Person person;
        //if(i%7==0){//header
        //   person=new Person(true,"标题"+i);
        //}else{//item
        //   person=new Person(false,null);
        //   person.setName("小黑"+i);
        //   person.setAge(25+i);
        // }
        //   mPersonList.add(person);
        //}
        mPersonAdapter=new PersonAdapter<>(mPersonList,MainActivity.this);
        mPersonAdapter.setRecyclerGridManager(mRecyclerView,3);
```
##### 3.3 设置分割线,返回RecyclerView.ItemDecoration对象
线性布局的时候，可以像下面这样设置分割线：
```
//线性布局分割线l
LinearDividerItemDecoration linearDivider=mPersonAdapter.setLinearLayoutItemSpace(mRecyclerView, 5, R.color.colorAccent);
```
注意: 在实现分组适配器<即继承GroupAdapter的adapter>不能使用setGridLayoutItemSpace给列表设置分割线，会出现ui上显示的bug，为了避免
用户使用不当，我已经将GroupAdapter类中的setGridLayoutItemSpace抛出异常并做以错误提示。大家若还是需要在分组九宫格布局中做分割线的话，
需要自己在布局中，或者在adapter中处理数据的时候，用代码来实现分割线效果。
##### 3.4 移除分割线
```
/**移除RecycleView间距**/
removeItemSpace(RecyclerView recyclerView, RecyclerView.ItemDecoration divider)   
```
##### 3.5 点击事件
若要设置adapter的点击事件，你可以像下面这样写:
```
        //点击事件
        mPersonAdapter.setOnItemClickListener(new AdapterHelper.OnItemClickListener() {
            @Override
            public void itemClickListener(View view, BaseViewHolder viewHolder, Object obj) {
                Person person= (Person) obj;
                switch (view.getId()) {
                    case R.id.tv_title://header
                        ToastUtil.shortShow("===="+person.header+"===");
                        break;
                    case R.id.tv://item
                        ToastUtil.shortShow("===="+person.getName()+"===");
                        break;
                    default:
                        break;
                }
            }
        });
```

