package me.khmdev.APIAuxiliar.Effects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.World;

public class PlayFirework {
	private static Method world_getHandle = null;
	private static Method nms_world_broadcastEntityEffect = null;
	private static Method firework_getHandle = null;

	public static void playFirework(World world, Location loc, FireworkEffect fe) {

		Firework fw = (Firework) world.spawn(loc, Firework.class);

		Object nms_world = null;
		Object nms_firework = null;

		if (world_getHandle == null) {
			world_getHandle = AuxPackets.getMethod(world.getClass(),
					"getHandle");
			firework_getHandle = AuxPackets.getMethod(fw.getClass(),
					"getHandle");
		}

		try {
			nms_world = world_getHandle.invoke(world, (Object[]) null);

			nms_firework = firework_getHandle.invoke(fw, (Object[]) null);

			if (nms_world_broadcastEntityEffect == null) {
				nms_world_broadcastEntityEffect = AuxPackets.getMethod(
						nms_world.getClass(), "broadcastEntityEffect");
			}

			FireworkMeta data = (FireworkMeta) fw.getFireworkMeta();

			data.clearEffects();

			data.setPower(1);

			data.addEffect(fe);

			fw.setFireworkMeta(data);

			nms_world_broadcastEntityEffect.invoke(nms_world, new Object[] {
					nms_firework, (byte) 17 });
			fw.remove();
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void playRandomFirework(World world, Location loc) {
		Color c=randomColor();
		Color c2=randomColor();
		
		Type t=randomType();

		FireworkEffect effect = FireworkEffect.builder()
				.withColor(c).withFade(c2).with(t).build();

		playFirework(world, loc, effect);
	}

	private static Color randomColor() {
		int r = (int) (Math.random() * 15);
		switch (r) {
		case 0:
			return DyeColor.BLACK.getFireworkColor();
		case 1:

			return DyeColor.BLUE.getFireworkColor();
		case 2:

			return DyeColor.BROWN.getFireworkColor();
		case 3:
			return DyeColor.CYAN.getFireworkColor();
		case 4:
			return DyeColor.GRAY.getFireworkColor();
		case 5:
			return DyeColor.GREEN.getFireworkColor();
		case 6:
			return DyeColor.LIGHT_BLUE.getFireworkColor();
		case 7:
			return DyeColor.LIME.getFireworkColor();
		case 8:
			return DyeColor.MAGENTA.getFireworkColor();
		case 9:
			return DyeColor.ORANGE.getFireworkColor();
		case 10:
			return DyeColor.PINK.getFireworkColor();
		case 11:
			return DyeColor.PURPLE.getFireworkColor();
		case 12:
			return DyeColor.RED.getFireworkColor();
		case 13:
			return DyeColor.SILVER.getFireworkColor();
		case 14:
			return DyeColor.WHITE.getFireworkColor();
		default:
			return DyeColor.YELLOW.getFireworkColor();
		
		}
	}

	private static Type randomType() {
		int r = (int) (Math.random() * 4);
		switch (r) {
		case 0:
			return Type.BALL;
		case 1:
			return Type.BALL_LARGE;
		case 2:
			return Type.BURST;
		case 3:
			return Type.CREEPER;
		default:
			return Type.STAR;
		}
	}

}
