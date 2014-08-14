package me.khmdev.APIAuxiliar.ScoreBoard;

import java.util.List;

import org.bukkit.entity.Player;

public interface IBoard {

	public abstract void actualizar();

	public abstract List<Player> getPlayers();

	public abstract void actualizar(Player p);

	void clearPlayers();

	boolean containPlayer(Player pl);

	void removePlayer(Player pl);

	void addPlayer(Player pl);

	void addScoreBoard(Player pl);

	void removeScoreBoard(Player pl);
	

	

}
