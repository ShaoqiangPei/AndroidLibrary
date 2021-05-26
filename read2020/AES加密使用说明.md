## AES加密使用说明

### 概述
`AES`作为一中高级加密方式，是目前应用十分广泛的`对称性加密`模式。为了方便使用，我封装了一个`Aes256`和`Aes128`加密工具类。  
在`Android`开发过程中，我们用得比较多的是`Aes256`工具类

### 使用说明
#### 一. Aes256加密使用
使用示例如下：
```
        //Aes256加密初始化
        Aes256.getInstance()
//                //设置加密模式,一般使用默认,除非对填充模式有特殊要求才设置(不设置的话默认为AES.CBC_PKCS5PADDING)
//                .setFillModel(AES.CBC_PKCS5PADDING)
                .setCharsetName(AES.UTF_8) //设置字符集(不设置的话默认为AES.UTF_8)
                .init(key,iv);//设置私钥,向量(偏移量)

        //Aes256加密
        String enCode256= Aes256.getInstance().encrypt(message);
        LogUtil.i("======Aes256加密结果: enCode256="+enCode256);
        //Aes256解密
        String deCode256= Aes256.getInstance().decrypt(enCode256);
        LogUtil.i("======Aes256解密结果: deCode256="+deCode256);
```
**这里需要注意的是，我们很少去设置`setFillModel`方法，默认为`AES.CBC_PKCS5PADDING`,除非对填充模式有特殊要求才设置**
#### 二. Aes128加密使用
使用示例如下：
```
        String message = "中国";
        String key = "46cc793c53dc451b";

        //Aes128加密初始化
        Aes128.getInstance()
//                //设置加密模式,一般使用默认,除非对填充模式有特殊要求才设置(不设置的话默认为AES.ECB_PKCS5PADDING)
//                .setFillModel(AES.ECB_PKCS5PADDING)
                .setCharsetName(AES.UTF_8) //设置字符集(不设置的话默认为AES.UTF_8)
                .init(key); //设置私钥
        //Aes128加密
        String enCode128 = Aes128.getInstance().encrypt(message);
        LogUtil.i("======Aes128加密结果: enCode128=" + enCode128);
        //Aes128解密
        String deCode128 = Aes128.getInstance().decrypt(enCode128);
        LogUtil.i("======Aes128解密结果: deCode128=" + deCode128);
```
**这里需要注意的是，我们很少去设置`setFillModel`方法，默认为`AES.ECB_PKCS5PADDING`,除非对填充模式有特殊要求才设置**
#### 三. AES 加密/解密 注意的问题
当`AES`加/解密出现问题时，我们可以从以下几个方面排除
```
1. AES 加密/解密 使用相同的密钥
2. 若涉及到偏移量，则AES 加密/解密 使用的偏移量要一样
3. AES 加密/解密 要使用相同加密数位，如都使用`AES_256`
4. AES 加密/解密 使用相同的字符集
5. AES 加密/解密 使用相同的加密，填充模式，如都使用`AES/CBC/PKCS5Padding`模式
6. 由于不同开发语言(如C 和 Java)及不同开发环境(如 Java 和 Android)的影响，可能相同的加解密算法在实现上出现差异，若你们注意到这个差异，就可能导致加解密出现问题
```
更多相关知识，可查看[AES加密(一) — 详解](https://www.jianshu.com/p/afa713345d96)

