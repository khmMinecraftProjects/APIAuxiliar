package me.khmdev.APIAuxiliar;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ListenAuxiliar implements Listener {
	private static List<ItemStack> noDrop=new LinkedList<>();

	private static final boolean limit=true;
    @EventHandler
    public void onItemDrop (PlayerDropItemEvent e) {
    	
    	if(e.getPlayer().hasPermission("HubPlugin.dropItem")
    			){
    		return;
    	}
    	if(limit){e.setCancelled(true);}
    	ItemStack itm=e.getItemDrop().getItemStack();
    	if(itm.getAmount()!=1){itm=itm.clone();itm.setAmount(1);}
    	if(noDrop.contains(itm)){
    		e.setCancelled(true);
    	}
    }
    
	@EventHandler
    public void death(PlayerDeathEvent e) {
		for(ItemStack it:noDrop){
			e.getDrops().remove(it);
		}
	}

}