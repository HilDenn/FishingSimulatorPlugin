package GUI;

import GUI.holder.FishLocationMenuHolder;
import GUI.holder.FishMenuHolder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.hilden.fishingsimulator.*;
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

public class FishLocationMenu {

    private Inventory fishLocationMenu;
    private List<Inventory> fishLocationPages = new ArrayList<>();
    private LocationManager locationManager = new LocationManager();

    public Inventory getFishLocationMenu() {
        return fishLocationMenu;
    }

    public List<Inventory> getFishLocationPages() {
        return fishLocationPages;
    }

    public FishLocationMenu(ItemStack itemStack, Player player) throws IOException {
        fishLocationMenu = Bukkit.createInventory(new FishLocationMenuHolder(), 54, "Рыбы на локации");
        fishLocationPages.add(fishLocationMenu);

        Location location = locationManager.getLocation(itemStack);

        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(FishingManager.getLocationFile()).at("/lake/chanceToCatch");
        Map<String, Float> map = location.getChanceToCatch();

        Document document = ConnectionDB.getPlayer(player.getName());

        List<String> fishList = document.getList("caughtFish", String.class);

        int i = 0;
        for (Map.Entry<String, Float> entry : map.entrySet()) {
            String key = entry.getKey();
            Float value = entry.getValue();

            JsonNode jsonNode1 = objectMapper.readTree(FishingManager.getFishFile()).at("/" + key);
            Fish fish = objectMapper.treeToValue(jsonNode1, Fish.class);
            Maps.getFish().remove(fish.getUuid());

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + "Минимальный вес рыбы - " + fish.getMinWeight());
            lore.add(ChatColor.GREEN + "Максимальный вес рыбы - " + fish.getMaxWeight());
            lore.add(ChatColor.GREEN + "Минимальная длина рыбы - " + fish.getMinLength());
            lore.add(ChatColor.GREEN + "Максимальная длина рыбы - " + fish.getMaxLength());
            lore.add(ChatColor.GREEN + "Базовая цены рыбы за грамм - " + fish.getPrice());
            lore.add(ChatColor.DARK_GRAY + fish.getUuid().toString());

            ItemStack item;



            if (fishList.contains(fish.getName())) {
                item = new ItemStack(Material.getMaterial(fish.getMaterial()));
//                player.sendMessage("suda");
            } else {
                item = new ItemStack(Material.CLAY);
//                player.sendMessage("tuda");
            }

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.BLUE + fish.getName());
            meta.setLore(lore);
            item.setItemMeta(meta);

            ItemStack orangeGlassPane = new ItemStack(Material.STAINED_GLASS_PANE);
            ItemMeta orangeMeta = orangeGlassPane.getItemMeta();
            orangeMeta.setDisplayName(ChatColor.YELLOW + "Назад");
            orangeGlassPane.setItemMeta(orangeMeta);
            orangeGlassPane.setDurability((short) 6);

            fishLocationMenu.setItem(48, orangeGlassPane);
            fishLocationMenu.setItem(50, MainMenu.getYellowGlassPane());


            if (fishLocationPages.get(i).getItem(44) != null) {
                fishLocationPages.add(Bukkit.createInventory(new FishLocationMenuHolder(), 54, "Рыбы на локации"));

                fishLocationPages.get(i).setItem(53, MainMenu.getGreenGlassPane());
                fishLocationPages.get(i + 1).setItem(45, MainMenu.getRedGlassPane());
                fishLocationPages.get(i + 1).setItem(50, MainMenu.getYellowGlassPane());
                fishLocationPages.get(i + 1).setItem(48, orangeGlassPane);


                i++;
            }

            fishLocationPages.get(i).addItem(item);


        }
    }

    public FishLocationMenu(Location location) throws IOException {
        fishLocationMenu = Bukkit.createInventory(new FishLocationMenuHolder(), 54, "Рыбы на локации");
        fishLocationPages.add(fishLocationMenu);

//        Location location = locationManager.getLocation(itemStack);

        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(FishingManager.getLocationFile()).at("/lake/chanceToCatch");
        Map<String, Float> map = location.getChanceToCatch();


        int i = 0;
        for (Map.Entry<String, Float> entry : map.entrySet()) {
            String key = entry.getKey();
            Float value = entry.getValue();

            JsonNode jsonNode1 = objectMapper.readTree(FishingManager.getFishFile()).at("/" + key);
            Fish fish = objectMapper.treeToValue(jsonNode1, Fish.class);
            Maps.getFish().remove(fish.getUuid());

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + "Минимальный вес рыбы - " + fish.getMinWeight());
            lore.add(ChatColor.GREEN + "Максимальный вес рыбы - " + fish.getMaxWeight());
            lore.add(ChatColor.GREEN + "Минимальная длина рыбы - " + fish.getMinLength());
            lore.add(ChatColor.GREEN + "Максимальная длина рыбы - " + fish.getMaxLength());
            lore.add(ChatColor.GREEN + "Базовая цены рыбы за грамм - " + fish.getPrice());
            lore.add(ChatColor.DARK_GRAY + fish.getUuid().toString());

            ItemStack item = new ItemStack(Material.getMaterial(fish.getMaterial()));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.BLUE + fish.getName());
            meta.setLore(lore);
            item.setItemMeta(meta);


            ItemStack yellowGlassPane = new ItemStack(Material.STAINED_GLASS_PANE);
            ItemMeta yellowMeta = yellowGlassPane.getItemMeta();
            yellowMeta.setDisplayName(ChatColor.YELLOW + "На главную");
            yellowGlassPane.setItemMeta(yellowMeta);
            yellowGlassPane.setDurability((short) 4);

            ItemStack orangeGlassPane = new ItemStack(Material.STAINED_GLASS_PANE);
            ItemMeta orangeMeta = orangeGlassPane.getItemMeta();
            orangeMeta.setDisplayName(ChatColor.YELLOW + "Назад");
            orangeGlassPane.setItemMeta(orangeMeta);
            orangeGlassPane.setDurability((short) 6);

            fishLocationMenu.setItem(48, orangeGlassPane);
            fishLocationMenu.setItem(50, yellowGlassPane);


            if (fishLocationPages.get(i).getItem(44) != null) {
                fishLocationPages.add(Bukkit.createInventory(new FishLocationMenuHolder(), 54, "Рыбы на локации"));

                ItemStack greenGlassPane = new ItemStack(Material.STAINED_GLASS_PANE);
                ItemMeta greenMeta = greenGlassPane.getItemMeta();
                greenMeta.setDisplayName(ChatColor.GREEN + "Вперед");
                greenGlassPane.setItemMeta(greenMeta);
                greenGlassPane.setDurability((short) 5);

                ItemStack redGlassPane = new ItemStack(Material.STAINED_GLASS_PANE);
                ItemMeta redMeta = redGlassPane.getItemMeta();
                redMeta.setDisplayName(ChatColor.RED + "Назад");
                redGlassPane.setItemMeta(redMeta);
                redGlassPane.setDurability((short) 14);

                fishLocationPages.get(i).setItem(53, greenGlassPane);
                fishLocationPages.get(i + 1).setItem(45, redGlassPane);
                fishLocationPages.get(i + 1).setItem(48, orangeGlassPane);
                fishLocationPages.get(i + 1).setItem(50, yellowGlassPane);


                i++;
            }

            fishLocationPages.get(i).addItem(item);


        }
    }

}
