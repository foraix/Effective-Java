# Effective Java

## 第二章：创建和销毁对象

### 本章主题

1. 何时以及如何创建对象
2. 何时以及如何避免创建对象
3. 如何确保所创建的对象能够被适时地销毁
4. 如何管理对象销毁之前必须进行的各种清理操作

### 第一条：使用静态工厂方法替代构造器

1. 引入：类可以提供一个公有的静态工厂方法，它只是一个返回类的实例的静态方法
2. 简单示例：

~~~ java
public static Boolean valueOf(boolean b) {
    return b ? Boolean.TRUE : Boolean.FALSE;
}
~~~

3. 需要注意的是，该方法与设计模式中的工厂方法不同

#### 优点一：静态工厂方法与构造器不同的第一大优势在于他们有名称

+ 如果构造器的参数本身没有确切地描述正被返回的对象，则有适当名称的静态工厂会更加易用，易阅读

+ 一个类只能有一个带有指定参数的构造器，示例如下

+ ``` java
  /**
   * @author yuan
   * @version 1.00
   * @time 2019/3/21 22:45
   * @desc 通过两个构造器来避免一个类只能带有一个指定签名的构造器的限制，显然是不太合理的，因为用户并不清楚该调用哪个，如果没有参考文档，往往不知所云
   */
  public class TestParameter {
      private String name;
      private Integer sex;
  
      public TestParameter(String name, Integer sex) {
          this.name = name;
          this.sex = sex;
      }
  
      public TestParameter(Integer sex,String name) {
          this.name = name;
          this.sex = sex;
      }
  }
  ```

#### 优点二：不必再每次调用他们的时候都创建一个新对象

+ 上面示例中的Boolean.valueOf说明了这项技术，它从来不创建对象。
+ 这种方法类似于享元模式（Flyweight）
+ 如果程序经常申请创建同一个对象，使用这项技术将会大大提高效率
+ 静态工厂有助于严格控制某个时刻那些类应该存在，这种类被称为**实例受控的类（instance-controlled）**可以确保一个是是一个Singleton，或者是不可实例化的，他还可以确保**不可变的值类**不会存在两个相同的实例

#### 优点三：它们可以返回原返回类型的任何子类型的对象

+ 这样API可以返回对象，同时又不会是对象的类变成公有的，也会使得API更加简洁
+ 在Java8之前，接口不能有静态方法，因此现在一般不应该给接口一个**不可实例化的伴生类**

#### 优点四：所返回对象的类型可以随着每次调用而发生变化，这取决于静态工厂方法的参数值

+ 只要是已经声明的返回类型的子类型都是可以的，返回对象的类也可能随着发型版本的不同而不同

#### 优点五：方法返回的对象所属的类可以在编写包含该静态工厂方法的类时不存在

+ 这种类型的方法构成了服务提供者框架的基础如JDBC API

#### 缺点一：类如果不包含公有的护着收保护的的构造器，就不能被实例化

+ 也许会因祸得福，这样或许程序员会考虑使用组合（composition）而是不是继承，这真个是不可变类所需要的

#### 缺点二：程序员很难发现他们

+ 首先Javadoc没有注意到静态工厂方法的存在
+ 使用标准的命名能够很大程度上解决这个问题.如下：

~~~ java
//from->类型转化方法，它只有单个参数，放回该类对应的一个实例，例如：
Date d = Date.from(instant);

//of->聚合方法，带有多个参数返回该类型的一个实例，把他们合并起来，例如：
Set<Rank> faceCards = EnumSet.of(JACK,QUEEN,KING);

//valueOf->比of和from更加繁琐的一种代替方法，例如：
BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);

//getInstance或者instance->返回的实例是通过方法（如有）的参数来描述的，但是不能说与参数具有同样的值，例如：
StackWalker luke = StackWalker.getInstance(options);

//newInstance或者create->能够确保每次返回一个新的实例,例如：
Object newArray = Array.newInstance(classObject arrayLen);

//getType()->像getInstance一样，但是在工厂方法中处于不用的类使用，Type表示工厂方法返回的对象类型
例如：
FileStroe fs = Files.getFileStore(path);

//newType->像newInstance一样，但是在工厂方法中处于不同的类的时候使用。
BufferdReader br = Files.newBuffered
~~~

+ 简而言之，静态工厂方法和构造器各有用处，切忌第一反应就是使用构造器，应该考虑情况而定

### 第二条：遇到多个构造器参数考虑使用构建起

#### 原因

+ 静态工厂和构造器都有个共同的局限性，他们都不能扩展到到大量的可选参数

#### 重叠构造器模式(telecsoping constructor)

+ 第一个构造器只有必要的参数，第二个一个可选，第三个两个可选，以此类推，最后一个包含所有可选

```java
/**
 * @author yuan
 * @version 1.00
 * @time 2019/3/25 20:03
 * @desc 重叠构造器模式
 */
public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int sodium;

    /**
     * 必须要提供参数
     */
    public NutritionFacts(int servingSize, int servings) {
       this(servingSize, servings, 0);
    }

    /**
     * 可选参数
     */
    public NutritionFacts(int servingSize, int servings,int calories) {
        this(servingSize,servings,calories,0);
    }

    /**
     * 可选参数
     */
    public NutritionFacts(int servingSize, int servings, int calories, int sodium) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.sodium = sodium;
    }
}
```

+ 简而言之，重叠构造器可行，但是当有许多参数的时候，客户端代码很难编写，并且难以阅读

#### 创建无参构造器，提供setter方法（javaBean模式）

```java
/**
 * @author yuan
 * @version 1.00
 * @time 2019/3/25 20:10
 * @desc 无参构造器 + Setter方法
 */
public class NutritionFactsSetter {
    private  int servingSize;
    private  int servings;
    private  int calories;
    private  int sodium;

    public NutritionFactsSetter() {
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }
}
```

+ 遗憾的是