package com.example.kita.tandanziandroid;

/**
 * Created by KITA on 2018-01-08.
 */

public class ContentVO {
    private int foodNum;
    private String name;
    private double eachkcal;
    private double eachCarbohy;
    private double eachProtein;
    private double eachFat;

    public ContentVO(){}

    public ContentVO(int foodNum, String name, double eachkcal, double eachCarbohy, double eachProtein, double eachFat) {
        super();
        this.foodNum = foodNum;
        this.name = name;
        this.eachkcal = eachkcal;
        this.eachCarbohy = eachCarbohy;
        this.eachProtein = eachProtein;
        this.eachFat = eachFat;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getEachkcal() {
        return eachkcal;
    }

    public void setEachkcal(double eachkcal) {
        this.eachkcal = eachkcal;
    }

    public double getEachCarbohy() {
        return eachCarbohy;
    }

    public void setEachCarbohy(double eachCarbohy) {
        this.eachCarbohy = eachCarbohy;
    }

    public double getEachProtein() {
        return eachProtein;
    }

    public void setEachProtein(double eachProtein) {
        this.eachProtein = eachProtein;
    }

    public double getEachFat() {
        return eachFat;
    }

    public void setEachFat(double eachFat) {
        this.eachFat = eachFat;
    }

    @Override
    public String toString() {
        return "SearchVO [foodNum=" + foodNum + ", name=" + name + ", eachkcal=" + eachkcal + ", eachCarbohy="
                + eachCarbohy + ", eachProtein=" + eachProtein + ", eachFat=" + eachFat + "]";
    }


}
