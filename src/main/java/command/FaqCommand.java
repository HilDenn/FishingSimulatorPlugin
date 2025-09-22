package command;

import GUI.FaqMenu;
import GUI.FishMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FaqCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player player = ((Player) commandSender).getPlayer();

        player.openInventory(FaqMenu.getFaqMenu());
        return false;
    }
}
