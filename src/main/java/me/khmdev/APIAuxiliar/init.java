package me.khmdev.APIAuxiliar;

import me.khmdev.APIAuxiliar.Avisos.Avisos;
import me.khmdev.APIAuxiliar.Avisos.Constantes;
import me.khmdev.APIAuxiliar.Effects.ListenerFreeze;
import me.khmdev.APIAuxiliar.Inventory.APII;
import me.khmdev.APIAuxiliar.Players.APIPlayer;
import me.khmdev.APIAuxiliar.lang.Lang;
import me.khmdev.APIAuxiliar.whereIs.ConstantesServerSQL;
import me.khmdev.APIAuxiliar.whereIs.SQLControl;
import me.khmdev.APIBase.API;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class init extends JavaPlugin {
	private APII apii;
	private APIPlayer apip;
	private static final int timeAvisos = 60;
	private static final int timeServers = 15;
	public void onEnable() {
		if (!hasPluging("APIBase")) {
			getLogger().severe(
					Lang.get("init_disable").replace("%plugin%", getName()));
			setEnabled(false);
			return;
		}
		if (API.getInstance().getSql().isEnable()) {
			/*
			 * Avisos
			 */
			Bukkit.getServer()
					.getScheduler()
					.runTaskTimerAsynchronously(this, new Avisos(),
							timeAvisos * 20, timeAvisos * 20);
			for (String s : Constantes.sql) {
				API.getInstance().getSql().sendUpdate(s);
			}
			/*
			 * Servers
			 */
			SQLControl sqlC = new SQLControl(API.getInstance().getSql());

			Bukkit.getPluginManager().registerEvents(sqlC, this);
			Bukkit.getServer().getScheduler()
					.runTaskTimerAsynchronously(this, sqlC, 20, timeServers * 20);

			for (String s : ConstantesServerSQL.sql) {
				API.getInstance().getSql().sendUpdate(s);

			}
		}
		apii = new APII(this);
		apip = new APIPlayer(this);
		ListenerFreeze.init(this);
		
		// Bukkit.getServer().getPluginManager().registerEvents(new
			// ListenAuxiliar(),this);
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
