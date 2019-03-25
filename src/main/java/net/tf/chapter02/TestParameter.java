package net.tf.chapter02;

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
