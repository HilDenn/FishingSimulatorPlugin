package GUI;

import GUI.holder.FishMenuHolder;
import GUI.holder.LocationMenuHolder;
import GUI.holder.MainMenuHolder;
import me.hilden.fishingsimulator.Faq;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainMenu {

    protected String penis = "penis";

    private static Inventory inventory;

    public static Inventory getInventory() {
        return inventory;
    }

    private static ItemStack yellowGlassPane;
    private static ItemStack redGlassPane;
    private static ItemStack greenGlassPane;

    public String getPenis() {
        return penis;
    }

    public static ItemStack getYellowGlassPane() {
        return yellowGlassPane;
    }

    public static ItemStack getRedGlassPane() {
        return redGlassPane;
    }

    public static ItemStack getGreenGlassPane() {
        return greenGlassPane;
    }

    public MainMenu() {
        inventory = Bukkit.createInventory(new MainMenuHolder(), 54, "Главное меню");

        ItemStack locations = new ItemStack(Material.GRASS);
        ItemMeta locationsMeta = locations.getItemMeta();
        locationsMeta.setDisplayName(ChatColor.BLUE + "Локации");
        locations.setItemMeta(locationsMeta);

        ItemStack fish = new ItemStack(Material.RAW_FISH);
        ItemMeta fishMeta = fish.getItemMeta();
        fishMeta.setDisplayName(ChatColor.BLUE + "Рыбы");
        fish.setItemMeta(fishMeta);

        ItemStack sellFish = new ItemStack(Material.DIAMOND);
        ItemMeta sellFishMeta = sellFish.getItemMeta();
        sellFishMeta.setDisplayName(ChatColor.BLUE + "Продать");
        sellFish.setItemMeta(sellFishMeta);

        ItemStack faq = new ItemStack(Material.STONE);
        ItemMeta faqMeta = faq.getItemMeta();
        faqMeta.setDisplayName(ChatColor.BLUE + "Частозадаваемые вопросы");
        faq.setItemMeta(faqMeta);

        ItemStack upgrade = new ItemStack(Material.CLAY_BALL);
        ItemMeta upgradeMeta = upgrade.getItemMeta();
        upgradeMeta.setDisplayName(ChatColor.BLUE + "Улученя");
        upgrade.setItemMeta(upgradeMeta);

        inventory.setItem(4, faq);
        inventory.setItem(10, locations);
        inventory.setItem(16, fish);
        inventory.setItem(31, sellFish);
        inventory.setItem(25, upgrade);

        yellowGlassPane = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta yellowMeta = yellowGlassPane.getItemMeta();
        yellowMeta.setDisplayName(ChatColor.YELLOW + "На главную");
        yellowGlassPane.setItemMeta(yellowMeta);
        yellowGlassPane.setDurability((short) 4);

        greenGlassPane = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta greenMeta = greenGlassPane.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GREEN + "Вперед");
        greenGlassPane.setItemMeta(greenMeta);
        greenGlassPane.setDurability((short) 5);

        redGlassPane = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta redMeta = redGlassPane.getItemMeta();
        redMeta.setDisplayName(ChatColor.RED + "Назад");
        redGlassPane.setItemMeta(redMeta);
        redGlassPane.setDurability((short) 14);
    }

}
