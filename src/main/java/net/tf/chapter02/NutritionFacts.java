package net.tf.chapter02;

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
