package me.khmdev.APIAuxiliar.ScoreBoard;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class getStringConst implements FunctorString {
	protected String c;
	protected int coef = 1;

	public getStringConst(String s) {
		c = ChatColor.translateAlternateColorCodes('&', s);
	}

	private HashMap<Player, Integer> moves = new HashMap<>();

	@Override
	public String getString(Player p) {
		if (c.length() < 17) {
			return c;
		} else {
			int m = 0;
			if (moves.containsKey(p)) {
				m = moves.get(p);
			}
			if (m > Integer.MAX_VALUE) {
				moves.put(p, 0);
			} else {
				moves.put(p, m + 1);
			}
			int pos = (int) ((m / coef) - 0.5) % c.length();
			
			if (pos >= c.length()) {
				pos = 0;
				m = 0;
			}
			String res = c.substring(pos, Math.min(c.length(), 15 + pos));
			if (c.length() - pos < 15) {
				String aux = "  " + c;
				res += aux.substring(0, Math.max(0, 15 - (c.length() - pos)));
			}
			m++;
			if(pos==0){res=" "+res;}
			return res;
		}

	}

}
