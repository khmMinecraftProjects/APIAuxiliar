package me.khmdev.APIAuxiliar.Players;

import java.util.Hashtable;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class NamesTags {
	private static Hashtable<Player, String> ChangeName = 
			new Hashtable<Player, String>();
	private static JavaPlugin plugin;
	public static boolean APItag, APIprotocol;

	public static void initNamesTag(JavaPlugin plug){
		if(APItag||APIprotocol){
			if (APItag) {
				plug.getLogger().info(plug.getName()+" usara APItag");
			} else if (APIprotocol) {
				plug.getLogger().info(plug.getName()+" usara APIprotocol");
			}
		}
		APItag = hasPluging("org.kitteh.tag.PlayerReceiveNameTagEvent");
		APIprotocol = hasPluging("com.comphenix.protocol.ProtocolLibrary");
		if (APItag) {
			plug.getServer()
					.getPluginManager()
					.registerEvents(new AuxiliarTag(),
							plug);
			plug.getLogger().info("NamesTag usara APItag");
		} else if (APIprotocol) {
			AuxiliarProtocol.creaListen(plug);
			plugin=plug;
			plug.getLogger().info("NamesTag usara APIprotocol");
		}else{
			plug.getLogger().info("NamesTag esta desactivado");
		}
	}
	public static void actualizar(Player pl){
		if (APItag) {
			AuxiliarTag.actualizar(pl);
		}
	}
	
	private static boolean hasPluging(String s) {
		try {
			Class.forName(s);
			return true;
		} catch (Exception e) {

		}
		return false;
	}
	
	
	public static String getName(Player p) {
		String s = ChangeName.get(p);
		
		if (s == null || s.length() == 0) {

			return p.getName();
		}
		if (s.length() > 16) {
			s = s.substring(0, 15);
		}
		return s;
	}

	public static void addPlayer(Player p, String nM) {
		ChangeName.put(p, nM);
		actualizar(p);
	}

	public static void removePlayer(Player p) {
		ChangeName.remove(p);
		actualizar(p);

	}
	public static void Close() {
		if (!APItag && APIprotocol) {
			AuxiliarProtocol.removeListen(plugin);
		}
	}
}
