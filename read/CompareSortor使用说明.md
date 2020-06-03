## CompareSortor使用说明

### 概述
CompareSortor是一个排列集合类，主要用于集合的升序，降序排列。针对int类型的List集合，数字字符串的集合，以及包含int属性或数字字符串属性的对象类结合List
的排列。

### 使用说明
#### 1. CompareSortor对数字排序
代码如下：
```
List<Integer>listk=new ArrayList<>();
listk.add(1);
listk.add(9);
listk.add(3);
listk.add(7);
List<Integer>fList=CompareSortor.getInstance().sortList(listk);
LogUtil.e(MainActivity.class,"=====fList====="+fList.toString());
```
打印结果：
```
MainActivity:=====fList=====[1, 3, 7, 9]
```
#### 2. CompareSortor对对像按其属性中数字大小排序
这里需要注意的是，用于对比的对象里面的属性要写 Setter/Getter 方法，若没有会导致排序异常。  
下面是对Student以age排序(注：age需要是数字，即int，float，double，long类型)
```
Student stu1=new Student("c",12);
Student stu2=new Student("a",17);
Student stu3=new Student("d",10);
Student stu4=new Student("b",15);

List<Student>list=new ArrayList<>();
list.add(stu1);
list.add(stu2);
list.add(stu3);
list.add(stu4);

//List<Student>studentList=CompareSortor.getInstance().sortList(list,"age");
List<Student>studentList=CompareSortor.getInstance().reverseList(list,"age");
for(Student s:studentList){
    LogUtil.e(MainActivity.class,"=========s="+s.toString());
}
```
打印结果：
```
MainActivity:=========s=age=17  name=a 
MainActivity:=========s=age=15  name=b 
MainActivity:=========s=age=12  name=c
MainActivity:=========s=age=10  name=d 
```
CompareSortor.getInstance().sortList(list,"age");方法中list表示要排序的集合，age表示list中对象是以age属性进行排列的。

当然，仅这些远远不足以满足我们的开发需求，于是有一个自定义比较方法
#### 3.CompareSortor自定义排序方法
```
List<Student>studentList=CompareSortor.getInstance().customList(list, new Comparator() {
@Override
public int compare(Object obj1, Object obj2) {
   //写自己的排序规则
   //...
   return 0;
 }
});
```



