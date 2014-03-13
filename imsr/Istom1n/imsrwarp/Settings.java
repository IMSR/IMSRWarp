package imsr.Istom1n.imsrwarp;

import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class Settings {
	
	public static int delay = 0;
	public static boolean perWarpPerms = false;
	public static boolean signsReqPerms = false;
	public static boolean signsPerWarpPerms = false;
	public static boolean permsBypassDelay = false;
	public static boolean opsBypassDelay = false;
	public static boolean warpOtherBypassDelay = false;
	public static boolean signsBypassDelay = false;

	public static void load(IMSRWarp plugin) {
		loadSettings(plugin);
	}

	public static void loadSettings(Plugin plugin) {
		try {
			Settings.delay = plugin.getConfig().getInt("warp-delay");
			Settings.perWarpPerms = plugin.getConfig().getBoolean("per-warp-permissions");
			Settings.signsReqPerms = plugin.getConfig().getBoolean("signs-require-permissions");
			Settings.signsPerWarpPerms = plugin.getConfig().getBoolean("signs-per-warp-permissions");
			Settings.permsBypassDelay = plugin.getConfig().getBoolean("permissions-bypass-delay");
			Settings.opsBypassDelay = plugin.getConfig().getBoolean("ops-bypass-delay");
			Settings.warpOtherBypassDelay = plugin.getConfig().getBoolean("warp-other-bypass-delay");
			Settings.signsBypassDelay = plugin.getConfig().getBoolean("signsr-bypass-delay");
		} catch (Exception e) {
			plugin.getLogger().log(Level.WARNING, "Ошибка загрузки конфига, используются стандартные значения.");
		}
	}
}
