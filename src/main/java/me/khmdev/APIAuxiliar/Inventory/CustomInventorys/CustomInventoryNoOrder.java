package me.khmdev.APIAuxiliar.Inventory.CustomInventorys;

import org.bukkit.event.inventory.InventoryClickEvent;

public class CustomInventoryNoOrder extends CustomInventory {
	public CustomInventoryNoOrder(String s) {
		super(s);
	}

	public void Request(InventoryClickEvent event) {

		for (CustomItem item : items) {
			if (item.isthis(event.getCurrentItem())) {
				if (item.hasPerms(event.getWhoClicked())) {
					item.execute(event);
				}
				return;
			}
		}

	}

}
