package me.khmdev.APIAuxiliar.Inventory.CustomInventorys;

import org.bukkit.block.Sign;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class CustomSign{
	protected Sign sign;
	


	public Sign getSign() {
		return sign;
	}

	public void setSign(Sign sgn) {
		sign = sgn;
	}
	
	public abstract void execute(PlayerInteractEvent event);

	public abstract boolean isthis(Sign s);
}
