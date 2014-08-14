package me.khmdev.APIAuxiliar.Players;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;



import org.bukkit.Bukkit;

public class APIPermisons {
	public static boolean PEXisEnable(){
		return hasPluging("PermissionsEx");
	}

	public static List<String> getOnlines(String rank){
		return hasPluging("PermissionsEx")?
				PexInterface.getOnlines(rank):new LinkedList<String>();
	}
	public static Collection<String> getAll(){
		return hasPluging("PermissionsEx")?
				PexInterface.getAll():new LinkedList<String>();
	}

	private static boolean hasPluging(String s) {
		try {
			return Bukkit.getPluginManager().getPlugin(s).isEnabled();
		} catch (Exception e) {

		}
		return false;
	}
	public static boolean hasPerm(String name,String perm){
		return hasPluging("PermissionsEx")?
				PexInterface.hasPerm(name,perm):false;
	}
	public static String getPerm(String name){
		return hasPluging("PermissionsEx")?
				PexInterface.getPerm(name):"null";
	}
	public static boolean hasPerm(String name, List<String> perms) {
		return hasPluging("PermissionsEx")?
				PexInterface.hasPerm(name,perms):false;
	}
}
