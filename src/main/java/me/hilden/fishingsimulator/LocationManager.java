package me.hilden.fishingsimulator;

import GUI.AcceptMenu;
import GUI.LocationMenu;
import GUI.holder.AcceptMenuHolder;
import GUI.holder.LocationMenuHolder;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LocationManager {

    public Location getLocation(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        String s = lore.get(2).substring(lore.get(2).length() - 36);
        UUID uuid = UUID.fromString(s);
        return Maps.getLocations().get(uuid);

    }

    public Location getLocationWithNumber(int number) {
        for(Map.Entry<UUID, Location> entry : Maps.getLocations().entrySet()) {
            UUID key = entry.getKey();
            Location value = entry.getValue();

            if (value.getNumber() == number) {
                return value;
            }
        }
        return null;
    }

    public void teleportToLocation(Player player, ItemStack item) {
        Location location = getLocation(item);
        player.teleport(new org.bukkit.Location(player.getWorld(), location.getX(), location.getY(), location.getZ()));
    }

    private Player player;
    private InventoryClickEvent event;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public InventoryClickEvent getEvent() {
        return event;
    }

    public void setEvent(InventoryClickEvent event) {
        this.event = event;
    }

    public void buyLocation(Player player, InventoryClickEvent event, int openedLocationPage) throws IOException {
        Document document = ConnectionDB.getPlayer(player.getName());
        List<String> list = document.getList("locations", String.class);
        Location location;
        if (event.getInventory().getHolder() instanceof LocationMenuHolder) {
            location = getLocation(event.getCurrentItem());
            if (list.contains(location.getName())) {
                teleportToLocation(player, event.getCurrentItem());
                return;
            }
            this.player = player;
            this.event = event;
            player.openInventory(new AcceptMenu(event.getCurrentItem()).getAcceptInventory());
        } else if (event.getInventory().getHolder() instanceof AcceptMenuHolder) {
            location = getLocation(this.event.getCurrentItem());
            if (!list.contains(location.getName())) {
                if ((double) document.get("money") >= location.getPrice()) {
                    ConnectionDB.decreaseParameter(player.getName(), "money", (double) location.getPrice());
                    list.add(location.getName());
                    Bson update = Updates.set("locations", list);
                    UpdateResult result = ConnectionDB.getCollection().updateOne(Filters.eq("uuid", ConnectionDB.getPlayer(player.getName()).get("uuid")), update);
                    player.closeInventory();
                    player.openInventory(new LocationMenu(player).getLocationPages().get(openedLocationPage));
                } else {
                    player.sendMessage("Не хватает деньгов");
                }
            }
        }

    }

}
