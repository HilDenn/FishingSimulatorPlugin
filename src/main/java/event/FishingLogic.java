package event;

import GUI.MainMenu;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import me.hilden.fishingsimulator.*;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FishingLogic implements Listener {

    UpgradeManager upgradeManager = new UpgradeManager();


    FishingManager fishingManager = new FishingManager();
    BossBar bossBar;
    List<Player> playersFishing = new ArrayList<>();
    Entity hook;

    Fish fish = null;
    int count = 0;
    double idk;

    @EventHandler
    public void onFishing(PlayerFishEvent event) throws IOException {
        Player player = event.getPlayer();
        hook = event.getHook();

        if (event.getState() == PlayerFishEvent.State.FISHING) {
            playersFishing.add(player);
            player.sendMessage("Добавили тебя пидарас");
        }

        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH || event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY) {
            event.setCancelled(true);
            bossBar = Bukkit.createBossBar("Давай слабак", BarColor.GREEN, BarStyle.SOLID, BarFlag.DARKEN_SKY);
            bossBar.setProgress(0.5);
            bossBar.addPlayer(player);
//            fishItem = fishingManager.getCaughtFish(player);
//            fish =
            fish = fishingManager.getRandomFish(player);
            Upgrade lineUpgrade = upgradeManager.getUpgrade("line", (int) ConnectionDB.getPlayer(player.getName()).get("lineUpgrade"));
            Upgrade hookUpgrade = upgradeManager.getUpgrade("hook", (int) ConnectionDB.getPlayer(player.getName()).get("hookUpgrade"));
            idk = (lineUpgrade.getUpgradeMultiplier() / fish.getWeight());
//            double idk = (double) ((int) ConnectionDB.getPlayer(player.getName()).get("hookUpgrade") / (int) ConnectionDB.getPlayer(player.getName()).get("lineUpgrade"));
//            idk = fish.getWeight() * hookUpgrade;


            count = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(FishingSimulator.instance, new Runnable() {
                public void run() {
                    if (bossBar.getProgress() > (0.1 / idk)) {
                        bossBar.setProgress(bossBar.getProgress() - (0.1 / idk));
                    } else {
                        bossBar.removeAll();
                        bossBar = null;
                        fish = null;
                        Bukkit.getScheduler().cancelTask(count);
                    }
                }
            }, 0L, 10L);


        } else if (((event.getState() == PlayerFishEvent.State.FAILED_ATTEMPT && bossBar == null) || event.getState() == PlayerFishEvent.State.CAUGHT_FISH || event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY || event.getState() == PlayerFishEvent.State.IN_GROUND) && !event.isCancelled()) {
            playersFishing.remove(player);
            event.getHook().remove();
            player.sendMessage("Удалили тебя все пизда");
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) throws IOException {
        Player player = event.getPlayer();
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR ||event.getAction() == Action.LEFT_CLICK_BLOCK) && player.getItemInHand().getType() == Material.COMPASS) {
            player.openInventory(MainMenu.getInventory());
        }

        if (bossBar != null && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) {
            if ((bossBar.getProgress() + (0.1 * idk)) < 1) {
                bossBar.setProgress(bossBar.getProgress() + (0.1 * idk));
                player.sendMessage("Еще чутка " + bossBar.getProgress());
            } else {
                bossBar.removeAll();
                bossBar = null;
                Bukkit.getScheduler().cancelTask(count);
//                player.getInventory().addItem(fishingManager.getCaughtFish(player));
                fishingManager.initializeFish(fish);
                fishingManager.updateInfo(fish);
                player.sendMessage(fish.getUuid().toString());
                List<String> fishList = ConnectionDB.getPlayer(player.getName()).getList("caughtFish", String.class);
                if (!fishList.contains(fish.getName())) {
                    fishList.add(fish.getName());
                    Bson update = Updates.set("caughtFish", fishList);
                    UpdateResult result = ConnectionDB.getCollection().updateOne(Filters.eq("uuid", ConnectionDB.getPlayer(player.getName()).get("uuid")), update);
                }
                player.getInventory().addItem(fishingManager.getCaughtFish(player, fish));
                fish = null;
                hook.remove();
            }

        } else if (bossBar != null && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            event.setCancelled(true);
        }

    }

}
