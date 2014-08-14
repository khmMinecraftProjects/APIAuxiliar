package me.khmdev.APIAuxiliar.Inventory.CustomInventorys;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

public class ReloadItem extends BukkitRunnable{
	private List<TimerItem> pila=new LinkedList<>();
	@SuppressWarnings("unused")
	private int id;
	@Override
	public void run() {
		for (int i = 0; i < pila.size(); i++) {
			if(pila.get(i).setUses()){
				pila.remove(i);
			}
		}
	}
	public void addTime(TimerItem it){
		pila.add(it);
	}
	public void setId(int idd) {
		id=idd;
	}
}
