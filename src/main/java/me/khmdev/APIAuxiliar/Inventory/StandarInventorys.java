package me.khmdev.APIAuxiliar.Inventory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import me.khmdev.APIAuxiliar.ConstantesAuxiliar;
import me.khmdev.APIBase.Almacenes.Almacen;

public class StandarInventorys {
	
	@SuppressWarnings("serial")
	private static HashMap<String, InventoryBase> inventorys=
				new HashMap<String, InventoryBase>()
			{{put(ConstantesAuxiliar.standar.getName(),ConstantesAuxiliar.standar);
				put("Vacio", new InventoryBase("Vacio"));
				put("Armor", new InventoryBase("Armor",
						
						new ItemStack[]{
						new ItemStack(Material.LEATHER_BOOTS),
		new ItemStack(Material.LEATHER_LEGGINGS),
		new ItemStack(Material.LEATHER_CHESTPLATE),
		new ItemStack(Material.LEATHER_HELMET)},
		new ItemStack[]{})
				);}};
				

	public static void addInventory(String s, InventoryConfig inv) {
		inventorys.put(s, inv);
	}

	public static boolean containInventory(String s) {
		return inventorys.containsKey(s);
	}

	public static InventoryBase getInventory(String s) {
		return inventorys.get(s);
	}
	
	
	public static void initAll(ConfigurationSection config) {

		if (config == null) {
			return;
		}
		Iterator<String> it = config.getKeys(false).iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (config.isConfigurationSection(key)) {
				ConfigurationSection subs = config.getConfigurationSection(key);
				InventoryConfig inv=new InventoryConfig(subs.getName());
				inv.initConf(subs);
				inventorys.put(inv.getName(), inv);
			}
		}

	}
	

	public static void initAll(Almacen almacen) {

		if (almacen == null) {
			return;
		}
		Iterator<Almacen> it = almacen.getAlmacenes().iterator();
		while (it.hasNext()) {
			Almacen alm = it.next();

			InventoryNBT inv = new InventoryNBT(alm.getName());
			inv.initAlmacen(alm);
			inventorys.put(inv.getName(), inv);
		}

	}

	public static void saveAll(Almacen nbt) {
		Iterator<Entry<String, InventoryBase>> it=
				inventorys.entrySet().iterator();
		while(it.hasNext()){
			InventoryNBT inv=getINBT(it.next().getValue());
			Almacen alm=nbt.getAlmacen(inv.getName());
			inv.saveAll(nbt);
			nbt.setAlmacen(alm.getName(), alm);
		}
	}
	private static InventoryNBT getINBT(InventoryBase inv){
		if(inv instanceof InventoryNBT){
			return (InventoryNBT)inv;
		}
		return new InventoryNBT(inv.getName(), inv.getArmor(),
				inv.getInventory());
	}
}
