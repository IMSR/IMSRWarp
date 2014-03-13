package imsr.Istom1n.imsrwarp;

import imsr.Istom1n.imsrwarp.managers.WarpManager;
import imsr.Istom1n.imsrwarp.objects.Warp;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class IWListener implements Listener {
	
	public IMSRWarp plugin;
	
	public IWListener(IMSRWarp plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		Player player = e.getPlayer();
		
		if(e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()){
			cancelWarps(player);
		}
	}
	
	@EventHandler
	public void onHurt(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player player = (Player) e.getEntity();
			
			cancelWarps(player);
		}
	}
	
	public void cancelWarps(Player player){
		if(Utils.isWarping(player)){
			Utils.stopWarping(player);
			
			player.sendMessage(ChatColor.RED + "Задержка варпа была отменена.");
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.SIGN){
				Player player = e.getPlayer();
				
				Sign sign = (Sign) e.getClickedBlock().getState();
				
				if(match(sign.getLine(0), new String[] {"[IMSRWarp]"})){
					
					String warpN = ChatColor.stripColor(sign.getLine(1));
					
					if(!WarpManager.isWarp(warpN)){
						sign.setLine(0, ChatColor.DARK_RED + "!ERROR!");
						sign.setLine(1, "Варпа не");
						sign.setLine(2, "существует.");
						sign.setLine(3, "");

						return;
					}
					
					if(Settings.signsReqPerms && !player.hasPermission("imsrwarp.sign.use")){
						player.sendMessage(ChatColor.RED + "Ошибка: Вы не имеете прав на это.");
						return;
					}
					
					if(Settings.signsPerWarpPerms && !player.hasPermission("imsrwarp.warp." + warpN)){
						player.sendMessage(ChatColor.RED + "Ошибка: Вы не имеете прав на это.");
						return;
					}
					
					Warp warp = WarpManager.getWarp(warpN);

					Utils.warpSign(player, warp);
				}
			}
		}
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent e){
		Player player = e.getPlayer();

		if (match(e.getLine(0), new String[] {"[IMSRWarp]", "[IW]", "[IMSR Warp]"})) {
			if(!player.hasPermission("imsrwarp.sign.create")){
				e.setLine(0, ChatColor.DARK_RED + "Ошибка!");
				e.setLine(1, "Вы не имеете");
				e.setLine(2, "прав на созда");
				e.setLine(3, "ние табличек");
				
				return;
			}

			String warpN = e.getLine(1);
			
			if (!WarpManager.isWarp(warpN)) {
				e.setLine(0, ChatColor.DARK_RED + "Ошибка!");
				e.setLine(1, "Этого варпа");
				e.setLine(2, "не");
				e.setLine(3, "существует");
			} else {
				e.setLine(0, ChatColor.WHITE + "[IMSRWarp]");
				e.setLine(1, ChatColor.BOLD + e.getLine(1));
				e.setLine(2, ChatColor.DARK_GRAY + e.getLine(2));
				e.setLine(3, ChatColor.DARK_GRAY + e.getLine(3));
				
				player.sendMessage(Utils.getPrefix() + "Вы создали табличку с варпом.");
			}
		}
	}
	
	public boolean match(String x, String[] split){
		String xx = ChatColor.stripColor(x);
		
		for(String y: split){
			String yy = ChatColor.stripColor(y);
			
			if(xx.equalsIgnoreCase(yy)){
				return true;
			}
		}
		
		return false;
	}
}
