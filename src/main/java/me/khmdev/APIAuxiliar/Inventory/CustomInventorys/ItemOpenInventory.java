package me.khmdev.APIAuxiliar.Inventory.CustomInventorys;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class ItemOpenInventory extends CustomItem {
	Inventory inventory;
	
	public ItemOpenInventory(ItemStack it, Inventory inv,String perms) {
		inventory = inv;
		item = it;
		permisos=perms;
	}
	public ItemOpenInventory(ItemStack it, Inventory inv) {
		inventory = inv;
		item = it;
	}

	@Override
	public void execute(InventoryClickEvent event) {
		event.setCancelled(true);
		if(inventory==null){event.getWhoClicked().closeInventory();return;}
		event.getWhoClicked().openInventory(inventory);
	}

	@Override
	public void execute(PlayerInteractEvent event) {
		event.setCancelled(true);	
		if(inventory==null){return;}
		event.getPlayer().openInventory(inventory);
	}

}
