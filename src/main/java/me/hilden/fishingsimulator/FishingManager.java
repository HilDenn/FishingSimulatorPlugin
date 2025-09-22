package me.hilden.fishingsimulator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import net.md_5.bungee.api.ChatColor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class FishingManager {

    private static final File FISH_FILE = Paths.get("C:\\Users\\denis\\IdeaProjects\\FishingSimulator\\src\\main\\resources\\json\\fish.json").toFile();
    private static final File LOCATION_FILE = Paths.get("C:\\Users\\denis\\IdeaProjects\\FishingSimulator\\src\\main\\resources\\json\\location.json").toFile();

    private StatisticManager statisticManager = new StatisticManager();
    private UpgradeManager upgradeManager = new UpgradeManager();

    public static File getFishFile() {
        return FISH_FILE;
    }

    public static File getLocationFile() {
        return LOCATION_FILE;
    }

    public Fish getRandomFish() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(LOCATION_FILE).at("/lake/chanceToCatch");
        Map<String, String> map = objectMapper.treeToValue(jsonNode, new TypeReference<Map<String, String>>() {});
        double random = Math.random();
        double lastCheck = 0.0;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            double value = Double.parseDouble(entry.getValue());

            if (random > lastCheck && random <= (value + lastCheck)) {
                JsonNode node = objectMapper.readTree(FISH_FILE).at("/" + key);
                Fish fish = objectMapper.treeToValue(node, Fish.class);
                return fish;
            } else {
                lastCheck = value;
                continue;
            }
        }
        return null;
    }

    public Fish getRandomFish(Player player) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(LOCATION_FILE).at("/lake/chanceToCatch");
        Map<String, String> map = objectMapper.treeToValue(jsonNode, new TypeReference<Map<String, String>>() {});
        double random = Math.random();
        double lastCheck = 0.0;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            double value = Double.parseDouble(entry.getValue());

            if (random > lastCheck && random <= (value + lastCheck)) {
                JsonNode node = objectMapper.readTree(FISH_FILE).at("/" + key);
                Fish fish = objectMapper.treeToValue(node, Fish.class);

                Document document = ConnectionDB.getPlayer(player.getName());
                double randomMultiplier = Math.random();
                double multiplier = upgradeManager.getUpgrade("hook", (int) document.get("hookUpgrade")).getUpgradeMultiplier();
                int weight = (int) getRandomNumber(fish.getMinWeight(), fish.getMaxWeight(), random) * (int) multiplier;
                fish.setWeight(weight);
                int length = (int) getRandomNumber(fish.getMinLength(), fish.getMaxLength(), random) * (int) multiplier;
                fish.setLength(length);

                fish.setFisher(player);

                return fish;
            } else {
                lastCheck = value;
                continue;
            }
        }
        return null;
    }

    public double getRandomNumber(double minNumber, double maxNumber) {
        double random = Math.random();
        double difference = maxNumber - minNumber;
        double result = minNumber + difference * random;
        return result;
    }

    public double getRandomNumber(double minNumber, double maxNumber, double random) {
        double difference = maxNumber - minNumber;
        double result = minNumber + difference * random;
        return result;
    }

    public double getRandomNumber(double minNumber, double maxNumber, double random, double multiply) {
        double difference = maxNumber - minNumber;
        double result = (minNumber + difference * random) * multiply;
        return result;
    }

    public ItemStack getCaughtFish(Player player) throws IOException {
        Document document = ConnectionDB.getPlayer(player.getName());
        Fish fish = getRandomFish();
        assert fish != null;
        double random = Math.random();
        double multiplier = upgradeManager.getUpgrade("hook", (int) document.get("hookUpgrade")).getUpgradeMultiplier();
        int weight = (int) getRandomNumber(fish.getMinWeight(), fish.getMaxWeight(), random) * (int) multiplier;
        fish.setWeight(weight);
        int length = (int) getRandomNumber(fish.getMinLength(), fish.getMaxLength(), random) * (int) multiplier;
        fish.setLength(length);

        fish.setFisher(player);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Длина рыбы: " + ChatColor.DARK_GREEN + length);
        lore.add(ChatColor.GREEN + "Вес рыбы: " + ChatColor.DARK_GREEN + weight);
        lore.add(ChatColor.DARK_GRAY + fish.getUuid().toString());

        ItemStack fishItem = new ItemStack(Material.getMaterial(fish.getMaterial()));
        ItemMeta fishMeta = fishItem.getItemMeta();
        fishMeta.setDisplayName(ChatColor.BLUE + fish.getName());
        fishMeta.setLore(lore);
        fishItem.setItemMeta(fishMeta);

        return fishItem;
    }

    public ItemStack getCaughtFish(Player player, Fish fish) throws IOException {
        Document document = ConnectionDB.getPlayer(player.getName());
        assert fish != null;

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Длина рыбы: " + ChatColor.DARK_GREEN + (int) fish.getLength());
        lore.add(ChatColor.GREEN + "Вес рыбы: " + ChatColor.DARK_GREEN + (int) fish.getWeight());
        lore.add(ChatColor.DARK_GRAY + fish.getUuid().toString());

        ItemStack fishItem = new ItemStack(Material.getMaterial(fish.getMaterial()));
        ItemMeta fishMeta = fishItem.getItemMeta();
        fishMeta.setDisplayName(ChatColor.BLUE + fish.getName());
        fishMeta.setLore(lore);
        fishItem.setItemMeta(fishMeta);

        return fishItem;
    }

    public void sellFish(Player player) {
        double moneyToGive = 0.0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.CLAY_BALL) {
                List<String> lore = item.getItemMeta().getLore();
                // ChatColor's UUID bothers Fish's UUID
                String s = lore.get(2).substring(lore.get(2).length() - 36);
                UUID uuid = UUID.fromString(s);
//                Fish fish = Maps.getFish().get(uuid);
                Document fish = ConnectionDB.getFish(uuid);
                moneyToGive += (double) fish.get("weight") * (double) fish.get("price");
                player.getInventory().remove(item);
                ConnectionDB.getFishCollection().deleteOne(fish);
                ConnectionDB.increaseParameter(player.getName(), "money", moneyToGive);
            }
        }

        player.sendMessage("Диньгов у вас" + ConnectionDB.getPlayer(player.getName()).get("money"));
    }

    public void initializeFish(Fish fish) {
        Document document = new Document("uuid", fish.getUuid())
                .append("name", fish.getName())
                .append("minLength", fish.getMinLength())
                .append("maxLength", fish.getMaxLength())
                .append("minWidth", fish.getMinWeight())
                .append("maxWidth", fish.getMaxWeight())
                .append("price", fish.getPrice())
                .append("experience", fish.getExperience())
                .append("material", fish.getMaterial())
                .append("weight", fish.getWeight())
                .append("length", fish.getLength())
                .append("fisher", fish.getFisherName());

        ConnectionDB.getFishCollection().insertOne(document);
    }

    public void updateInfo(Fish fish) {
        Bson weightUpdate = Updates.set("weight", fish.getWeight());
        Bson lengthUpdate = Updates.set("length", fish.getLength());
        Bson fisherUpdate = Updates.set("fisher", fish.getFisherName());

        UpdateResult weightResult = ConnectionDB.getFishCollection().updateOne(Filters.eq("uuid", fish.getUuid()), weightUpdate);
        UpdateResult lengthResult = ConnectionDB.getFishCollection().updateOne(Filters.eq("uuid", fish.getUuid()), lengthUpdate);
        UpdateResult fisherResult = ConnectionDB.getFishCollection().updateOne(Filters.eq("uuid", fish.getUuid()), fisherUpdate);
    }

}
