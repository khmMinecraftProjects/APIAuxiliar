package me.khmdev.APIAuxiliar.Inventory.CustomInventorys;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CustomItemNULL extends CustomItem{
	public CustomItemNULL(){
		super(new ItemStack(Material.AIR));
	}
	public CustomItemNULL(ItemStack it){
		super(it);
	}
	@Override
	public void execute(InventoryClickEvent event) {
		
	}

	@Override
	public void execute(PlayerInteractEvent event) {
		
	}

}
