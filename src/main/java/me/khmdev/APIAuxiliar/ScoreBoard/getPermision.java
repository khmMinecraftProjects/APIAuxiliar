package me.khmdev.APIAuxiliar.ScoreBoard;

import me.khmdev.APIAuxiliar.Players.APIPermisons;

import org.bukkit.entity.Player;

public class getPermision implements FunctorString {

	@Override
	public String getString(Player p) {
		return APIPermisons.getPerm(p.getName());
	}

}
