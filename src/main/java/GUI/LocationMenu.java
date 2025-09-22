package GUI;

import GUI.holder.FishMenuHolder;
import GUI.holder.LocationMenuHolder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.hilden.fishingsimulator.*;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
//import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationMenu extends MainMenu{

    private Inventory locationMenu;
    private List<Inventory> locationPages = new ArrayList<>();

    public Inventory getLocationMenu() {
        return locationMenu;
    }

    public List<Inventory> getLocationPages() {
        return locationPages;
    }

    public LocationMenu(Player player) throws IOException {
        locationMenu = Bukkit.createInventory(new LocationMenuHolder(), 54, "Локации");
        locationPages.add(locationMenu);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Location> map = objectMapper.readValue(FishingManager.getLocationFile(), new TypeReference<Map<String, Location>>() {});

        Document document = ConnectionDB.getPlayer(player.getName());

        List<String> locations = document.getList("locations", String.class);

        int i = 0;
        for (Map.Entry<String, Location> entry : map.entrySet()) {
            String key = entry.getKey();
            Location value = entry.getValue();

            List<String> lore = new ArrayList<>();

            ItemStack item;

            if (locations.contains(value.getName())) {
                item = new ItemStack(Material.getMaterial(value.getMaterial()));
                lore.add(ChatColor.AQUA + "Телепортация - " + ChatColor.BOLD + "ЛКМ");
            } else {
                item = new ItemStack(Material.STONE);
                lore.add(ChatColor.AQUA + "Купить за " + ChatColor.GREEN + value.getPrice() + (ChatColor.BOLD + (ChatColor.AQUA + " ЛКМ")));
            }

            lore.add(ChatColor.AQUA + "Доступные рыбы - " + ChatColor.BOLD + "ПКМ");
            lore.add(ChatColor.DARK_GRAY + value.getUuid().toString());


            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.BLUE + value.getName());
            meta.setLore(lore);
            item.setItemMeta(meta);

            locationMenu.setItem(49, MainMenu.getYellowGlassPane());

            if (locationPages.get(i).getItem(44) != null) {
                locationPages.add(Bukkit.createInventory(new LocationMenuHolder(), 54, "Локации"));

                locationPages.get(i).setItem(53, MainMenu.getGreenGlassPane());
                locationPages.get(i + 1).setItem(45, MainMenu.getRedGlassPane());
                locationPages.get(i + 1).setItem(49, MainMenu.getYellowGlassPane());

                i++;
            }

            locationPages.get(i).addItem(item);

        }
    }
}
