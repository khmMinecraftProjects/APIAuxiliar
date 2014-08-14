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
	
	public static Object createPacket(Location l,String txt, int h) {
		
		Object packet = null;
		try {
			packet = AuxPackets.getClass("PacketPlayOutSpawnEntityLiving");
			if (packet == null) {
				return null;
			}
			AuxPackets.setValue(packet, "a", ENTITY_ID);
			AuxPackets.setValue(packet, "b", (byte) 63);
			AuxPackets.setValue(packet, "c", (int) Math.floor(l.getBlockX() * 32.0D));
			AuxPackets.setValue(packet, "d", (int) 0);
			AuxPackets.setValue(packet, "e", (int) Math.floor(l.getBlockZ() * 32.0D));
			AuxPackets.setValue(packet, "f", (byte) 0);
			AuxPackets.setValue(packet, "g", (byte) 0);
			AuxPackets.setValue(packet, "h", (byte) 0);
			AuxPackets.setValue(packet, "i", (byte) 0);
			AuxPackets.setValue(packet, "j", (byte) 0);
			AuxPackets.setValue(packet, "k", (byte) 0);
			DataWatcher watcher = createWatcher(txt, h);
			AuxPackets.setValue(packet, "l", watcher);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return packet;
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
		txt=reemplace(txt, pl.getName());

		Object packet = createPacket(pl.getLocation(),txt, Healt);
		AuxPackets.sendPacket(pl, packet);
		Bars.put(pl.getName(), packet);
	
		
	}
	private static String reemplace(String s,String pl){
		return s.replaceAll("%name%",pl);
	}
	public static BarRun sendCargaPlayer(Player pl,String ini,String fin,long tmp
										,Boolean atras,IFuncion f){
		ini=reemplace(ini, pl.getName());
		ini=reemplace(fin, pl.getName());

		BarRun run=new BarRun(pl, ini,fin, tmp,atras,f);
		int id=Bukkit.getScheduler().scheduleSyncRepeatingTask(API.getInstance(), 
				run, 10, 10);
		run.setId(id);
		return run;
	}
	
	public static BarRun sendCargaAll(String ini,String fin,long tmp,boolean atras
			,IFuncion f){
		
		BarRun run=new BarRun(Bukkit.getServer().getOnlinePlayers(),
				ini,fin, tmp,atras,f);
		int id=Bukkit.getScheduler().scheduleSyncRepeatingTask(API.getInstance(), 
				run, 10, 10);
		run.setId(id);
		return run;
	}
	public static void sendBarToAll(String txt, int Healt) {

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			Object packet = createPacket(player.getLocation(),txt, Healt);

			AuxPackets.sendPacket(player, packet);
			Bars.put(player.getName(), packet);

		}
	}
	
	public static void sendBarToGroup(String txt, int Healt,List<Player> pls) {

		for (Player player : pls) {
			Object packet = createPacket(player.getLocation(),txt, Healt);
			AuxPackets.sendPacket(player, packet);
			Bars.put(player.getName(), packet);

		}
	}
	private static Object getDestroyEntityPacket() {
		Object packet = null;
		try {
			packet = AuxPackets.getClass("PacketPlayOutEntityDestroy");
			AuxPackets.setValue(packet, "a", new int[] { ENTITY_ID });
		} catch (Exception e) {
			e.printStackTrace();
		}

		return packet;
	}

	
	

	public static void removeBarPlayer(Player pl) {
		Bars.remove(pl.getName());
		AuxPackets.sendPacket(pl, getDestroyEntityPacket());
	}

	public static void removeBarAll() {
		Iterator<Entry<String, Object>> it=Bars.entrySet().iterator();
		while(it.hasNext()) {
			String p=it.next().getKey();
			if(p!=null){
				@SuppressWarnings("deprecation")
				Player pl=Bukkit.getServer().getPlayer(p);
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
		BarRun run=new BarRun(pl, i,j, timeParty,b,iFuncion);
		int id=Bukkit.getScheduler().scheduleSyncRepeatingTask(API.getInstance(), 
				run, 10, 10);
		run.setId(id);	
		return run;
	}

	public static void removeCargasAll() {
		Iterator<BarRun> it=runs.iterator();
		while(it.hasNext()){
			it.next().finalizar();
		}
	}
	
	
	public static void finCarga(BarRun r) {
		runs.remove(r);
	}


}
