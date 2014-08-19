package me.khmdev.APIAuxiliar.Inventory.CustomInventorys;

import java.util.Stack;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CustomInventory {
	protected Stack<CustomItem> items;
	protected Inventory inventory;
	protected String name;
	protected int pos;

	public CustomInventory(String s) {
		name = s;
		inventory = Bukkit.getServer().createInventory(null, 9, name);
		pos = 0;
		items = new Stack<CustomItem>();
	}

	public void addItem(CustomItem item) {
		if (inventory.getSize() <= pos) {
			int len = inventory.getSize() + 9;
			ItemStack[] aux = inventory.getContents();

			inventory = Bukkit.getServer().createInventory(null, len, name);
			addItems(aux);

		}

		ItemStack[] aux = inventory.getContents();
		aux[pos] = item.getItem();
		pos++;
		inventory.setContents(aux);
		items.add(item);
	}

	public void Request(InventoryClickEvent event) {
		if (items == null || items.size() <= event.getSlot()) {
			return;
		}
		if (items.get(event.getSlot()) == null) {
			event.setCancelled(true);
			return;
		}
		CustomItem item = items.get(event.getSlot());
		if (item.hasPerms(event.getWhoClicked())) {
			item.execute(event);
		}

	}

	public void Request(InventoryDragEvent event) {
		event.setCancelled(true);
	}

	public void Destination(InventoryMoveItemEvent event) {
		event.setCancelled(true);
	}

	public void Initiator(InventoryMoveItemEvent event) {
		event.setCancelled(true);
	}

	protected void addItems(ItemStack[] items) {
		ItemStack[] inv = inventory.getContents();
		for (int i = 0; i < items.length; i++) {
			inv[i] = items[i];

		}
		inventory.setContents(inv);
	}

	public Stack<CustomItem> getItems() {
		return items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory i) {
		inventory = i;
	}

	public boolean isthis(Inventory inv) {
		return inventory.getName().equalsIgnoreCase(inv.getName());
	}

}
