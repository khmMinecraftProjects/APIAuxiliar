package me.khmdev.APIAuxiliar.Inventory;

import java.util.Iterator;

import me.khmdev.APIAuxiliar.Players.AuxPlayer;
import me.khmdev.APIBase.Almacenes.Almacen;
import me.khmdev.APIBase.Auxiliar.Auxiliar;


import org.bukkit.inventory.ItemStack;


public class InventoryNBT extends InventoryBase {
	public InventoryNBT(String s) {
		super(s);
	}
	public InventoryNBT(String string, ItemStack[] inv) {
		super(string, inv);
	}

	public InventoryNBT(String string, ItemStack[] armr, ItemStack[] inv) {
		super(string, armr, inv);
	}

	private int pos = 0;

	public void initAlmacen(Almacen almacen) {
		if (almacen.hasKey("Armor")) {

			initArmor(almacen.getAlmacen("Armor"));
		}

		if (almacen.hasKey("Inventory")) {
			initInventory(almacen.getAlmacen("Inventory"));
		}
	}

	private void initArmor(Almacen almacen) {

		Iterator<Almacen> it = almacen.getAlmacenes().iterator();

		while (it.hasNext()) {
			Almacen alm = it.next();

			addArmor(alm);

		}
	}

	private void addArmor(Almacen almacen) {
/*
		int id = Auxiliar.getNatural(almacen.getName(), -1);
		if (id == -1) {
			return;
		}
		ItemStack item = new ItemStack(id);
		int posa = AuxPlayer.posArmor(item);
		if (pos == -1) {
			return;
		}

		int data = Auxiliar.getNatural(almacen.getString("Data"), -1);

		if (data > -1) {
			item = new ItemStack(id, 1, (short) data);
		}

		int[] en = almacen.getIntArray("Enchatment");
		int[] niv = almacen.getIntArray("Level");

		if (en != null && niv != null) {

			for (int i = 0; i < niv.length && i < en.length; i++) {

				int ench = en[i];
				int nvl = niv[i];
				if (ench != -1 && nvl != -1) {
					item.addEnchantment(Enchantment.getById(ench), nvl);
				}

			}
		}

		int color = Auxiliar.getNatural(almacen.getString("Color"), -1);
		if (color != -1 && DyeColor.values().length > color
				&& Constantes.leather.contains(item.getType())) {

			DyeColor cl = DyeColor.values()[color];
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.setColor(cl.getColor());
			item.setItemMeta(meta);
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
		lr.setLore(lor);
		item.setItemMeta(lr);*/
		ItemStack item = ItemNBT.crearItem(almacen);
		int posa = AuxPlayer.posArmor(item);
		armor[posa] = item;
	}
	private void addSizeInventory(){
		ItemStack[] nuevo=new ItemStack[inventory.length+9];
		for (int i = 0; i < inventory.length; i++) {
			nuevo[i]=inventory[i];
		}
		inventory=nuevo;
	}

	public void initInventory(Almacen almacen) {
		if(inventory==null){
			inventory=new ItemStack[9];
		}
		Iterator<Almacen> it = almacen.getAlmacenes().iterator();

		while (it.hasNext()) {
			Almacen alm = it.next();

			addItem(alm);

		}
	}

	private void addItem(Almacen almacen) {
		int n=Auxiliar.getNatural(almacen.getName(),-1);
		if(n==-1){n=pos;}
		if(n>=inventory.length){
			addSizeInventory();
		}
		/*
		int id = almacen.getInteger("Id");
		if (id == -1) {
			return;
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
		if(lr!=null){
		lr.setLore(lor);
		item.setItemMeta(lr);
		}
		item.setAmount(amount);*/
		ItemStack item = ItemNBT.crearItem(almacen);
		
		inventory[n] = item;
		
		pos++;
	}

	public void saveInventory(Almacen almacen) {
		almacen.clear();

		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				Almacen sec = almacen.getAlmacen(i+"");
				saveItem(sec, inventory[i]);
				almacen.setAlmacen(sec.getName(), sec);
			}
		}

	}

	private void saveItem(Almacen almacen, ItemStack item) {
		/*
		almacen.setInteger("Id", item.getTypeId());
		almacen.setString("Ammount", item.getAmount() + "");

		almacen.setString("Data", item.getData().getData() + "");

		Map<Enchantment, Integer> en = item.getEnchantments();
		int[] enc = new int[en.size()];
		int[] niv = new int[en.size()];

		Iterator<Entry<Enchantment, Integer>> it = en.entrySet().iterator();
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
		if(lr==null||lr.getLore()==null){return;}
		Iterator<String> lore = lr.getLore().iterator();
		String lor = "";
		while (lore.hasNext()) {
			lor += lore.next() + "\n";
		}
		almacen.setString("Lore", lor);*/
		ItemNBT.saveItem(almacen,item);
	}

	private void saveArmor(Almacen almacen) {
		for (int i = 0; i < armor.length; i++) {
			if (armor[i] != null) {
				@SuppressWarnings("deprecation")
				Almacen sec = almacen.getAlmacen(String.valueOf(armor[i]
						.getTypeId()));
				saveItemArmor(sec, armor[i]);
				almacen.setAlmacen(sec.getName(), sec);

			}
		}
	}

	private void saveItemArmor(Almacen almacen, ItemStack item) {
		/*
		if (item.getData().getData() != 0) {
			almacen.setString("Data", item.getData().getData() + "");
		}

		Map<Enchantment, Integer> en = item.getEnchantments();
		int[] enc = new int[en.size()];
		int[] niv = new int[en.size()];

		Iterator<Entry<Enchantment, Integer>> it = en.entrySet().iterator();
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
		Iterator<String> lore = lr.getLore().iterator();
		String lor = "";
		while (lore.hasNext()) {
			lor += lore.next() + "\n";
		}
		almacen.setString("Lore", lor);

		if (Constantes.leather.contains(item.getType())) {

			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			DyeColor dye = DyeColor.getByColor(meta.getColor());
			if (dye != null) {
				int color = dye.getData();
				almacen.setString("Color", color + "");
			}
		}
*/
		ItemNBT.saveItem(almacen,item);

	}

	public void saveAll(Almacen almacen) {
	
		if (armor != null) {
			Almacen alm = almacen.getAlmacen("Armor");
			saveArmor(almacen.getAlmacen("Armor"));
			almacen.setAlmacen(alm.getName(), alm);
		}
		if (inventory != null) {
			Almacen alm = almacen.getAlmacen("Inventory");
			saveInventory(almacen.getAlmacen("Inventory"));
			almacen.setAlmacen(alm.getName(), alm);
		}
	}
}
