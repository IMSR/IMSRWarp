package imsr.Istom1n.imsrwarp.commands;

import imsr.Istom1n.imsrwarp.Utils;
import imsr.Istom1n.imsrwarp.managers.FileManager;
import imsr.Istom1n.imsrwarp.managers.WarpManager;
import imsr.Istom1n.imsrwarp.objects.Warp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DelWarpCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if (cmd.getName().equalsIgnoreCase("delwarp")) {
			if (!WarpManager.isWarp(args[0])) {
				sender.sendMessage(ChatColor.RED + "Error: This warp doesn't exist.");
				return true;
			}

			if (!sender.hasPermission("imsrwarp.command.delwarp")) {
				sender.sendMessage(ChatColor.RED + "Error: You need the 'imsrwarp.command.delwarp' permission node to do this.");
				return true;
			}

			Warp remove = WarpManager.getWarp(args[0]);

			sender.sendMessage(Utils.getPrefix() + "The warp " + ChatColor.RED + remove.getName() + ChatColor.GREEN + " has been removed.");

			WarpManager.removeWarp(remove);
			FileManager.saveWarps();

			return true;
		}

		return false;
	}
}