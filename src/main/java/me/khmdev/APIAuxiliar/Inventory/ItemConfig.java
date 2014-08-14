package me.khmdev.APIAuxiliar.Inventory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.khmdev.APIAuxiliar.ConstantesAuxiliar;
import me.khmdev.APIBase.Auxiliar.Auxiliar;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemConfig {
	private static final char SEPARATOR = ';';

	@SuppressWarnings("deprecation")
	public static ItemStack crearItem(ConfigurationSection section) {
		
		int id = Auxiliar.getNatural(
				Auxiliar.getSeparate(section.getName(), 0, SEPARATOR), -1);
		if (id == -1) {
			return null;
		}
		ItemStack item = new ItemStack(id);
		int amount = Auxiliar.getNatural(section.getString("Ammount"), 1);

		int data = Auxiliar.getNatural(section.getString("Data"), -1);

		if (data > -1) {
			item = new ItemStack(id, 1, (short) data);
		}

		List<String> en = section.getStringList("Enchatment");
		if (en != null) {
			Iterator<String> itE = en.iterator();

			while (itE.hasNext()) {
				String enc = itE.next();
				int ench = Auxiliar.getNatural(
						Auxiliar.getSeparate(enc, 0, SEPARATOR), -1);
				int nvl = Auxiliar.getNatural(
						Auxiliar.getSeparate(enc, 1, SEPARATOR), -1);
				if (ench != -1 && nvl != -1) {
					item.addEnchantment(Enchantment.getById(ench), nvl);
				}

			}
		}

		int color = Auxiliar.getNatural(section.getString("Color"), -1);
		if (color != -1 && DyeColor.values().length > color
				&& ConstantesAuxiliar.leather.contains(item.getType())) {

			DyeColor cl = DyeColor.values()[color];
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.setColor(cl.getColor());
			item.setItemMeta(meta);
		}

		List<String> lor = section.getStringList("Lore");
		List<String> lorf = new LinkedList<>();
		ItemMeta lr = item.getItemMeta();
		if (section.getString("Name", null) != null) {
			lr.setDisplayName(
					ChatColor.translateAlternateColorCodes('&',
							section.getString("Name")));
		}
		if (lor != null && lor.size() != 0) {
			Iterator<String> it = lor.iterator();
			while (it.hasNext()) {
				lorf.add(ChatColor.translateAlternateColorCodes('&', it.next()));
			}
		}
		lr.setLore(lorf);
		item.setItemMeta(lr);
		item.setAmount(amount);
		return item;
	}

	@SuppressWarnings("deprecation")
	public static void saveItem(ConfigurationSection section, ItemStack item) {

		if (item.getAmount() != 1) {
			section.set("Ammount", item.getAmount());
		}

		if (item.getData().getData() != 0) {
			section.set("Data", item.getData().getData());
		}

		Map<Enchantment, Integer> en = item.getEnchantments();
		List<String> ench = new LinkedList<String>();

		Iterator<Entry<Enchantment, Integer>> it = en.entrySet().iterator();

		while (it.hasNext()) {
			Entry<Enchantment, Integer> next = it.next();
			ench.add(next.getKey().getId() + ";" + next.getValue());
		}
		if (ench.size() != 0) {
			section.set("Enchatment", ench);
		}

		ItemMeta lr = item.getItemMeta();
		if (lr != null) {
			if (lr.getDisplayName() != null) {
				section.set("Name", lr.getDisplayName());
			}
			List<String> lore = lr.getLore();
			if (lore != null && lore.size() != 0) {
				section.set("Lore", lore);
			}

		}
		if (ConstantesAuxiliar.leather.contains(item.getType())) {

			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			DyeColor dye = DyeColor.getByColor(meta.getColor());
			if (dye != null) {
				int color = dye.getData();
				section.set("Color", color);
			}
		}

	}

}
