package me.khmdev.APIAuxiliar.ScoreBoard;

import org.bukkit.entity.Player;

public class getConstant implements Functor{
	private int n;

	public getConstant(int i){
		n=i;
	}

	@Override
	public int getInt(Player p) {
		return n;
	}

}
