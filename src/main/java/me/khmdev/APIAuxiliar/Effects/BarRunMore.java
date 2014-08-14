package me.khmdev.APIAuxiliar.Effects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BarRunMore extends BukkitRunnable {
	private static final int ENTITY_ID = 1234;

	private Player[] pl;
	private int id;
	private long tempo, init = -1;
	private String txt, fin;
	private IFuncion func;
	private boolean primer = true,atras;

	public BarRunMore(Player[] p, String tt, String string, long msec,boolean atrs
											,IFuncion f) {
		atras=atrs;
		pl = p;
		txt = tt;
		fin = string;
		tempo = msec;
		func=f;
		if(atras){
			AuxPackets.sendPacketMore(pl, BossBar.createPacket(pl[0].getLocation(),txt, 200));
		}else{
			AuxPackets.sendPacketMore(pl, BossBar.createPacket(pl[0].getLocation(),txt, 0));
		}
	}

	@Override
	public void run() {
		if (init == -1) {
			init = System.currentTimeMillis();
		}
		long actual = System.currentTimeMillis();
		Object destroyEntityPacket = getDestroyEntityPacket();
		AuxPackets.sendPacketMore(pl, destroyEntityPacket);
		if (actual - init < tempo) {
			int h = (int) ((((actual - init) * 200) / tempo));

			Object packet = null;
			if(atras){
				AuxPackets.sendPacketMore(pl, BossBar.createPacket(pl[0].getLocation(),txt, 200-h));
			}else{
				AuxPackets.sendPacketMore(pl, BossBar.createPacket(pl[0].getLocation(),txt, h));
			}
			AuxPackets.sendPacketMore(pl, packet);
		} else {
			if (primer) {
				Object packet = null;
				if(atras){
					AuxPackets.sendPacketMore(pl, BossBar.createPacket(pl[0].getLocation(),fin, 0));
				}else{
					AuxPackets.sendPacketMore(pl, BossBar.createPacket(pl[0].getLocation(),fin, 200));
				}
				AuxPackets.sendPacketMore(pl, packet);

				primer = false;
			} else {
				if(func!=null){func.exec();}
				Bukkit.getScheduler().cancelTask(id);
			}
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}