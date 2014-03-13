package imsr.Istom1n.imsrwarp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import imsr.Istom1n.imsrwarp.objects.Warp;
import imsr.Istom1n.imsrwarp.objects.WarpTimer;

import java.util.ArrayList;

public class Utils {
	public static ArrayList<WarpTimer>	warpTimers	= new ArrayList<WarpTimer>();


	public static String getPrefix() {
		return ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "IMSRWarp" + ChatColor.DARK_AQUA + "] " + ChatColor.GREEN;
	}

	public static void warpOther(Player player, Warp to) {
		if (Settings.delay == 0 || (Settings.warpOtherBypassDelay) || (Settings.opsBypassDelay && player.isOp()) || (Settings.permsBypassDelay && player.hasPermission("imsrwarp.delay.bypass"))) {
			player.teleport(to.getLocation());
			player.sendMessage(getPrefix() + "You have been warped to " + ChatColor.RED + to.getName());
			return;
		}

		delayedTeleport(player, to);
	}

	public static void warpSelf(Player player, Warp to) {
		if (Settings.delay == 0 || (Settings.opsBypassDelay && player.isOp()) || (Settings.permsBypassDelay && player.hasPermission("imsrwarp.delay.bypass"))) {
			player.teleport(to.getLocation());
			player.sendMessage(getPrefix() + "You have been warped to " + ChatColor.RED + to.getName());
			return;
		}

		delayedTeleport(player, to);
	}

	public static void warpSign(Player player, Warp to) {
		if (Settings.delay == 0 || (Settings.opsBypassDelay && player.isOp()) || (Settings.permsBypassDelay && player.hasPermission("imsrwarp.delay.bypass")) || (Settings.signsBypassDelay)) {

			player.teleport(to.getLocation());

			player.sendMessage(getPrefix() + "You have been warped to " + ChatColor.RED + to.getName());
		}

		delayedTeleport(player, to);
	}

	public static void delayedTeleport(Player player, Warp to) {
		player.sendMessage(getPrefix() + "You will be warped in " + ChatColor.RED + Settings.delay + " seconds. " + ChatColor.GREEN + "Не двигайтесь.");

		if (isWarping(player)) {
			stopWarping(player);
		}

		WarpTimer warpTimer = new WarpTimer(player, to);
		warpTimer.id = Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("IMSRWarp"), warpTimer, 20L * Settings.delay);

		warpTimers.add(warpTimer);
	}

	public static boolean isWarping(Player p) {
		for (WarpTimer t : warpTimers) {
			if (t.player.getName().equalsIgnoreCase(p.getName())) {
				return true;
			}
		}

		return false;
	}

	public static void stopWarping(Player p) {
		if (isWarping(p)) {
			WarpTimer wT = null;

			for (WarpTimer t : warpTimers) {
				if (t.player.getName().equalsIgnoreCase(p.getName())) {
					wT = t;
					break;
				}
			}

			if (wT != null) {
				Bukkit.getScheduler().cancelTask(wT.id);
				warpTimers.remove(wT);
			}
		}
	}

	public static Plugin getPlugin() {
		return Bukkit.getPluginManager().getPlugin("IMSRWarp");
	}
}
