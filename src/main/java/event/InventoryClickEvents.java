package event;

import GUI.*;
import GUI.holder.*;
import me.hilden.fishingsimulator.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.List;

public class InventoryClickEvents implements Listener {
    FishingManager fishingManager = new FishingManager();
    UpgradeManager upgradeManager = new UpgradeManager();
    LocationManager locationManager = new LocationManager();

    Player player;
    InventoryClickEvent event;

    private int openedFishPage = 0;
    private int openedLocationPage = 0;
    private int openedFishLocationPage = 0;
    private int openedFaqPage = 0;

    private FishLocationMenu fishLocationMenu;

    @EventHandler
    public void onClickInInventory(InventoryClickEvent event) throws IOException {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if (inventory.getHolder() instanceof FishrodUpgradeGuiHolder) {
            event.setCancelled(true);
            if (event.getSlot() == 1) {
                if (upgradeManager.getUpgradedItem(player) == null) return;

                if (event.getClick().isRightClick() && (int) ConnectionDB.getPlayer(player.getName()).get("hookUpgrade") > 0) {
                    ConnectionDB.decreaseParameter(player.getName(), "hookUpgrade", 1);
                    event.getClickedInventory().setItem(1, upgradeManager.getUpgradedItem(player));
                    player.sendMessage("панизил");
                    return;
                }

                if (upgradeManager.getUpgrade("hook", (int) ConnectionDB.getPlayer(player.getName()).get("hookUpgrade") + 1) == null) return;
                upgradeManager.upgradeHook(player);
                event.getClickedInventory().setItem(1, upgradeManager.getUpgradedItem(player));
            } else if (event.getSlot() == 4) {
                if (upgradeManager.getUpgradedItem(player, "line", "lineUpgrade") == null) return;

                if (event.getClick().isRightClick() && (int) ConnectionDB.getPlayer(player.getName()).get("lineUpgrade") > 0) {
                    ConnectionDB.decreaseParameter(player.getName(), "lineUpgrade", 1);
                    event.getClickedInventory().setItem(4, upgradeManager.getUpgradedItem(player, "line", "lineUpgrade"));
                    player.sendMessage("панизил");
                    return;
                }

                if (upgradeManager.getUpgrade("line", (int) ConnectionDB.getPlayer(player.getName()).get("lineUpgrade") + 1) == null) return;
                upgradeManager.upgradeItem(player, "line", "lineUpgrade");
                event.getClickedInventory().setItem(4, upgradeManager.getUpgradedItem(player, "line", "lineUpgrade"));
            }
        } else if (inventory.getHolder() instanceof MainMenuHolder) {
            event.setCancelled(true);
            if (event.getSlot() == 31) {
                fishingManager.sellFish(player);
            } else if (event.getSlot() == 16) {
                player.openInventory(new FishMenu(player).getFishPages().get(openedFishPage));
            } else if (event.getSlot() == 10) {
                player.openInventory(new LocationMenu(player).getLocationPages().get(openedLocationPage));
            } else if (event.getSlot() == 4) {
                player.openInventory(FaqMenu.getFaqPages().get(openedFaqPage));
            } else if (event.getSlot() == 25) {
                player.openInventory(new FishrodUpgrade(player).getInventory());
            }
        } else if (inventory.getHolder() instanceof FishMenuHolder) {
            event.setCancelled(true);
            if (event.getSlot() == 49) {
                player.openInventory(MainMenu.getInventory());
            } else if (event.getSlot() == 53 && FishMenu.getFishPages().size() > 1 && FishMenu.getFishPages()
                    .size() > openedFishPage + 1) {
                player.sendMessage("Дальше");
                player.openInventory(FishMenu.getFishPages().get(openedFishPage + 1));
                openedFishPage++;
            } else if (event.getSlot() == 45 && FishMenu.getFishPages().size() > 1 && openedFishPage > 0) {
                player.sendMessage("Назад");
                player.openInventory(FishMenu.getFishPages().get(openedFishPage - 1));
                openedFishPage--;
            }
        } else if (inventory.getHolder() instanceof LocationMenuHolder) {
            event.setCancelled(true);
            List<Inventory> locationPages = new LocationMenu(player).getLocationPages();
            if (event.getSlot() == 49) {
                player.openInventory(MainMenu.getInventory());
            } else if (event.getSlot() == 53 && locationPages.size() > 1 && locationPages
                    .size() > openedLocationPage + 1) {
                player.sendMessage("Дальше");
                player.openInventory(locationPages.get(openedLocationPage + 1));
                openedLocationPage++;
                player.sendMessage(openedLocationPage + "dada" + locationPages.size());
            } else if (event.getSlot() == 45 && locationPages.size() > 1 && openedLocationPage > 0) {
                player.sendMessage("Назад");
                player.openInventory(locationPages.get(openedLocationPage - 1));
                openedLocationPage--;
            } else if (event.getSlot() >= 0 && event.getSlot() <= 44 && event.getCurrentItem() != null && event.getClick() == ClickType.LEFT) {
                locationManager.buyLocation(player, event, openedLocationPage);
                this.player = locationManager.getPlayer();
                this.event = locationManager.getEvent();
            } else if (event.getSlot() >= 0 && event.getSlot() <= 44 && event.getCurrentItem() != null && event.getClick() == ClickType.RIGHT) {
                fishLocationMenu = new FishLocationMenu(event.getCurrentItem(), player);
                player.openInventory(fishLocationMenu.getFishLocationMenu());
            }
        } else if (inventory.getHolder() instanceof FishLocationMenuHolder) {
            event.setCancelled(true);
            if (event.getSlot() == 50) {
                player.openInventory(MainMenu.getInventory());
                openedFishLocationPage = 0;
            } else if (event.getSlot() == 53 && fishLocationMenu.getFishLocationPages().size() > 1 && fishLocationMenu.getFishLocationPages()
                    .size() > openedFishLocationPage + 1) {
                player.sendMessage("Дальше");
                player.openInventory(fishLocationMenu.getFishLocationPages().get(openedFishLocationPage + 1));
                openedFishLocationPage++;
//                player.sendMessage(openedFishLocationPage + "dada" + fishLocationMenu.getFishLocationPages().size());
            } else if (event.getSlot() == 45 && fishLocationMenu.getFishLocationPages()
                    .size() > 1 && openedFishLocationPage > 0) {
                player.sendMessage("Назад");
                player.openInventory(fishLocationMenu.getFishLocationPages().get(openedFishLocationPage - 1));
                openedFishLocationPage--;

            } else if (event.getSlot() == 48) {
                player.openInventory(new LocationMenu(player).getLocationPages().get(openedLocationPage));
                openedFishLocationPage = 0;
            }
        } else if (inventory.getHolder() instanceof AcceptMenuHolder) {
            event.setCancelled(true);
            if (event.getSlot() == 11) {
                player.openInventory(new LocationMenu(player).getLocationPages().get(openedLocationPage));
            } else if (event.getSlot() == 15) {
                locationManager.buyLocation(player, event, openedLocationPage);
                this.player = locationManager.getPlayer();
                this.event = locationManager.getEvent();
            } else if (event.getSlot() == 22) {
                player.openInventory(MainMenu.getInventory());
            }

        } else if (inventory.getHolder() == player.getInventory().getHolder() && event.getCurrentItem().getType() == Material.COMPASS) {
            event.setCancelled(true);
            player.openInventory(MainMenu.getInventory());
        } else if (inventory.getHolder() instanceof FaqMenuHolder) {
            event.setCancelled(true);
            if (event.getSlot() == 49) {
                player.openInventory(MainMenu.getInventory());
            } else if (event.getSlot() == 53 && FaqMenu.getFaqPages().size() > 1 && FaqMenu.getFaqPages()
                    .size() > openedFaqPage + 1) {
                player.sendMessage("Дальше");
                player.openInventory(FaqMenu.getFaqPages().get(openedFaqPage + 1));
                openedFaqPage++;
            } else if (event.getSlot() == 45 && FaqMenu.getFaqPages().size() > 1 && openedFaqPage > 0) {
                player.sendMessage("Назад");
                player.openInventory(FaqMenu.getFaqPages().get(openedFaqPage - 1));
                openedFaqPage--;
            }
        }
    }




}


