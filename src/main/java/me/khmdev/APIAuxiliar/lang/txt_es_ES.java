package me.khmdev.APIAuxiliar.lang;

import java.util.*;

public class txt_es_ES extends ListResourceBundle {
	public Object[][] getContents() {
		return contenido;
	}

	private Object[][] contenido = {
			{"APII.saveInventory","Almacenado inventario"},
			{"APII.noInventory","No existe inventario"},
			{"APII.setInventory","Establecido inventario"},
			{"APII.help","/equip  save/load (name)\n"},
			{"APII.NoValues","No se ha introducido valores"},
			{"APIPlayer.NoPlayer","Player incorrecto"},
			{"APIPlayer.help",
					"/apip <Command>\n"
							+ "Commands:     newBar (\"texto\")\n"
							+ "Commands:     newBarPlayer (Player) (\"texto\")\n"
							+ "Commands:     newBarAll (\"texto\")\n"
							+ "Commands:     removeBar\n"
							+ "Commands:     removeBarPlayer (Player)\n"
							+ "Commands:     removeBarAll\n" },
			{ "ListenerFreeze.wait", "Espere %time% segundos" },
			{ "init_disable",
					"%plugin% se desactivo debido ha que no encontro la API" },
			{ "CustomItem.NoPerms", "No tienes permisos %perms%" },  };

}