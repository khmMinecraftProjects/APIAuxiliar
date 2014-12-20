package me.khmdev.APIAuxiliar.Inventory;

import me.khmdev.APIAuxiliar.Players.AuxPlayer;

import org.bukkit.inventory.ItemStack;

public class InventoryBase {

	protected String name;
	protected ItemStack[] armor = new ItemStack[4];
	protected ItemStack[] inventory = new ItemStack[4 * 9];

	public InventoryBase(String s) {
		name = s;
	}

	public InventoryBase(String string, ItemStack[] armr, ItemStack[] inv) {
		name = string;
		if (armr != null) {
			for (int i = 0; i < armr.length; i++) {
				int pos = AuxPlayer.posArmor(armr[i]);
				if (pos != -1) {
					armor[pos] = armr[i];
				}
			}
		}
		if (inv != null) {
			if (inventory.length != inv.length) {
				for (int i = 0; i < inv.length; i++) {
					inventory[i] = inv[i];
				}
			} else {
				inventory = inv;
			}
		}
	}
	public InventoryBase(String string, ItemStack[] inv) {
		name = string;

		if (inv != null) {
			/*inventory=new ItemStack[inv.length];
			for (int i = 0; i < inv.length; i++) {
					inventory[i] = inv[i];
			}*/
			inventory=inv;
			
		}
	}


	public void setArmor(ItemStack[] ar) {
		armor = ar;
	}

	public void setInventory(ItemStack[] inv) {
		inventory = inv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemStack[] getArmor() {
		return armor;
	}

	public ItemStack[] getInventory() {
		return inventory;
	}
}
