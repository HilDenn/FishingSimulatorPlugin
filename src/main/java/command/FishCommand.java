package command;

import GUI.FishMenu;
import GUI.LocationMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;
        Player player = ((Player) commandSender).getPlayer();

        player.openInventory(FishMenu.getFishInventory());
        return false;
    }
}
