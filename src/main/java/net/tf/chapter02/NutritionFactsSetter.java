package net.tf.chapter02;

/**
 * @author yuan
 * @version 1.00
 * @time 2019/3/25 20:10
 * @desc 无参构造器 + Setter方法
 */
public class NutritionFactsSetter {
    private int servingSize;
    private int servings;
    private int calories;
    private int sodium;

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
