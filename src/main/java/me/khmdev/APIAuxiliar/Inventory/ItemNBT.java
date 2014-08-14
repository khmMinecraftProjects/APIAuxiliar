package me.khmdev.APIAuxiliar.Inventory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.khmdev.APIAuxiliar.ConstantesAuxiliar;
import me.khmdev.APIBase.Almacenes.Almacen;
import me.khmdev.APIBase.Auxiliar.Auxiliar;

import org.bukkit.DyeColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemNBT {

	@SuppressWarnings("deprecation")
	public static ItemStack crearItem(Almacen almacen) {

		int id = almacen.getInteger("Id");
		if (id == -1) {
			return null;
		}
		int amount = Auxiliar.getNatural(almacen.getString("Ammount"), 1);

		int data = Auxiliar.getNatural(almacen.getString("Data"), -1);

		ItemStack item;
		if (data > -1) {
			item = new ItemStack(id, 1, (short) data);
		} else {
			item = new ItemStack(id);

		}


		String lore = almacen.getString("Lore");
		String linea = Auxiliar.getSeparate(lore, 0, '\n');
		List<String> lor = new LinkedList<String>();
		int i = 0;
		while (linea.length() != 0) {
			lor.add(linea);
			i++;
			linea = Auxiliar.getSeparate(lore, i, '\n');

		}
		ItemMeta lr = item.getItemMeta();
		
		
		int[] enc=almacen.getIntArray("Enchatment");
		int[] niv=almacen.getIntArray("Level");
		for (int o = 0; o < niv.length;o++) {

			lr.addEnchant(Enchantment.getById(enc[o]), niv[o], false);
			
		}
		
		if (lr != null) {
			lr.setLore(lor);
			item.setItemMeta(lr);
		}
		item.setAmount(amount);
		int color = Auxiliar.getNatural(almacen.getString("Color"), -1);
		if (color != -1 && DyeColor.values().length > color
				&& ConstantesAuxiliar.leather.contains(item.getType())) {

			DyeColor cl = DyeColor.values()[color];
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.setColor(cl.getColor());
			item.setItemMeta(meta);
		}
		return item;
	}

	@SuppressWarnings("deprecation")
	public static void saveItem(Almacen almacen, ItemStack item) {
		almacen.setInteger("Id", item.getTypeId());
		almacen.setString("Ammount", item.getAmount() + "");

		almacen.setString("Data", item.getData().getData() + "");

		Map<Enchantment, Integer> en = item.getEnchantments();
		int[] enc = new int[en.size()];
		int[] niv = new int[en.size()];
		Iterator<Entry<Enchantment, Integer>> it = item.getEnchantments()
											.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Entry<Enchantment, Integer> next = it.next();
			enc[i] = next.getKey().getId();
			niv[i] = next.getValue();
			i++;
		}
		almacen.setIntArray("Enchatment", enc);
		almacen.setIntArray("Level", niv);

		ItemMeta lr = item.getItemMeta();
		if (lr == null || lr.getLore() == null) {
			return;
		}
		Iterator<String> lore = lr.getLore().iterator();
		String lor = "";
		while (lore.hasNext()) {
			lor += lore.next() + "\n";
		}
		almacen.setString("Lore", lor);
		if (ConstantesAuxiliar.leather.contains(item.getType())) {

			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			DyeColor dye = DyeColor.getByColor(meta.getColor());
			if (dye != null) {
				int color = dye.getData();
				almacen.setString("Color", color + "");
			}
		}
	}
}
