package me.khmdev.APIAuxiliar.Avisos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.khmdev.APIBase.API;
import me.khmdev.APIBase.Almacenes.sql.AlmacenSQL;
import me.khmdev.APIBase.Almacenes.sql.FieldSQL;
import me.khmdev.APIBase.Almacenes.sql.FieldSQLChange;

public class Avisos extends BukkitRunnable {
	private static AlmacenSQL sql = API.getInstance().getSql();
	private static HashMap<Integer, Mensaje> msgs = new HashMap<>();

	@SuppressWarnings("deprecation")
	public static void addMensaje(String pl, String msg) {
		System.out.println("send to pl "+pl+"  -"+msg);
		Player ply = Bukkit.getServer().getPlayerExact(pl);
		if (ply != null) {
			System.out.println(ply.getName()+" "+ply);

			ply.sendMessage(msg);
		} else {
			sql.createField(Constantes.tablaAvisos,
					new FieldSQL(Constantes.AvisosUser,pl),
					new FieldSQL(Constantes.AvisosMSG, msg));
		}
	}

	public static void removeMensaje(String pl, String msg) {
		sql.deleteField(Constantes.tablaAvisos,
				new FieldSQL(Constantes.AvisosUser,pl),
				new FieldSQL(Constantes.AvisosMSG, msg));
	}
	public static void removeMensaje(int id) {
		sql.changeData(Constantes.tablaAvisos,
				new FieldSQLChange(Constantes.AvisosRecv,1,
						new FieldSQL(Constantes.AvisosID, id)));
	}
	@SuppressWarnings("deprecation")
	private static void actualizar() {
		if (!sql.isEnable()) {
			return;
		}
		msgs.clear();
		ResultSet r = sql.getValue(Constantes.tablaAvisos,
				new FieldSQL(Constantes.AvisosRecv,0));
		if (r == null) {
			return;
		}
		try {
			while (r.next()) {
				String user = r.getString(Constantes.AvisosUser), msg = r
						.getString(Constantes.AvisosMSG);
				int id=r.getInt(Constantes.AvisosID);
				System.out.println("sqlget "+user+" -"+id);
				Player pl = Bukkit.getServer().getPlayerExact(user);
				if (pl != null) {
					pl.sendMessage(msg);
					removeMensaje(id);
				} else {
					msgs.put(id,new Mensaje(user, msg));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		actualizar();
	}
}
