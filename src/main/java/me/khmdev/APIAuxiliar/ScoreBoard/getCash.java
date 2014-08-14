package me.khmdev.APIAuxiliar.ScoreBoard;

import me.khmdev.APIEconomy.Own.APIEconomy;

import org.bukkit.entity.Player;

public class getCash implements Functor {

	@Override
	public int getInt(Player p) {
		return (int) APIEconomy.getCash(p.getName());
	}

}
