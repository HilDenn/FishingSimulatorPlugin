package me.hilden.fishingsimulator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpgradeManager {

    private final File upgradeFile = Paths.get("C:\\Users\\denis\\IdeaProjects\\FishingSimulator\\src\\main\\resources\\json\\upgrade.json").toFile();

    StatisticManager statisticManager = new StatisticManager();

    public File getUpgradeFile() {
        return upgradeFile;
    }

    public Upgrade getUpgrade(String name, int number) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(upgradeFile).at("/" + name);
        Map<String, Upgrade> map = objectMapper.treeToValue(jsonNode, new TypeReference<Map<String, Upgrade>>() {});
        if (number >= map.size()) return null;
        return map.get(((Integer) number).toString());
    }

    public ItemStack getUpgradedItem(Player player, String name, String databaseName) throws IOException {
        Document document = ConnectionDB.getPlayer(player.getName());
        Upgrade upgrade = getUpgrade(name, ((int) document.get(databaseName)));
        Upgrade newUpgrade = getUpgrade(name, ((int) document.get(databaseName) + 1));
        if (getUpgrade(name, ((int) document.get(databaseName)) + 1) == null) {
            player.sendMessage("гамнина гамняная");

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + "У вас максимальный уровень");

            ItemStack item = new ItemStack(Material.getMaterial(getUpgrade(name, ((int) document.get(databaseName))).getMaterial()));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Улучшение " + ((int) document.get(databaseName)));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;

        }
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Стоит пизда короче: " + ChatColor.GOLD + newUpgrade.getPrice());
        lore.add(ChatColor.GREEN + "Ну так-то неплохо умножает: " + ChatColor.GOLD + upgrade.getUpgradeMultiplier() + ChatColor.GREEN + " ---> " + ChatColor.GOLD + newUpgrade.getUpgradeMultiplier());

        ItemStack item = new ItemStack(Material.getMaterial(upgrade.getMaterial()));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Улучшение " + document.get(databaseName) + " ---> " +  + ((int) document.get(databaseName) + 1));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getUpgradedItem(Player player) throws IOException {
        Document document = ConnectionDB.getPlayer(player.getName());
        Upgrade upgrade = getUpgrade("hook", ((int) document.get("hookUpgrade")));
        Upgrade newUpgrade = getUpgrade("hook", ((int) document.get("hookUpgrade") + 1));
        if (getUpgrade("hook", ((int) document.get("hookUpgrade")) + 1) == null) {
            player.sendMessage("гамнина гамняная");

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + "У вас максимальный уровень");

            ItemStack item = new ItemStack(Material.getMaterial(getUpgrade("hook", ((int) document.get("hookUpgrade"))).getMaterial()));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Улучшение " + ((int) document.get("hookUpgrade")));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;

        }
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Стоит пизда короче: " + ChatColor.GOLD + newUpgrade.getPrice());
        lore.add(ChatColor.GREEN + "Ну так-то неплохо умножает: " + ChatColor.GOLD + upgrade.getUpgradeMultiplier() + ChatColor.GREEN + " ---> " + ChatColor.GOLD + newUpgrade.getUpgradeMultiplier());

        ItemStack item = new ItemStack(Material.getMaterial(upgrade.getMaterial()));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Улучшение " + document.get("hookUpgrade") + " ---> " +  + ((int) document.get("hookUpgrade") + 1));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public void upgradeHook(Player player) throws IOException {
        Document document = ConnectionDB.getPlayer(player.getName());
        if ((double) document.get("money") >= getUpgrade("hook", (int) document.get("hookUpgrade") + 1).getPrice()) {
            ConnectionDB.increaseParameter(player.getName(), "hookUpgrade", 1);
            player.sendMessage("Крючок улучшили тебе хуйня");
//        } else if (document.get("hookUpgrade") > ) {

        } else {
            player.sendMessage("Нет денег говноед " + document.get("money"));
        }
    }

    public void upgradeItem(Player player, String name, String databaseName) throws IOException {
        Document document = ConnectionDB.getPlayer(player.getName());
        if ((double) document.get("money") >= getUpgrade(name, (int) document.get(databaseName) + 1).getPrice()) {
            ConnectionDB.increaseParameter(player.getName(), databaseName, 1);
            player.sendMessage("Крючок улучшили тебе хуйня");
//        } else if (document.get("hookUpgrade") > ) {

        } else {
            player.sendMessage("Нет денег говноед " + document.get("money"));
        }
    }
}
