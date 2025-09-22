package command;

import GUI.FishrodUpgrade;
import me.hilden.fishingsimulator.FishingManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SellFishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;
        Player player = ((Player) commandSender).getPlayer();

        FishingManager fishingManager = new FishingManager();
        fishingManager.sellFish(player);
        return false;
    }

}
