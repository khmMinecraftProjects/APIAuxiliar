package me.khmdev.APIAuxiliar;

import java.util.Arrays;
import java.util.List;


import me.khmdev.APIAuxiliar.Inventory.InventoryBase;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ConstantesAuxiliar {

	public static final List<Material> boots=
			Arrays.asList(Material.CHAINMAIL_BOOTS,Material.IRON_BOOTS,Material.GOLD_BOOTS,
					Material.LEATHER_BOOTS,Material.DIAMOND_BOOTS),						
					chestplate=Arrays.asList(Material.CHAINMAIL_CHESTPLATE,Material.IRON_CHESTPLATE,Material.GOLD_CHESTPLATE,
									Material.LEATHER_CHESTPLATE,Material.DIAMOND_CHESTPLATE),
					helmet=Arrays.asList(Material.CHAINMAIL_HELMET,Material.IRON_HELMET,Material.GOLD_HELMET,
									Material.LEATHER_HELMET,Material.DIAMOND_HELMET),
					leggins=Arrays.asList(Material.CHAINMAIL_LEGGINGS ,Material.IRON_LEGGINGS,Material.GOLD_LEGGINGS,
									Material.LEATHER_LEGGINGS,Material.DIAMOND_LEGGINGS),
					leather=Arrays.asList(Material.LEATHER_BOOTS,Material.LEATHER_LEGGINGS,
									Material.LEATHER_CHESTPLATE,Material.LEATHER_HELMET);

	public static final InventoryBase standar=new InventoryBase("standar",
			new ItemStack[]{ new ItemStack(Material.LEATHER_BOOTS),
		new ItemStack(Material.LEATHER_LEGGINGS),
		new ItemStack(Material.LEATHER_CHESTPLATE),
		new ItemStack(Material.LEATHER_HELMET)},
		new ItemStack[]{new ItemStack(Material.IRON_SWORD), 
			new ItemStack(Material.BOW)
			,new ItemStack(
				Material.IRON_PICKAXE), new ItemStack(Material.IRON_SPADE),
				new ItemStack(Material.IRON_AXE), new ItemStack(Material.WOOL,
						64), new ItemStack(Material.WOOL, 64),
						new ItemStack(Material.ARROW, 64)});	
	
	
}
