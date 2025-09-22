package GUI;

import GUI.holder.FaqMenuHolder;
import GUI.holder.LocationMenuHolder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.hilden.fishingsimulator.Faq;
import me.hilden.fishingsimulator.FishingManager;
import me.hilden.fishingsimulator.Location;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FaqMenu {

    private static Inventory faqMenu;
    private static List<Inventory> faqPages = new ArrayList<>();

    public static Inventory getFaqMenu() {
        return faqMenu;
    }

    public static List<Inventory> getFaqPages() {
        return faqPages;
    }

    private File faqFile = Paths.get("C:\\Users\\denis\\IdeaProjects\\FishingSimulator\\src\\main\\resources\\json\\faq.json").toFile();

    public FaqMenu() throws IOException {
        faqMenu = Bukkit.createInventory(new FaqMenuHolder(), 54, "Частозадаваемые вопросы");
        faqPages.add(faqMenu);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Faq> list = objectMapper.readValue(faqFile, new TypeReference<List<Faq>>() {});

        int i = 0;
        for (Faq faq : list) {
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + faq.getAnswer());
            lore.add(ChatColor.DARK_GRAY + faq.getUuid().toString());

            ItemStack item = new ItemStack(Material.getMaterial(faq.getMaterial()));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.BLUE + faq.getName());
            meta.setLore(lore);
            item.setItemMeta(meta);

            faqMenu.setItem(49, MainMenu.getYellowGlassPane());


            if (faqPages.get(i).getItem(44) != null) {
                faqPages.add(Bukkit.createInventory(new LocationMenuHolder(), 54, "Рыбы"));

                faqPages.get(i).setItem(53, MainMenu.getGreenGlassPane());
                faqPages.get(i + 1).setItem(45, MainMenu.getRedGlassPane());
                faqPages.get(i + 1).setItem(49, MainMenu.getYellowGlassPane());

                i++;
            }

            faqPages.get(i).addItem(item);
        }
    }
}
