package me.khmdev.APIAuxiliar.Players;

import java.util.Enumeration;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;





public class VisiblesPlayer {
	
	public static void esconderJugadores(Player p) {
		ListenVisible.addNoView(p.getName());

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(!player.hasPermission("desaparecer.desactivado")){
				p.hidePlayer(player);
			}
		}
	}

	public static void esconderJugadores(Player player,
			Enumeration<IGetPlayer> jugadores) {
		while (jugadores.hasMoreElements()) {
			IGetPlayer j = jugadores.nextElement();
			player.hidePlayer(j.getPlayer());
		}
	}

	public static void mostrarLista(Player p, Enumeration<? extends IGetPlayer> jugadores) {
		while (jugadores.hasMoreElements()) {
			IGetPlayer j = jugadores.nextElement();

			p.showPlayer(j.getPlayer());
		}
	}

	public static void mostrame(Player p) {
		ListenVisible.removeInvisible(p.getName());

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.showPlayer(p);

		}
	}

	public static void ocultarme(Player p) {
		ListenVisible.addInvisible(p.getName());

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {

			player.hidePlayer(p);

		}
	}

	public static void mostrame(Player p, Enumeration<? extends IGetPlayer> jugadores) {
		while (jugadores.hasMoreElements()) {
			IGetPlayer j = jugadores.nextElement();

			j.getPlayer().showPlayer(p);
		}
	}

	public static void ocultarme(Player p, Enumeration<? extends IGetPlayer> jugadores) {
		while (jugadores.hasMoreElements()) {
			IGetPlayer j = jugadores.nextElement();

			j.getPlayer().hidePlayer(p);
		}
	}

	public static void resetearJugadores(Player p) {
		ListenVisible.removeNoView(p.getName());
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			p.showPlayer(player);
		}
	}


	

}
