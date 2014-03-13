package imsr.Istom1n.imsrwarp;

import org.bukkit.plugin.java.JavaPlugin;

import imsr.Istom1n.imsrwarp.commands.*;
import imsr.Istom1n.imsrwarp.managers.FileManager;

public class IMSRWarp extends JavaPlugin {
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new IWListener(this), this);

		saveDefaultConfig();
		Settings.load(this);

		FileManager.loadWarps();

		getCommand("delwarp").setExecutor(new DelWarpCommand());
		getCommand("imsrwarp").setExecutor(new EasyWarpCommand());
		getCommand("listwarp").setExecutor(new ListWarpCommand());
		getCommand("setwarp").setExecutor(new SetWarpCommand());
		getCommand("warp").setExecutor(new WarpCommand());
	}

	public void onDisable() {
		FileManager.saveWarps();
	}
}
