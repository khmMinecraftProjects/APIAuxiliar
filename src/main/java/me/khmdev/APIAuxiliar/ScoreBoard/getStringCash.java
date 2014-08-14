package me.khmdev.APIAuxiliar.ScoreBoard;

import org.bukkit.entity.Player;

public class getStringCash extends getCash implements FunctorString {

	@Override
	public String getString(Player p) {
		return ""+getInt(p);
	}

}
