package me.khmdev.APIAuxiliar.Effects;

import java.util.HashMap;

import me.khmdev.APIAuxiliar.Auxiliar.Timer;
import me.khmdev.APIAuxiliar.lang.Lang;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerFreeze implements Listener {

	public static void init(JavaPlugin plug) {
		plug.getServer().getPluginManager()
				.registerEvents(new ListenerFreeze(), plug);
	}

	private static HashMap<String, Timer> frees = new HashMap<>();

	@EventHandler(priority = EventPriority.HIGH)
	public void move(PlayerMoveEvent e) {
		if (e.getFrom().getX() != e.getTo().getX()
				|| e.getFrom().getZ() != e.getTo().getZ()) {
			Player pl = e.getPlayer();
			String name = pl.getName();
			if (!frees.containsKey(name)) {
				return;
			}
			Timer t = frees.get(name);
			if (t.isEnd()) {
				frees.remove(name);
				return;
			}
			if (t.getLeftSeconds() >= 0) {
				pl.sendMessage(Lang.get("ListenerFreeze.wait").replace("%time%", t.getLeftSeconds()+""));
			}
			pl.teleport(e.getFrom());
		}

	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEntityEvent event) {
		Block(event, event.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void move(PlayerInteractEvent event) {
		Block(event, event.getPlayer());
	}

	private static void Block(Cancellable event, Player pl) {
		String name = pl.getName();
		if (!frees.containsKey(name)) {
			return;
		}
		Timer t = frees.get(name);
		if (t.isEnd()) {
			frees.remove(name);
			return;
		}
		if (t.getLeftSeconds() >= 0) {

			pl.sendMessage(Lang.get("ListenerFreeze.wait").replace("%time%", t.getLeftSeconds()+""));
		}
		event.setCancelled(true);
	}

	public static void addPlayer(String name, long time) {
		frees.put(name, new Timer(time));
	}

	public static void removePlayer(String name) {
		frees.remove(name);
	}

	public static boolean conteinPlayer(String name) {
		return frees.containsKey(name);
	}
}
