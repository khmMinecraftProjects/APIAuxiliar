package me.khmdev.APIAuxiliar.ScoreBoard;

import java.util.LinkedList;
import java.util.List;

import me.khmdev.APIAuxiliar.ScoreBoard.ObjetiveData;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class Board implements IBoard{
	protected FunctorString title;
	protected List<ObjetiveData> objs = new LinkedList<>();
	protected List<Player> players = new LinkedList<Player>();

	public Board(FunctorString name) {
		title = (name);
	}
	
	public Board(String name) {
		title = new getStringConst(name);
	}
	
	public List<ObjetiveData> getObjs() {
		return objs;
	}

	


	public void set(List<ObjetiveData> d) {
		objs = d;
	}

	public void add(ObjetiveData d) {
		objs.add(d);
	}

	protected int spaces = 1;

	protected void setWhite(Objective objective, int i) {

		Score scr = objective.getScore(getWhite());

		scr.setScore(i);
	}

	protected String getWhite() {
		String s = "";
		if (spaces == 17) {
			spaces = 1;
		}
		for (int j = 0; j < spaces; j++) {
			s += " ";
		}
		spaces++;
		return s;
	}
	@Override
	public void actualizar() {
		
	}
	@Override
	public void actualizar(Player p) {
		if (title == null || title == null) {
			return;
		}
		String s = title.getString(p);
		Scoreboard board = p.getScoreboard();
		Objective objective = board.getObjective(s);
		if (objective != null) {
			objective.unregister();
		}
		objective = board.registerNewObjective(s, s);

		for (ObjetiveData o : objs) {
			String obj=o.getTitle(p);
			if (obj == null) {
				o.setTitle(new getStringConst(getWhite()));
				obj=o.getTitle(p);
			}
			Score scr = objective.getScore(obj);
			scr.setScore(o.getF(p));

		}

		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	@Override
	public final void addPlayer(Player pl) {
		players.add(pl);
		runBoard.addPlayer(pl,this);
		addScoreBoard(pl);
	}
	@Override
	public final void removePlayer(Player pl) {
		players.remove(pl);
		runBoard.removePlayer(pl);
		removeScoreBoard(pl);
	}
	@Override
	public final void clearPlayers() {
		players.clear();
		runBoard.clearBoard(this);
		clearScoreBoard();
	}
	
	@Override
	public void addScoreBoard(Player pl) {
		pl.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		actualizar(pl);
	}


	@Override
	public void removeScoreBoard(Player pl) {
		pl.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}
	@Override
	public List<Player> getPlayers() {
		return players;
	}
	@Override
	public boolean containPlayer(Player pl) {
		return players.contains(pl);
	}
	
	
	private final void clearScoreBoard() {
		for (Player pl : players) {
			removeScoreBoard(pl);
		}
	}
}
