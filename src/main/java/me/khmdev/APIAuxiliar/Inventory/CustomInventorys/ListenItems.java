package me.khmdev.APIAuxiliar.Inventory.CustomInventorys;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ListenItems implements Listener {

	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		CustomInventory inv = CItems.getInventory(event.getInventory());
		if (inv != null) {
			if (event.isShiftClick()) {
				event.setCancelled(true);
				return;
			}

			if (event.getSlotType() != SlotType.CONTAINER
					|| event.getRawSlot() > inv.getInventory().getSize() - 1) {
				return;
			}
			event.setCancelled(true);

			inv.Request(event);
		}
	}
	@EventHandler
	public void placeBlock(BlockPlaceEvent event) {
		CustomItem item = CItems.getItem(event.getItemInHand());
		if (item == null) {
			return;
		}
		  event.setCancelled(true);
		if(item.hasPerms(event.getPlayer())){item.execute(event);}
		}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled()
				&&event.getAction()!=Action.RIGHT_CLICK_AIR){return;}
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
				|| event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

			Block b = event.getClickedBlock();
			if (b.getType() == Material.SIGN_POST
					|| b.getType() == Material.WALL_SIGN) {
				CustomSign sign = CItems.getSign((Sign) b.getState());
				if (sign != null) {
					sign.execute(event);
				}
			}
		}

		if (event.getAction() != Action.RIGHT_CLICK_AIR
				&& event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		ItemStack it = event.getPlayer().getItemInHand();
		if (it == null || it.getItemMeta() == null) {
			return;
		}
		CustomItem item = CItems.getItem(it);
		if (item == null) {
			return;
		}
		  
		if(item.hasPerms(event.getPlayer())){item.execute(event);}
	}


	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent event) {
		if (event.isCancelled()){return;}
		ItemStack it = event.getPlayer().getItemInHand();
		if (it == null || it.getItemMeta() == null) {
			return;
		}
		CustomItem item = CItems.getItem(it);
		if (item == null) {
			return;
		}
		  
		if(item.hasPerms(event.getPlayer())){item.execute(event);}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void inventory(InventoryDragEvent event) {
		CustomInventory inv = CItems.getInventory(event.getInventory());
		if (inv != null) {
			inv.Request(event);
		}
	}

	@EventHandler
	public void inventory(InventoryMoveItemEvent event) {

		CustomInventory inv = CItems.getInventory(event.getDestination());
		if (inv != null) {
			inv.Destination(event);
		}
		inv = CItems.getInventory(event.getInitiator());
		if (inv != null) {
			inv.Initiator(event);
		}
	}

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		NewsCustomSign ns = CItems.getNewsSign(event);
		if (ns != null) {
			CItems.addSign(ns.getSign((Sign) event.getBlock().getState()));
		}
	}
}
