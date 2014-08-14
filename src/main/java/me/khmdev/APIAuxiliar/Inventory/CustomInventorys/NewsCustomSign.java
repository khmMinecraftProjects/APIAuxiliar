package me.khmdev.APIAuxiliar.Inventory.CustomInventorys;

import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;

public abstract class NewsCustomSign {
	
	public abstract CustomSign getSign(Sign event);

	public abstract boolean isthis(SignChangeEvent event);

	
}
