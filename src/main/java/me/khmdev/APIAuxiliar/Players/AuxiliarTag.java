package me.khmdev.APIAuxiliar.Players;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;
import org.kitteh.tag.TagAPI;

public class AuxiliarTag implements Listener {
	@EventHandler
	public void onNameTag(AsyncPlayerReceiveNameTagEvent event) {
		String s = NamesTags.getName(event.getNamedPlayer());
		if (s != event.getNamedPlayer().getName()) {
			event.setTag(s);
		}
	}

	public static void actualizar(Player pl) {
		TagAPI.refreshPlayer(pl);
	}
}
