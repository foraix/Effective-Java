package net.tf.chapter02;

/**
 * @author yuan
 * @version 1.00
 * @time 2019/3/25 20:23
 * @desc 建造者模式(Builder)
 */
public class NutritionFactsBuilder {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int sodium;

    /**
     * 内部构造器Builder
     */
    public static class Builder {
        private final int servingSize;
        private int servings;
        private int calories;
        private int sodium;

        /**
         * 提供必要参数构造器
         */
        Builder(int servingSize) {
            this.servingSize = servingSize;
        }

        /**
         * 提供可选参数构造
         */
        public Builder servings(int servings) {
            this.servings = servings;
            return this;
        }

        /**
         * 提供可选参数构造
         */
        Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

        /**
         * 提供可选参数构造
         */
        Builder sodium(int sodium) {
            this.sodium = sodium;
            return this;
        }

        /**
         * 返回所构建的对象
         */
        NutritionFactsBuilder build() {
            return new NutritionFactsBuilder(this);
        }
    }

    private NutritionFactsBuilder(Builder builder) {
        this.servingSize = builder.servingSize;
        this.servings = builder.servings;
        this.calories = builder.calories;
        this.sodium = builder.sodium;
    }

    public static void main(String[] args) {
        NutritionFactsBuilder nutritionFactsBuilder = new NutritionFactsBuilder.Builder(1)
                .calories(1)
                .sodium(2).build();
        System.out.println(nutritionFactsBuilder);
    }

    @Override
    public String toString() {
        return "NutritionFactsBuilder{" +
                "servingSize=" + servingSize +
                ", servings=" + servings +
                ", calories=" + calories +
                ", sodium=" + sodium +
                '}';
    }
}
