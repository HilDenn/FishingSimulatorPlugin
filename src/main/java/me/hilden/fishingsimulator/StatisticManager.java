package me.hilden.fishingsimulator;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class StatisticManager {

    private static Map<Player, Statistic> playerStatistic = new HashMap<>();

    public static Map<Player, Statistic> getPlayerStatistic() {
        return playerStatistic;
    }
}
