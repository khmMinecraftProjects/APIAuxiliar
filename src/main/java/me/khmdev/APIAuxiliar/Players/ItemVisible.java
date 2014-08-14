package me.khmdev.APIAuxiliar.Players;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;

public class ItemVisible extends CustomItem{
	public static ItemStack vis,inv;
	private long timeout=5000;
	public ItemVisible(ItemStack it){
		vis=AuxPlayer.getItem(Material.SLIME_BALL, "&AJugadores visibles"
				, "");
		inv=AuxPlayer.getItem(Material.MAGMA_CREAM, "&CJugadores invisibles"
				, "");
		item=vis;
	}
	@Override
	public void execute(InventoryClickEvent event) {
		
	}
	public static HashMap<String, Long> times=new HashMap<>();
	@SuppressWarnings("deprecation")
	@Override
	public void execute(PlayerInteractEvent event) {
		String name=event.getPlayer().getName();
		if(times.containsKey(name)){
			if((System.currentTimeMillis()
					-times.get(name))<timeout){
			event.getPlayer().sendMessage("Aun no se ha recargado el item");
			return;
			}else{
				times.remove(name);
			}
		}	
		
		if(ListenVisible.containNoView(event.getPlayer().getName())){
			VisiblesPlayer.resetearJugadores(event.getPlayer());
			ListenVisible.removeNoView(event.getPlayer().getName());
			event.getPlayer().sendMessage("Todos los jugadores ahora son visibles");

			event.getPlayer().setItemInHand(vis);
			event.getPlayer().updateInventory();

		}else{
			VisiblesPlayer.esconderJugadores(event.getPlayer());
			ListenVisible.addNoView(event.getPlayer().getName());
			event.getPlayer().sendMessage("Todos los jugadores ahora son invisibles");

			event.getPlayer().setItemInHand(inv);
			event.getPlayer().updateInventory();

		}
		times.put(name, System.currentTimeMillis());
	}
	public boolean isthis(ItemStack it){
		return inv.isSimilar(it)||vis.isSimilar(it);
	}

}
