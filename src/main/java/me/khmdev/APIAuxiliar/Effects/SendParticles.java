package me.khmdev.APIAuxiliar.Effects;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SendParticles {
	private static Object packet = null;

	public static void send(ParticleEffect effect, Location loc, int s, int a,
			Vector locB) {
		String name=effect.getName();
		try {
			if (packet==null){
				packet = getClass("PacketPlayOutWorldParticles");
				return;}
			setValue(packet, "a", name);
			setValue(packet, "b", (float) loc.getX());
			setValue(packet, "c", (float) loc.getY()+5);
			setValue(packet, "d", (float) loc.getZ());
			setValue(packet, "e", (float) locB.getX());
			setValue(packet, "f", (float) locB.getY());
			setValue(packet, "g", (float) locB.getZ());
			setValue(packet, "h", s);
			setValue(packet, "i", a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Player p : loc.getWorld().getPlayers()) {
			sendPacket(p, packet);
		}
	}

	private static void sendPacket(Player p, Object packet) {
		if (packet == null) {
			return;
		}
		try {
			Object entityPlayer = getMethod("getHandle",
					p.getClass(), 0).invoke(p);
			Object playerConnection = entityPlayer.getClass()
					.getField("playerConnection").get(entityPlayer);
			getMethod("sendPacket", playerConnection.getClass(),
					1).invoke(playerConnection, packet);
		} catch (Exception e) {
			Bukkit.getLogger().warning(
					"[ParticleEffect] Error al enviar particulas "
							+ p.getName() + "!");
		}
	}

	private static Object getClass(String name, Object... args) throws Exception {
		try{
		Class<?> c = Class
				.forName(getPackageName() + "." + name);
		int params = 0;
		if (args != null) {
			params = args.length;
		}
		for (Constructor<?> co : c.getConstructors()) {
			if (co.getParameterTypes().length == params) {
				return co.newInstance(args);
			}
		}
		return null;
		}catch(Exception e){
			return null;
		}
	}

	private static Method getMethod(String name, Class<?> c, int params) {
		for (Method m : c.getMethods()) {
			if (m.getName().equals(name)
					&& m.getParameterTypes().length == params) {
				return m;
			}
		}
		return null;
	}

	private static void setValue(Object instance, String fieldName, Object value)
			throws Exception {
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(instance, value);
	}

	private static String getPackageName() {
		return "net.minecraft.server."
				+ Bukkit.getServer().getClass().getPackage().getName()
						.replace(".", ",").split(",")[3];
	}

}
