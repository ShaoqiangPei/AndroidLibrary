### BaseEntity使用说明

BaseEntity 作为一个可序列化对象的基类，其实现了Parcelable，Serializable接口，当你需要建一个可序列化对象的时候，你的对象类去继承BaseEntity类是一个比较快捷的办法。

#### 继承BaseEntity,使对象类具备看序列化属性的范例
假如你有一个Person类，希望这个类具备可序列化属性，你可以像下面这样来写你这个Person类：

```
package com.androidlibrary;

import com.android.commonlibrary.entity.BaseEntity;

/**
 * Description:可序列化对象示例
 * 
 * Author:pei
 * Date: 2019/7/2
 */
public class Person extends BaseEntity {
    
    private String name;
    private int age;

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
