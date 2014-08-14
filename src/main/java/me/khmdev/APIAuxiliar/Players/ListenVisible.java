package me.khmdev.APIAuxiliar.Players;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class ListenVisible implements Listener {
	private static List<String> enabler = new LinkedList<>();
	private static List<String> invisibles = new LinkedList<>();

	@EventHandler
	public void logIn(PlayerLoginEvent event) {
		if (!event.getPlayer().hasPermission("desaparecer.desactivado")) {

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (enabler.contains(player.getName())) {
				player.hidePlayer(event.getPlayer());
			}
		}}
		
		Iterator<String> it=invisibles.iterator();
		while(it.hasNext()){
			Player pl=Bukkit.getPlayer(it.next());
			if(pl!=null){
				event.getPlayer().hidePlayer(pl);
			}
		}
	}

	public static void addNoView(String s) {
		enabler.add(s);
	}

	public static void removeNoView(String s) {
		enabler.remove(s);
	}

	public static boolean containNoView(String s) {
		return enabler.contains(s);
	}
	
	public static void addInvisible(String s) {
		invisibles.add(s);
	}

	public static void removeInvisible(String s) {
		invisibles.remove(s);
	}

	public static boolean containInvisible(String s) {
		return invisibles.contains(s);
	}
}
