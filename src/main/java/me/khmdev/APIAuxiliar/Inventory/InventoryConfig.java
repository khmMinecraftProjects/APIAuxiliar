package me.khmdev.APIAuxiliar.Inventory;

import java.util.Iterator;

import me.khmdev.APIAuxiliar.Players.AuxPlayer;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class InventoryConfig extends InventoryBase {

	public InventoryConfig(String s) {
		super(s);
	}

	public InventoryConfig(String string, ItemStack[] inv) {
		super(string, inv);
	}

	public InventoryConfig(String string, ItemStack[] armr, ItemStack[] inv) {
		super(string, armr, inv);
	}

	protected void saveItem(ConfigurationSection section, ItemStack item) {
		ItemConfig.saveItem(section, item);
	}

	protected ItemStack getItem(ConfigurationSection section) {
		return ItemConfig.crearItem(section);
	}

	protected static final char SEPARATOR = ';';

	protected int pos = 0;

	public void initConf(ConfigurationSection subs) {
		if (subs.isConfigurationSection("Armor")) {
			initArmor(subs.getConfigurationSection("Armor"));
		}

		if (subs.isConfigurationSection("Inventory")) {
			initInventory(subs.getConfigurationSection("Inventory"));
		}
	}

	public void initArmor(ConfigurationSection section) {

		Iterator<String> it = section.getKeys(false).iterator();

		while (it.hasNext()) {
			String key = it.next();
			if (section.isConfigurationSection(key)) {
				ItemStack item = getItem(section.getConfigurationSection(key));
				if (item != null) {
					
					int posa = AuxPlayer.posArmor(item);
					if(section.getConfigurationSection(key)
							.isInt("Posicion")){
						int p=section.getConfigurationSection(key)
								.getInt("Posicion");
						if(p>=0&&p<4){posa=p;}
					}
					if (posa < 4 && posa >= 0) {
						armor[posa] = item;
					}else{
						armor[3] = item;
					}
				}
			}
		}
	}

	protected void addArmor(ConfigurationSection section) {
		ItemStack item = ItemConfig.crearItem(section);
		int posa = AuxPlayer.posArmor(item);
		armor[posa] = item;
	}

	public void initInventory(ConfigurationSection section) {

		Iterator<String> it = section.getKeys(false).iterator();

		while (it.hasNext()) {
			String key = it.next();

			if (section.isConfigurationSection(key)) {

				addItem(section.getConfigurationSection(key));

			}
		}
	}

	protected boolean addItem(ConfigurationSection section) {
		if (pos >= inventory.length) {
			return false;
		}

		ItemStack item = ItemConfig.crearItem(section);
		inventory[pos] = item;
		pos++;
		return true;
	}

	public ItemStack[] getArmor() {
		return armor;
	}

	public ItemStack[] getInventory() {
		return inventory;
	}

	public void setArmor(ItemStack[] ar) {
		armor = ar;
	}

	public void setInventory(ItemStack[] inv) {
		inventory = inv;
	}

	@SuppressWarnings("deprecation")
	public void saveInventory(ConfigurationSection section) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				ConfigurationSection sec;
				if (section.isConfigurationSection(inventory[i].getTypeId()
						+ "")) {
					int num=0;
					while(section
							.isConfigurationSection(inventory[i].getTypeId()
									+ ";"+num)){num++;}
					sec = section
							.createSection(inventory[i].getTypeId() + ";"+num);
				} else {
					sec = section
							.createSection(inventory[i].getTypeId() + "");
				}
				saveItem(sec, inventory[i]);
			}
		}
	}

	public void saveArmor(ConfigurationSection section) {
		for (int i = 0; i < armor.length; i++) {
			if (armor[i] != null) {
				@SuppressWarnings("deprecation")
				ConfigurationSection sec = section.createSection(String
						.valueOf(armor[i].getTypeId()));
				ItemConfig.saveItem(sec, armor[i]);
				saveItem(sec, armor[i]);
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void saveAll(ConfigurationSection conf) {
		ConfigurationSection invS = conf.createSection(name);
		saveArmor(invS.createSection("Armor"));
		saveInventory(invS.createSection("Inventory"));
	}
}
