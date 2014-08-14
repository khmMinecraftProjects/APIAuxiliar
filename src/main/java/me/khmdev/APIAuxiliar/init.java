package me.khmdev.APIAuxiliar;

import me.khmdev.APIAuxiliar.Effects.ListenerFreeze;
import me.khmdev.APIAuxiliar.Inventory.APII;
import me.khmdev.APIAuxiliar.Players.APIPlayer;
import me.khmdev.APIAuxiliar.lang.Lang;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class init extends JavaPlugin{
	private APII apii;
	private APIPlayer apip;
	public void onEnable() {
		if (!hasPluging("APIBase")) {
			getLogger().severe(Lang.get("init_disable").replace("%plugin%", getName()));
			setEnabled(false);
			return;
		}
		apii=new APII(this);
		apip=new APIPlayer(this);
		ListenerFreeze.init(this);
		//Bukkit.getServer().getPluginManager().registerEvents(new ListenAuxiliar(),this);
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