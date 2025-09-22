package GUI;

import GUI.holder.FishrodUpgradeGuiHolder;
import me.hilden.fishingsimulator.ConnectionDB;
import me.hilden.fishingsimulator.Upgrade;
import me.hilden.fishingsimulator.UpgradeManager;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FishrodUpgrade {

    private String name = "Улучшение ебать";
    private InventoryHolder holder = new FishrodUpgradeGuiHolder();
    private int size = 9;
    private Player player;
    private Inventory inventory;

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    UpgradeManager upgradeManager = new UpgradeManager();

    public FishrodUpgrade(Player player) throws IOException {
        Document document = ConnectionDB.getPlayer(player.getName());
        this.player = player;

        inventory = Bukkit.createInventory(holder, size, name);

//        List<String> hookLore = new ArrayList<>();
//        hookLore.add("ебани уровен" + document.get("hookUpgrade"));
//        hookLore.add("Деньгов нада" + upgradeManager.getUpgrade("hook", (int) document.get("hookUpgrade")).getPrice());
//
//        ItemStack hook = new ItemStack(Material.CLAY_BALL);
//        ItemMeta hookMeta = hook.getItemMeta();
//        hookMeta.setDisplayName("Крутчок");
//        hookMeta.setLore(hookLore);
//        hook.setItemMeta(hookMeta);
//
//        ItemStack line = new ItemStack(Material.CLAY_BALL);
//        ItemStack rod = new ItemStack(Material.CLAY_BALL);

        if (upgradeManager.getUpgradedItem(player) != null) {
            inventory.setItem(1, upgradeManager.getUpgradedItem(player));
        } else {
            player.sendMessage("улучят нече");
        }

        if (upgradeManager.getUpgradedItem(player, "line", "lineUpgrade") != null) {
            inventory.setItem(4, upgradeManager.getUpgradedItem(player, "line", "lineUpgrade"));
        } else {
            player.sendMessage("улучят нече");
        }


//        inventory.setItem(1, hook);
//        inventory.setItem(4, line);
//        inventory.setItem(7, rod);


    }

}
