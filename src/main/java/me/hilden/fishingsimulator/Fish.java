package me.hilden.fishingsimulator;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Fish {

    private String name;
    private double minWeight;
    private double maxWeight;
    private double minLength;
    private double maxLength;
    private double price;
    private double experience;
    private String material;
    private double weight;
    private double length;
    private Player fisher;
    private String fisherName;
    private UUID uuid = UUID.randomUUID();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(double minWeight) {
        this.minWeight = minWeight;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public double getMinLength() {
        return minLength;
    }

    public void setMinLength(double minLength) {
        this.minLength = minLength;
    }

    public double getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(double maxLength) {
        this.maxLength = maxLength;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Player getFisher() {
        return fisher;
    }

    public void setFisher(Player fisher) {
        this.fisher = fisher;
    }

    public String getFisherName() {
        if (fisher == null) return null;
        return fisher.getName();
    }

    public UUID getUuid() {
        return uuid;
    }

    //    public Fish(String name, int minWeight, int maxWeight, int minLength, int maxLength, Material material) {
//        this.name = name;
//        this.minWeight = minWeight;
//        this.maxWeight = maxWeight;
//        this.minLength = minLength;
//        this.maxLength = maxLength;
//        this.material = material;
//    }

    Document document;

    public Fish() {
        Maps.getFish().put(uuid, this);

    }

}
