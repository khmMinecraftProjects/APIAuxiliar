package me.khmdev.APIAuxiliar.Players;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PexInterface {
	@SuppressWarnings("deprecation")
	public static String getPrefix(Player player){
		String[] g=PermissionsEx.getUser(player).getGroupsNames();
		return g.length!=0?PermissionsEx.getPermissionManager().getGroup(
					g[g.length-1]).getPrefix():"";
	}
	@SuppressWarnings("deprecation")
	public static String[] getGroups(String pl) {
		return PermissionsEx.getUser(pl).getGroupsNames();
	}

	
	public static List<String> getOnlines(String rank) {
		List<String> s = new LinkedList<>();
		Set<PermissionUser> g=PermissionsEx.getPermissionManager().getGroup(rank).getUsers();

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if(g.contains(PermissionsEx.getUser(p.getName())))
			{s.add(p.getName());}
		}
		return  s;

	}
	

	@SuppressWarnings("deprecation")
	public static String getPerm(String name){
		PermissionUser user=PermissionsEx.getUser(name);
		return ChatColor.translateAlternateColorCodes('&',
				user.getGroupsNames()[user.getGroupsNames().length-1]);
	}
	public static Collection<String> getAll() {
		return PermissionsEx.getPermissionManager().getGroupNames();
	}
	public static boolean hasPerm(String name, String perm) {
		PermissionUser user=PermissionsEx.getUser(name);
		return user.has(perm);
	}
	public static boolean hasPerm(String name, List<String> perms) {
		PermissionUser user=PermissionsEx.getUser(name);
		for (String string : perms) {
			
			if(user.inGroup(string)){return true;}
		}
		return false;
	}
}
