package me.khmdev.APIAuxiliar.ScoreBoard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import me.khmdev.APIBase.Auxiliar.runKill;

import org.bukkit.entity.Player;

public class runBoard extends runKill {

	private static List<IBoard> boards = new LinkedList<>();
	private static HashMap<Player, Board> players=new HashMap<>();
	public runBoard(IBoard b) {
		boards.add(b);
	}

	public runBoard() {
	}

	// private int time=0,reload=30;
	@Override
	public void run() {List<IBoard> remove=new LinkedList<>();
		for (IBoard board : boards) {
			if(board!=null){board.actualizar();
			for (Player p : board.getPlayers()) {
				board.actualizar(p);
			}}else{
				remove.add(board);
			}
		}
		for (IBoard iBoard : remove) {
			boards.remove(iBoard);
		}
	}

	public static void addBoard(IBoard b) {
		boards.add(b);
	}
	
	public static void removeBoard(IBoard b) {
		boards.remove(b);
	}
	public static IBoard getBoard(Player p) {
		for (IBoard b : boards) {
			if(b.containPlayer(p)){
				return b;
			}	
		}
		return null;
	}

	public static void addPlayer(Player pl, Board board) {
		Board b=players.get(pl);
		if(b!=null){b.removePlayer(pl);}
		players.put(pl, board);
	}
	public static void removePlayer(Player pl) {
		players.remove(pl);
	}

	public static void clearBoard(Board board) {
		for(Player p:board.getPlayers()){
			players.remove(p);
		}
	}
}
