package me.khmdev.APIAuxiliar.Inventory.CustomInventorys;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TimerItem {
	long time, init;
	ItemStack item;
	Player player;

	public TimerItem(ItemStack itm, long tim, Player pl) {
		item = itm;
		item.setDurability((short) (7));
		player = pl;
		time = tim;
		init = System.currentTimeMillis();
	}

	public TimerItem(ItemStack itm, long tim) {
		item = itm;
		item.setDurability((short) (7));
		time = tim;
		init = System.currentTimeMillis();
	}

	public boolean setUses() {
		long pass = System.currentTimeMillis() - init;
				short dura=(short) (item.getType().getMaxDurability() - (((double) pass / time) * item
				.getType().getMaxDurability()));
				dura=dura<0? 0:dura;
			ItemStack[] ar=player.getInventory().getContents();
			for (int i = 0; i < ar.length; i++) {
				if(item.isSimilar(ar[i])){
					item=ar[i];
				}
			}
		if (pass >= time) {
			item.setDurability((short) 0);

			//player.getItemInHand().setDurability(dura);

			return true;
		}

		item.setDurability(dura);
		//player.getItemInHand().setDurability(dura);
		return false;
	}

}
