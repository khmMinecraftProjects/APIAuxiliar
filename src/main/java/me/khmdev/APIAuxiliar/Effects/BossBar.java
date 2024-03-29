package me.khmdev.APIAuxiliar.Effects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import me.khmdev.APIBase.API;
import net.minecraft.server.v1_7_R3.DataWatcher;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BossBar {

	private static final int ENTITY_ID = 1234;

	private static HashMap<String, Object> Bars = new HashMap<>();
	private static List<BarRun> runs = new LinkedList<>();
	private static Object creator = null,destroy=null;

	public static Object createPacket(Location l, String txt, int h) {

		try {
			if (creator == null) {
				creator = AuxPackets.getClass("PacketPlayOutSpawnEntityLiving");
			}
			AuxPackets.setValue(creator, "a", ENTITY_ID);
			AuxPackets.setValue(creator, "b", (byte) 63);
			AuxPackets.setValue(creator, "c",
					(int) Math.floor(l.getBlockX() * 32.0D));
			AuxPackets.setValue(creator, "d", (int) 0);
			AuxPackets.setValue(creator, "e",
					(int) Math.floor(l.getBlockZ() * 32.0D));
			AuxPackets.setValue(creator, "f", (byte) 0);
			AuxPackets.setValue(creator, "g", (byte) 0);
			AuxPackets.setValue(creator, "h", (byte) 0);
			AuxPackets.setValue(creator, "i", (byte) 0);
			AuxPackets.setValue(creator, "j", (byte) 0);
			AuxPackets.setValue(creator, "k", (byte) 0);
			DataWatcher watcher = createWatcher(txt, h);
			AuxPackets.setValue(creator, "l", watcher);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return creator;
	}

	private static DataWatcher createWatcher(String text, int health) {
		DataWatcher watcher = new DataWatcher(null);
		watcher.a(0, (Byte) (byte) 0x20);
		watcher.a(6, (Float) (float) health);
		watcher.a(10, (String) text);
		watcher.a(11, (Byte) (byte) 100);

		return watcher;
	}

	public static void sendBarToPlayer(Player pl, String txt, int Healt) {
		txt = reemplace(txt, pl.getName());

		Object packet = createPacket(pl.getLocation(), txt, Healt);
		AuxPackets.sendPacket(pl, packet);
		Bars.put(pl.getName(), packet);

	}

	private static String reemplace(String s, String pl) {
		return s.replace("%name%", pl);
	}

	public static BarRun sendCargaPlayer(Player pl, String ini, String fin,
			long tmp, Boolean atras, IFuncion f) {
		ini = reemplace(ini, pl.getName());
		ini = reemplace(fin, pl.getName());

		BarRun run = new BarRun(pl, ini, fin, tmp, atras, f);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(
				API.getInstance(), run, 10, 10);
		run.setId(id);
		return run;
	}

	public static BarRun sendCargaAll(String ini, String fin, long tmp,
			boolean atras, IFuncion f) {

		BarRun run = new BarRun(Bukkit.getServer().getOnlinePlayers(), ini,
				fin, tmp, atras, f);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(
				API.getInstance(), run, 10, 10);
		run.setId(id);
		return run;
	}

	public static void sendBarToAll(String txt, int Healt) {

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			Object packet = createPacket(player.getLocation(), txt, Healt);

			AuxPackets.sendPacket(player, packet);
			Bars.put(player.getName(), packet);

		}
	}

	public static void sendBarToGroup(String txt, int Healt, List<Player> pls) {

		for (Player player : pls) {
			Object packet = createPacket(player.getLocation(), txt, Healt);
			AuxPackets.sendPacket(player, packet);
			Bars.put(player.getName(), packet);

		}
	}

	private static Object getDestroyEntityPacket() {
		try {
			if (destroy == null) {
				destroy = AuxPackets.getClass("PacketPlayOutEntityDestroy");
			}
			AuxPackets.setValue(destroy, "a", new int[] { ENTITY_ID });
		} catch (Exception e) {
			e.printStackTrace();
		}

		return destroy;
	}

	public static void removeBarPlayer(Player pl) {
		Bars.remove(pl.getName());
		AuxPackets.sendPacket(pl, getDestroyEntityPacket());
	}

	public static void removeBarAll() {
		Iterator<Entry<String, Object>> it = Bars.entrySet().iterator();
		while (it.hasNext()) {
			String p = it.next().getKey();
			if (p != null) {
				@SuppressWarnings("deprecation")
				Player pl = Bukkit.getServer().getPlayer(p);
				AuxPackets.sendPacket(pl, getDestroyEntityPacket());
			}

		}
		Bars.clear();
	}

	public static boolean containsKey(String name) {
		return Bars.containsKey(name);
	}

	public static Object get(String name) {
		return Bars.get(name);
	}

	public static BarRun sendCargaThese(Player[] pl, String i, String j,
			long timeParty, boolean b, IFuncion iFuncion) {
		BarRun run = new BarRun(pl, i, j, timeParty, b, iFuncion);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(
				API.getInstance(), run, 10, 10);
		run.setId(id);
		return run;
	}

	public static void removeCargasAll() {
		Iterator<BarRun> it = runs.iterator();
		while (it.hasNext()) {
			it.next().finalizar();
		}
	}

	public static void finCarga(BarRun r) {
		runs.remove(r);
	}

}
