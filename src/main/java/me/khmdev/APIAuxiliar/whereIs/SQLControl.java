package me.khmdev.APIAuxiliar.whereIs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.khmdev.APIBase.Almacenes.ConstantesAlmacen;
import me.khmdev.APIBase.Almacenes.sql.AlmacenSQL;
import me.khmdev.APIBase.Almacenes.sql.Consulta;
import me.khmdev.APIBase.Almacenes.sql.FieldSQL;
import me.khmdev.APIBase.Almacenes.sql.FieldSQLChange;
import me.khmdev.APIBase.Almacenes.sql.player.SQLPlayerData;

public class SQLControl extends BukkitRunnable implements Listener {
	AlmacenSQL sql;
	private static String ip = "0.0.0.0", name="null";
	public static final String desconocido="Desconocido";
	private static final String urlAPI="http://php-khmdev.rhcloud.com/getIP.php";
	public SQLControl(AlmacenSQL sq) {
		sql = sq;
		getMyServer();
		getServers();
		if (!servers.containsKey(ip) || servers.get(ip) != name) {
			Consulta c = sql.getValue(ConstantesServerSQL.tablaServ, new FieldSQL(
					ConstantesServerSQL.ServidorID, ip));
			if (c==null){
					return;
			}	
			ResultSet r=c.getR();
			try {
				if (r.next()) {
					if (r.getString(ConstantesServerSQL.ServidorAlias) != name) {
						sql.changeData(ConstantesServerSQL.tablaServ, new FieldSQLChange(
								ConstantesServerSQL.ServidorAlias, name, new FieldSQL(
										ConstantesServerSQL.ServidorID, ip)));
					}
				} else {
					sql.createField(ConstantesServerSQL.tablaServ, new FieldSQL(
							ConstantesServerSQL.ServidorID, ip), new FieldSQL(
									ConstantesServerSQL.ServidorAlias, name));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				c.close();
			}
		}
		for (Player pla : Bukkit.getServer().getOnlinePlayers()) {
			String pl = pla.getName();
			if (!SQLPlayerData.existUser(pl)) {
				SQLPlayerData.crearUser(pl, new FieldSQL(ConstantesServerSQL.UserServidor, ip));
			} else {
				SQLPlayerData.setVar(pl, ConstantesServerSQL.UserServidor, ip);
			}
		}
	}

	private Servidor getMyServer() {
		name = Bukkit.getServerName();
		getIP();
		ip = ip + ":" + Bukkit.getServer().getPort();
		return new Servidor(ip, name);
	}
	public static String getMyServerName() {
		return name;
	}


	private void getIP() {
		try {
			URL u = new URL(urlAPI);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					u.openStream()));
			ip = in.readLine();
		} catch (Exception ex) {
		}
	}

	@EventHandler
	public void logeIn(PlayerJoinEvent e) {
		String pl = e.getPlayer().getName();
		if (!SQLPlayerData.existUser(pl)) {
			SQLPlayerData.crearUser(pl, new FieldSQL(ConstantesServerSQL.UserServidor, ip));
		} else {
			SQLPlayerData.setVar(pl, ConstantesServerSQL.UserServidor, ip);
		}
	}

	@EventHandler
	public void logeOUT(PlayerQuitEvent e) {
		LogeOut(e.getPlayer().getName());
		
	}
	
	public static void LogeOutAll(){
		for (Player pl : Bukkit.getOnlinePlayers()) {
			LogeOut(pl.getName());
		}
	}
	
	public static void LogeOut(String pl){
		SQLPlayerData.setVar(pl, ConstantesServerSQL.UserServidor,
				ConstantesServerSQL.ServerNull);
	}

	private static HashMap<String, String> servers = new HashMap<>();
	private static HashMap<String, String> players = new HashMap<>();
	private static HashMap<String, String> tmp= new HashMap<>();
	private void getServers() {
		Consulta c = sql.getValue(ConstantesServerSQL.tablaServ);
		if(c==null){
			return;
		}
		ResultSet r=c.getR();
		tmp.clear();
		
		try {
			while (r.next()) {
				
				tmp.put(r.getString(ConstantesServerSQL.ServidorID).trim(),
						r.getString(ConstantesServerSQL.ServidorAlias));

			}
			servers=tmp;
		} catch (SQLException e) {
			
		}finally{
			c.close();
		}
	}

	@Override
	public void run() {
		
		tmp.clear();
		
		Consulta c = sql.getValue(ConstantesAlmacen.tabla);
		if(c==null){
			return;
		}
		ResultSet r=c.getR();
		try {
			while (r.next()) {
				String ipp=r.getString(ConstantesServerSQL.UserServidor);		
				String name = servers.get(ipp);
				if (name == null) {
					getServers();
					name = servers.get(ipp);
				}
				
				tmp.put(r.getString(ConstantesAlmacen.id), name);
			}
			players=tmp;
		} catch (Exception e) {
			Bukkit.getServer().getLogger()
			.severe("SQLControl no ha podido conectarse con la BD");
		}finally{
			c.close();
		}
	}

	public static String getServer(String player) {
		return players.get(player) != null ? players.get(player)
				: desconocido;
	}

	public static String getMyServerID() {
		return ip;
	}

	public static String getServerByName(String server) {
		return servers.get(server) != null ? servers.get(server)
				: desconocido;
	}
}
