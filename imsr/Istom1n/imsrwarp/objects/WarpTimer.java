package imsr.Istom1n.imsrwarp.objects;

import imsr.Istom1n.imsrwarp.Utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WarpTimer implements Runnable {
	
	public Player player = null;
	public Warp warp = null;
	public int id = 0;
	
	public WarpTimer(Player player, Warp warp){
		this.player = player;
		this.warp = warp;
	}
	
	@Override
	public void run() {
		player.teleport(warp.getLocation());
		player.sendMessage(Utils.getPrefix() + "Вы телепортированы на " + ChatColor.RED + warp.getName());
	}

}
