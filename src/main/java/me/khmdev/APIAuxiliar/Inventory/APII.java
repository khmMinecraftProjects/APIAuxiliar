package me.khmdev.APIAuxiliar.Inventory;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CItems;
import me.khmdev.APIAuxiliar.lang.Lang;
import me.khmdev.APIBase.Almacenes.Almacen;
import me.khmdev.APIBase.Almacenes.Datos;
import me.khmdev.APIBase.Almacenes.local.ConfigFile;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class APII implements Datos{
	
	private ConfigFile Conf;

	public APII(JavaPlugin init){
		CItems.initCItems(init);

		Conf = new ConfigFile(init.getDataFolder(), "inventarios");
		StandarInventorys.initAll(Conf.getConfig());
		
		//API.getInstance().getCentral().insertar(this);

	}
	private String help() {
		return Lang.get("APII.help");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!cmd.getName().equalsIgnoreCase("equipar")) {
			return false;
		}
		if(args.length==0){
			sender.sendMessage(help());
			return true;
		}
		if (args[0].equals("load")) {
			if (args.length < 2) {
				sender.sendMessage(Lang.get("APII.NoValues"));
				return true;
			}

			String r = args[1];
			
			Player pl = sender instanceof Player?(Player)sender:null;
			if(pl==null||!setInventory(pl,r)){return true;};

			sender.sendMessage(Lang.get("APII.setInventory"));
			
			return true;
		}
		if (args[0].equals("save")) {
			if (args.length < 2) {
				sender.sendMessage(Lang.get("APII.NoValues"));
				return true;
			}

			String r = args[1];

			Player pl = sender instanceof Player?(Player)sender:null;
			addInventory( pl, r);
			
			sender.sendMessage(Lang.get("APII.saveInventory"));

			return true;
		}
		sender.sendMessage(help());
		return true;
	}
	public boolean setInventory(Player pl,String r){
		if (!StandarInventorys.containInventory(r)) {
			pl.sendMessage(Lang.get("APII.noInventory"));
			return false;
		}
		InventoryBase inv = StandarInventorys.getInventory(r);
		pl.getInventory().setArmorContents(inv.getArmor());
		pl.getInventory().setContents(inv.getInventory());
		return true;

	}

	public void addInventory(Player pl,String r){
		PlayerInventory inve = (PlayerInventory) pl.getInventory();
		InventoryConfig inv = new InventoryConfig(r,
				inve.getArmorContents(), inve.getContents());
		inv.saveAll(Conf.getConfig());
		Conf.saveConfig();
		StandarInventorys.addInventory(r, inv);

	}
	@Override
	public void cargar(Almacen nbt) {
		Almacen alm=nbt.getAlmacen("Inventarios");
		StandarInventorys.initAll(alm);
		StandarInventorys.initAll(Conf.getConfig());
	}
	@Override
	public void guardar(Almacen nbt) {
		Almacen alm=nbt.getAlmacen("Inventarios");
		StandarInventorys.saveAll(alm);
		nbt.setAlmacen("Inventarios", alm);
	}
}
