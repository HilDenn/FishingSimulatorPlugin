package command;

import GUI.FishLocationMenu;
import GUI.LocationMenu;
import me.hilden.fishingsimulator.Location;
import me.hilden.fishingsimulator.LocationManager;
import me.hilden.fishingsimulator.Maps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class LocationsCommand implements CommandExecutor {

    LocationManager locationManager = new LocationManager();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;
        Player player = ((Player) commandSender).getPlayer();
        double d;

        if (strings.length == 0) {
            player.sendMessage("a");
            try {
                player.openInventory(new LocationMenu(player).getLocationMenu());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return false;
        }

        try {
            d = Double.parseDouble(strings[0]);
        } catch (NumberFormatException nfe) {
            player.sendMessage("иди нахуй");
            return true;
        }

        if (d > Maps.getLocations().size()) {
            player.sendMessage("иди нахуй дурак бля");
            return true;
        } else {
            try {
                Location location = locationManager.getLocationWithNumber((int) d);
                if (location != null) {
                    player.openInventory(new FishLocationMenu(location).getFishLocationMenu());
                }
            } catch (IOException e) {
                return true;
            }
        }

        return false;
    }
}
