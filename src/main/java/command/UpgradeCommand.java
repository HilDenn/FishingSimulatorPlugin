package command;

import GUI.FishrodUpgrade;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class UpgradeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;
        Player player = ((Player) commandSender).getPlayer();

        FishrodUpgrade fishrodUpgrade = null;
        try {
            fishrodUpgrade = new FishrodUpgrade(player);
        } catch (IOException e) {
            return true;
//            throw new RuntimeException(e);
        }
        player.openInventory(fishrodUpgrade.getInventory());
        return false;
    }

}
