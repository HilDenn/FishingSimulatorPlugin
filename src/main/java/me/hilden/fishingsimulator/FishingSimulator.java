package me.hilden.fishingsimulator;

import GUI.FaqMenu;
import GUI.MainMenu;
import command.*;
import event.ArmorStandTest;
import event.FishingLogic;
import event.InventoryClickEvents;
import event.PlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
public final class FishingSimulator extends JavaPlugin {

    public static FishingSimulator instance;

    @Override
    public void onEnable() {


        instance = this;
        MainMenu mainMenu = new MainMenu();
        Bukkit.getPluginManager().registerEvents(new InventoryClickEvents(), this);
        Bukkit.getPluginManager().registerEvents(new FishingLogic(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ArmorStandTest(), this);


        getCommand("upgrade").setExecutor(new UpgradeCommand());
        getCommand("sellFish").setExecutor(new SellFishCommand());
        getCommand("mainMenu").setExecutor(new MainMenuCommand());
        getCommand("locations").setExecutor(new LocationsCommand());
        getCommand("fish").setExecutor(new FishCommand());
        getCommand("faq").setExecutor(new FaqCommand());


        ConnectionDB.establishConnections();

        try {
            FaqMenu faqMenu = new FaqMenu();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//         Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}

