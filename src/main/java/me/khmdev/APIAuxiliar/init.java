package me.khmdev.APIAuxiliar;

import me.khmdev.APIAuxiliar.Effects.ListenerFreeze;
import me.khmdev.APIAuxiliar.Inventory.APII;
import me.khmdev.APIAuxiliar.Players.APIPlayer;
import me.khmdev.APIAuxiliar.lang.Lang;
import me.khmdev.APIAuxiliar.whereIs.SQLControl;
import me.khmdev.APIBase.API;
import me.khmdev.APIBase.Almacenes.local.ConfigFile;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class init extends JavaPlugin {
	private APII apii;
	private APIPlayer apip;
	private SQLControl sql;
	private static final int time=15;
	public void onEnable() {
		if (!hasPluging("APIBase")) {
			getLogger().severe(
					Lang.get("init_disable").replace("%plugin%", getName()));
			setEnabled(false);
			return;
		}
		configure();
		apii = new APII(this);
		apip = new APIPlayer(this);
		ListenerFreeze.init(this);
		if (API.getInstance().getSql().isEnable()) {
			sql=new SQLControl(API.getInstance().getSql());
			Bukkit.getPluginManager().registerEvents(sql,this);
			Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, 
					sql, time*20, time*20);
				
		}
		// Bukkit.getServer().getPluginManager().registerEvents(new
			// ListenAuxiliar(),this);
	}
	public static boolean active=false;
	private void configure(){
		ConfigFile conf=new ConfigFile(getDataFolder(), "config");
		FileConfiguration section=conf.getConfig();
		
		if(section.isBoolean("Activado")){
			active=section.getBoolean("Activado");
		}
		section.set("Activado",true);
		conf.saveConfig();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (apii.onCommand(sender, cmd, label, args)) {
			return true;
		}

		if (apip.onCommand(sender, cmd, label, args)) {
			return true;
		}

		return false;
	}

	private static boolean hasPluging(String s) {
		try {
			return Bukkit.getPluginManager().getPlugin(s).isEnabled();
		} catch (Exception e) {

		}
		return false;
	}

}
