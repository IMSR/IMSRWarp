package imsr.Istom1n.imsrwarp.commands;

import imsr.Istom1n.imsrwarp.Settings;
import imsr.Istom1n.imsrwarp.Utils;
import imsr.Istom1n.imsrwarp.managers.FileManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class IMSRWarpCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		
		if (cmd.getName().equalsIgnoreCase("imsrwarp")) {
			if (args.length == 0) {
				return false;
			}

			if (args[0].equalsIgnoreCase("reload")) {
				if (!sender.hasPermission("imsrwarp.command.reload")) {
					sender.sendMessage(ChatColor.RED + "Ошибка: Вы не имеете прав на это.");
					return true;
				}

				FileManager.saveWarps();

				Utils.getPlugin().reloadConfig();
				Settings.loadSettings(Utils.getPlugin());

				sender.sendMessage(Utils.getPrefix() + "Конфиг перезагружен.");
			}

			return true;
		}

		return false;
	}
}