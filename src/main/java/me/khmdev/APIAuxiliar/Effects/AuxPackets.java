package me.khmdev.APIAuxiliar.Effects;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;


public class AuxPackets {
	public static void sendPacket(Player p, Object packet) {
		if (packet == null||p==null) {
			return;
		}
		try {
			Object entityPlayer = getHandle(p);
			Field con_field = entityPlayer.getClass().getField("playerConnection");
			Object con = con_field.get(entityPlayer);
			Method packet_method = getMethod(con.getClass(), "sendPacket");
			packet_method.invoke(con, packet);
		} catch (Exception e) {
			Bukkit.getLogger().warning(
					"Error al enviar packet "+ p.getName() + "!");
		}
	}
	public static Object getHandle(World world) {
		Object nms_entity = null;
		Method entity_getHandle = getMethod(world.getClass(), "getHandle");
		try {
			nms_entity = entity_getHandle.invoke(world);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return nms_entity;
	}
	public static Object getHandle(Entity entity) {
		Object nms_entity = null;
		Method entity_getHandle = getMethod(entity.getClass(), "getHandle");
		try {
			nms_entity = entity_getHandle.invoke(entity);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return nms_entity;
	}
	public static void sendPacketMore(Player[] p, Object packet) {
		if (packet == null||p==null) {
			return;
		}
		for (Player pl :p) {
		try {
			sendPacket(pl,packet);
		} catch (Exception e) {
			Bukkit.getLogger().warning(
					"Error al envial packet "+ pl.getName() + "!");
		}
		}
	}

	public static Object getClass(String name, Object... args)
			throws Exception {
		try {
			Class<?> c = Class.forName(getPackageName() + "." + name);
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
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void setValue(Object instance, String fieldName, Object value)
			throws Exception {
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(instance, value);
	}

	public static String getPackageName() {
		return "net.minecraft.server."
				+ Bukkit.getServer().getClass().getPackage().getName()
						.replace(".", ",").split(",")[3];
	}
    public static Method getMethod(Class<?> cl, String method) {
        for(Method m : cl.getMethods()) {
            if(m.getName().equals(method)) {
                return m;
            }
        }
        return null;
    }
}
