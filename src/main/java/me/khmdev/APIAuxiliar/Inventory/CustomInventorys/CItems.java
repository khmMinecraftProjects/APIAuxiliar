package me.khmdev.APIAuxiliar.Inventory.CustomInventorys;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CItems {
	private static ReloadItem time;
	public static void initCItems(JavaPlugin apii){
		apii.getServer().getPluginManager()
		.registerEvents(new ListenItems(), apii);
		time=new ReloadItem();
		int idd = Bukkit.getScheduler().scheduleSyncRepeatingTask(
				apii,
				time, 0L, 20L);
		time.setId(idd);
	}
	
	public static void addTimer(ItemStack item,long tim){
		if(time==null){return;}
		time.addTime(new TimerItem(item, tim));
	}
	public static void addTimer(ItemStack item,long tim,Player pl){
		if(time==null){return;}
		time.addTime(new TimerItem(item, tim,pl));
	}
	private static List<CustomInventory> inventorys=
			new LinkedList<>();
			
	private static List<CustomItem> items=
			new LinkedList<>();	
			
	private static List<CustomSign> signs=
			new LinkedList<>();	
			
	private static List<NewsCustomSign> newSign=
			new LinkedList<>();
			
	public static List<CustomInventory> getInventorys() {
		return inventorys;
	}

	public static void addInventorys(CustomInventory inventory) {
		inventorys.add(inventory);
	}
	
	public static void removeInventory(String inv){
		inventorys.remove(inv);
	}

	public static CustomInventory getInventory(Inventory inventory) {
		Iterator<CustomInventory> it=inventorys.iterator();
		while(it.hasNext()){
			CustomInventory next=it.next();
			if(next.isthis(inventory)){
				return next;
			}
		}
		return null;
	}
	
	public static List<CustomItem> getItems() {
		return items;
	}

	public static void addItem(CustomItem item) {
		items.add(item);
	}
	
	public static void removeItem(String name){
		items.remove(name);
	}

	public static CustomItem getItem(ItemStack item) {
		Iterator<CustomItem> it=items.iterator();
		while(it.hasNext()){
			CustomItem next=it.next();
			if(next.isthis(item)){
				return next;
			}
		}
		return null;
	}
	
	public static void addSign(CustomSign item) {
		if(item==null){
			return;
		}
		signs.add(item);
	}
	
	public static void removeSign(Location name){
		signs.remove(name);
	}

	public static CustomSign getSign(Sign s) {
		Iterator<CustomSign> it=signs.iterator();
		while(it.hasNext()){
			CustomSign next=it.next();
			if(next.isthis(s)){
				return next;
			}
		}
		return null;
	}
	
	public static void addNewsSign(NewsCustomSign item) {
		if(item==null){
			return;
		}
		newSign.add(item);
	}
	
	public static void removeNewsSign(NewsCustomSign name){
		newSign.remove(name);
	}

	public static NewsCustomSign getNewsSign(SignChangeEvent event) {
		Iterator<NewsCustomSign> it=newSign.iterator();
		while(it.hasNext()){
			NewsCustomSign next=it.next();
			if(next.isthis(event)){
				return next;
			}
		}
		return null;
	}
}
