package me.hilden.fishingsimulator;

import org.bukkit.entity.Player;

public class Statistic {

    private Player player;
    private int money = 0;
    private int caughtFish = 0;

    private int hookUpgrade = 0;
    private int lineUpgrade = 0;
    private int rodUpgrade = 0;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCaughtFish() {
        return caughtFish;
    }

    public void setCaughtFish(int caughtFish) {
        this.caughtFish = caughtFish;
    }

    public int getHookUpgrade() {
        return hookUpgrade;
    }

    public void setHookUpgrade(int hookUpgrade) {
        this.hookUpgrade = hookUpgrade;
    }

    public int getLineUpgrade() {
        return lineUpgrade;
    }

    public void setLineUpgrade(int lineUpgrade) {
        this.lineUpgrade = lineUpgrade;
    }

    public int getRodUpgrade() {
        return rodUpgrade;
    }

    public void setRodUpgrade(int rodUpgrade) {
        this.rodUpgrade = rodUpgrade;
    }

    public Statistic (Player player) {
        this.player = player;
    }

}
