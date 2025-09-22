package me.hilden.fishingsimulator;

public class Upgrade {

    private int price;
    private double upgradeMultiplier;
    private int levelNeeded;
    private String material;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getUpgradeMultiplier() {
        return upgradeMultiplier;
    }

    public void setUpgradeMultiplier(double upgradeMultiplier) {
        this.upgradeMultiplier = upgradeMultiplier;
    }

    public int getLevelNeeded() {
        return levelNeeded;
    }

    public void setLevelNeeded(int levelNeeded) {
        this.levelNeeded = levelNeeded;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
