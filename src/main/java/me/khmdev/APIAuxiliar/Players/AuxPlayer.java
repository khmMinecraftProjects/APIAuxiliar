package me.khmdev.APIAuxiliar.Players;

import java.util.LinkedList;
import java.util.List;

import me.khmdev.APIAuxiliar.ConstantesAuxiliar;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AuxPlayer {
	public static boolean isPossibleSpawn(World w, int x, int y, int z) {
		return (w.getBlockAt(x, y, z).getType() == Material.AIR
				&& w.getBlockAt(x, y + 1, z).getType() == Material.AIR
				&& w.getBlockAt(x, y - 1, z).getType() != Material.AIR

				&& w.getBlockAt(x + 1, y + 1, z).getType() == Material.AIR
				&& w.getBlockAt(x + 1, y, z).getType() == Material.AIR
				// && w.getBlockAt(x+1, y-1, z).getType() == Material.AIR

				&& w.getBlockAt(x - 1, y + 1, z).getType() == Material.AIR
				&& w.getBlockAt(x - 1, y, z).getType() == Material.AIR
				// && w.getBlockAt(x-1, y-1, z).getType() == Material.AIR

				&& w.getBlockAt(x, y + 1, z - 1).getType() == Material.AIR
				&& w.getBlockAt(x, y, z - 1).getType() == Material.AIR
				// && w.getBlockAt(x, y-1, z-1).getType() != Material.AIR

				&& w.getBlockAt(x, y + 1, z + 1).getType() == Material.AIR && w
				.getBlockAt(x, y, z + 1).getType() == Material.AIR);// &&
																	// w.getBlockAt(x,
																	// y-1,
																	// z+1).getType()
																	// ==
																	// Material.AIR);
	}

	private static final int max = 40;

	public static ItemStack addDescription(ItemStack it, String... s) {
		if (it == null) {
			return it;
		}

		ItemMeta meta = it.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) {
			lore = new LinkedList<>();
		}

		for (int i = 0; i < s.length; i++) {
			String lor =ChatColor.translateAlternateColorCodes('&', s[i]);

			while (lor.length() != 0) {
				int m = Math.min(max, lor.length());
				String aux = lor.substring(0, m);
				lor = lor.substring(m);
				lore.add(aux);
			}

		}
		meta.setLore(lore);
		it.setItemMeta(meta);
		return it;
	}

	public static int posArmor(ItemStack it) {
		if (it == null) {
			return -1;
		}
		Material mat = it.getType();
		if (ConstantesAuxiliar.boots.contains(mat)) {
			return 0;
		} else if (ConstantesAuxiliar.leggins.contains(mat)) {
			return 1;
		} else if (ConstantesAuxiliar.chestplate.contains(mat)) {
			return 2;
		} else if (ConstantesAuxiliar.helmet.contains(mat)) {
			return 3;
		}
		return -1;
	}

	public static ItemStack getItem(Material m, String name, String... params) {
		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		item.setItemMeta(meta);
		item=addDescription(item,params);

		return item;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getItem(Material m, String name, int id,
			String... params) {
		ItemStack item = new ItemStack(m, 1, (short)1,(byte) id);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		item.setItemMeta(meta);
		item=addDescription(item,params);

		return item;
	}
}
