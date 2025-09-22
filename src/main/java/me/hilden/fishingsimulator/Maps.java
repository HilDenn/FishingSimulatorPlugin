package me.hilden.fishingsimulator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Maps {

    private static Map<UUID, Location> locations = new HashMap<>();

    public static Map<UUID, Location> getLocations() {
        return locations;
    }

    private static Map<UUID, Fish> fish = new HashMap<>();

    static public Map<UUID, Fish> getFish() {
        return fish;
    }
}
