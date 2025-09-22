package me.hilden.fishingsimulator;

import java.util.Map;
import java.util.UUID;

public class Location {
    private int number;
    private String name;
    private int price;
    private double x;
    private double y;
    private double z;
    private String material;
    private UUID uuid = UUID.randomUUID();
    private Map<String, Float> chanceToCatch;

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String getMaterial() {
        return material;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Map<String, Float> getChanceToCatch() {
        return chanceToCatch;
    }

    public Location() {
        Maps.getLocations().put(uuid, this);
    }
}
