package GUI;

import GUI.holder.AcceptMenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class AcceptMenu {

    private Inventory acceptInventory;

    public Inventory getAcceptInventory() {
        return acceptInventory;
    }

    public AcceptMenu(ItemStack itemStack) {
        acceptInventory = Bukkit.createInventory(new AcceptMenuHolder(), 27, "Точно?");

        acceptInventory.setItem(11, MainMenu.getRedGlassPane());
        acceptInventory.setItem(13, itemStack);
        acceptInventory.setItem(15, MainMenu.getGreenGlassPane());
        acceptInventory.setItem(22, MainMenu.getYellowGlassPane());
    }



}
