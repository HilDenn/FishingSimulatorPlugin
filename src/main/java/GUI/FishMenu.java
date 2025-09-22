package GUI;

import GUI.holder.FishMenuHolder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Filters;
import me.hilden.fishingsimulator.ConnectionDB;
import me.hilden.fishingsimulator.Fish;
import me.hilden.fishingsimulator.FishingManager;
import me.hilden.fishingsimulator.Maps;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FishMenu {

    private static Inventory fishInventory;

    public static Inventory getFishInventory() {
        return fishInventory;
    }

    private static List<Inventory> fishPages = new ArrayList<>();

    public static List<Inventory> getFishPages() {
        return fishPages;
    }

    public FishMenu(Player player) throws IOException {
        fishInventory = Bukkit.createInventory(new FishMenuHolder(), 54, "Рыбы");
        fishPages.add(fishInventory);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Fish> map = objectMapper.readValue(FishingManager.getFishFile(), new TypeReference<Map<String, Fish>>() {});

        Document document = ConnectionDB.getPlayer(player.getName());

        List<String> fish = document.getList("caughtFish", String.class);

        int i = 0;
        for (Map.Entry<String, Fish> entry : map.entrySet()) {

            String key = entry.getKey();
            Fish value = entry.getValue();
            Maps.getFish().remove(value.getUuid());
            ConnectionDB.getFishCollection().deleteOne(Filters.eq("uuid", value.getUuid()));

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + "Минимальный вес рыбы - " + value.getMinWeight());
            lore.add(ChatColor.GREEN + "Максимальный вес рыбы - " + value.getMaxWeight());
            lore.add(ChatColor.GREEN + "Минимальная длина рыбы - " + value.getMinLength());
            lore.add(ChatColor.GREEN + "Максимальная длина рыбы - " + value.getMaxLength());
            lore.add(ChatColor.GREEN + "Базовая цены рыбы за грамм - " + value.getPrice());
            lore.add(ChatColor.DARK_GRAY + value.getUuid().toString());


            ItemStack item;

            if (fish.contains(value.getName())) {
                item = new ItemStack(Material.getMaterial(value.getMaterial()));
//                player.sendMessage("suda");
            } else {
                item = new ItemStack(Material.CLAY);
//                player.sendMessage("tuda");
            }
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.BLUE + value.getName());
            meta.setLore(lore);
            item.setItemMeta(meta);

            fishInventory.setItem(49, MainMenu.getYellowGlassPane());

            if (fishPages.get(i).getItem(44) != null) {
                fishPages.add(Bukkit.createInventory(new FishMenuHolder(), 54, "Рыбы"));

                fishPages.get(i).setItem(53, MainMenu.getGreenGlassPane());
                fishPages.get(i + 1).setItem(45, MainMenu.getRedGlassPane());
                fishPages.get(i + 1).setItem(49, MainMenu.getYellowGlassPane());

                i++;
            }

            fishPages.get(i).addItem(item);

        }

    }


}
