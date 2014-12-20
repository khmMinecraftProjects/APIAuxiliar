package me.khmdev.APIAuxiliar.Players;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.khmdev.APIAuxiliar.Effects.BossBar;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CItems;
import me.khmdev.APIAuxiliar.ScoreBoard.runBoard;
import me.khmdev.APIBase.Auxiliar.Auxiliar;

public class APIPlayer {
	// private static HashMap<String, PlayerData> datas = new HashMap<>();
	private static ItemVisible enabler;

	public static ItemStack getEnabler() {
		return enabler.getItem();
	}

	public APIPlayer(JavaPlugin init) {
		NamesTags.initNamesTag(init);

		ItemStack item = new ItemStack(Material.GLASS_BOTTLE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Visivilidad");
		item.setItemMeta(meta);
		enabler = new ItemVisible(item);
		CItems.addItem(enabler);
		/*
		 * ScoreBoards
		 */
		runBoard run = new runBoard();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(
				init, run, 12, 12);
	}

	private String help() {
		String s = "";
		s += "/apip <Command>\n";
		s += "Commands:     newBar (\"texto\")\n";
		s += "Commands:     newBarPlayer (Player) (\"texto\")\n";
		s += "Commands:     newBarAll (\"texto\")\n";
		s += "Commands:     removeBar\n";
		s += "Commands:     removeBarPlayer (Player)\n";
		s += "Commands:     removeBarAll\n";
		return s;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (cmd.getName().equalsIgnoreCase("apip")) {
			if (args.length == 0) {
				sender.sendMessage(help());
				return true;
			}
			Player pl = sender instanceof Player?(Player)sender:null;
			if (args[0].equalsIgnoreCase("get")) {
				if (sender.getName().equalsIgnoreCase("CONSOLE")) {
					return true;
				}
				pl.getInventory().addItem(enabler.getItem());
				return true;

			}
			if (args[0].equalsIgnoreCase("newBar")) {
				if (sender.getName().equalsIgnoreCase("CONSOLE")) {
					return true;
				}

				if (args.length < 2) {
					sender.sendMessage(help());
					return true;
				}
				String txt = getText(args, 1, 0, 0);
				txt = ChatColor.translateAlternateColorCodes('&', txt);
				BossBar.sendBarToPlayer(pl, txt, 200);
				return true;

			}
			if (args[0].equalsIgnoreCase("newBarPlayer")) {

				if (args.length < 3) {
					sender.sendMessage(help());
					return true;
				}
				Player pl2 = Bukkit.getServer().getPlayer(args[1]);
				if (pl2 == null) {
					sender.sendMessage("Player incorrecto");
					return true;
				}
				String txt = getText(args, 1, 0, 0);
				txt = ChatColor.translateAlternateColorCodes('&', txt);
				BossBar.sendBarToPlayer(pl2, txt, 200);
				return true;

			}
			if (args[0].equalsIgnoreCase("newBarAll")) {

				if (args.length < 2) {
					sender.sendMessage(help());
					return true;
				}
				String txt = getText(args, 1, 0, 0);

				txt = ChatColor.translateAlternateColorCodes('&', txt);
				BossBar.sendBarToAll(txt, 200);
				return true;

			}

			if (args[0].equalsIgnoreCase("newCarga")) {
				if (sender.getName().equalsIgnoreCase("CONSOLE")) {
					return true;
				}

				if (args.length < 2) {
					sender.sendMessage(help());
					return true;
				}
				int tmp = Auxiliar.getNatural(args[1], -1);
				if (tmp < 0) {
					sender.sendMessage(help());
					return true;
				}
				String ini = getText(args, 2, 0, 0);
				String fin = getText(args, 2, 0, 1);
				fin = ChatColor.translateAlternateColorCodes('&', fin);

				ini = ChatColor.translateAlternateColorCodes('&', ini);
				BossBar.sendCargaPlayer(pl, ini, fin, tmp, false, null);
				return true;

			}
			if (args[0].equalsIgnoreCase("newCargaPlayer")) {

				if (args.length < 3) {
					sender.sendMessage(help());
					return true;
				}
				Player pl2 = Bukkit.getServer().getPlayer(args[1]);
				if (pl2 == null) {
					sender.sendMessage("Player incorrecto");
					return true;
				}
				int tmp = Auxiliar.getNatural(args[2], -1);
				if (tmp < 0) {
					sender.sendMessage(help());
					return true;
				}
				String ini = getText(args, 3, 0, 0);
				String fin = getText(args, 3, 0, 1);
				fin = ChatColor.translateAlternateColorCodes('&', fin);

				ini = ChatColor.translateAlternateColorCodes('&', ini);
				BossBar.sendCargaPlayer(pl, ini, fin, tmp, false, null);
				return true;

			}
			if (args[0].equalsIgnoreCase("newCargaAll")) {

				if (args.length < 2) {
					sender.sendMessage(help());
					return true;
				}
				int tmp = Auxiliar.getNatural(args[1], -1);
				if (tmp < 0) {
					sender.sendMessage(help());
					return true;
				}
				String ini = getText(args, 2, 0, 0);
				String fin = getText(args, 2, 0, 1);
				fin = ChatColor.translateAlternateColorCodes('&', fin);

				ini = ChatColor.translateAlternateColorCodes('&', ini);

				BossBar.sendCargaAll(ini, fin, tmp, false, null);
				return true;
			}
			if (args[0].equalsIgnoreCase("newCuenta")) {
				if (sender.getName().equalsIgnoreCase("CONSOLE")) {
					return true;
				}

				if (args.length < 2) {
					sender.sendMessage(help());
					return true;
				}
				int tmp = Auxiliar.getNatural(args[1], -1);
				if (tmp < 0) {
					sender.sendMessage(help());
					return true;
				}
				String ini = getText(args, 2, 0, 0);
				String fin = getText(args, 2, 0, 1);
				fin = ChatColor.translateAlternateColorCodes('&', fin);

				ini = ChatColor.translateAlternateColorCodes('&', ini);
				BossBar.sendCargaPlayer(pl, ini, fin, tmp, true, null);
				return true;

			}
			if (args[0].equalsIgnoreCase("newCuentaPlayer")) {

				if (args.length < 3) {
					sender.sendMessage(help());
					return true;
				}
				Player pl2 = Bukkit.getServer().getPlayer(args[1]);
				if (pl2 == null) {
					sender.sendMessage("Player incorrecto");
					return true;
				}
				int tmp = Auxiliar.getNatural(args[2], -1);
				if (tmp < 0) {
					sender.sendMessage(help());
					return true;
				}
				String ini = getText(args, 3, 0, 0);
				String fin = getText(args, 3, 0, 1);
				fin = ChatColor.translateAlternateColorCodes('&', fin);

				ini = ChatColor.translateAlternateColorCodes('&', ini);
				BossBar.sendCargaPlayer(pl, ini, fin, tmp, true, null);
				return true;

			}
			if (args[0].equalsIgnoreCase("newCuentaAll")) {

				if (args.length < 2) {
					sender.sendMessage(help());
					return true;
				}
				int tmp = Auxiliar.getNatural(args[1], -1);
				if (tmp < 0) {
					sender.sendMessage(help());
					return true;
				}
				String ini = getText(args, 2, 0, 0);
				String fin = getText(args, 2, 0, 1);

				ini = ChatColor.translateAlternateColorCodes('&', ini);
				fin = ChatColor.translateAlternateColorCodes('&', fin);

				BossBar.sendCargaAll(ini, fin, tmp, true, null);
				return true;
			}

			if (args[0].equalsIgnoreCase("removeBar")) {
				if (sender.getName().equalsIgnoreCase("CONSOLE")) {
					return true;
				}
				BossBar.removeBarPlayer(pl);
				return true;

			}
			if (args[0].equalsIgnoreCase("removeBarPlayer")) {
				if (args.length < 2) {
					sender.sendMessage(help());
					return true;
				}
				Player pl2 = Bukkit.getServer().getPlayer(args[1]);
				if (pl2 == null) {
					sender.sendMessage("Player incorrecto");
					return true;
				}
				BossBar.removeBarPlayer(pl2);
				return true;

			}
			if (args[0].equalsIgnoreCase("removeBarAll")) {
				BossBar.removeBarAll();
				return true;

			}
			sender.sendMessage(help());
			return true;
		}

		return false;
	}

	private static final char SEPARATOR = '"';

	private String getText(String[] txt, int ini, int fin, int num) {
		String s = "";
		boolean init = false;
		int pos = 0;
		for (int i = ini; i < txt.length - fin; i++) {
			if (!init) {
				if (txt[i].startsWith(SEPARATOR + "")) {
					if (txt[i].endsWith("" + SEPARATOR)) {
						s += txt[i].substring(1, txt[i].length() - 1);
						if (num == pos) {
							return s;
						}
						init = false;
						pos++;
						s = "";
					} else {
						s += txt[i].substring(1) + " ";
						init = true;
					}
				}
			} else {
				if (txt[i].endsWith("" + SEPARATOR)) {

					s += txt[i].substring(0, txt[i].length() - 1);
					if (num == pos) {
						return s;
					}
					init = false;
					pos++;
					s = "";
				} else {
					s += txt[i] + " ";
				}
			}
		}
		if (num == pos) {
			return s;
		}
		return "";
	}
	/*
	 * @Override public void cargar(Almacen nbt) { Iterator<Almacen> it =
	 * nbt.getAlmacenes().iterator(); while (it.hasNext()) { Almacen next =
	 * it.next(); PlayerData dat = new PlayerData(); dat.cargar(next); } }
	 * 
	 * @Override public void guardar(Almacen nbt) { Iterator<Entry<String,
	 * PlayerData>> it = datas.entrySet().iterator(); while (it.hasNext()) {
	 * Entry<String, PlayerData> next = it.next(); nbt.escribir(next.getValue(),
	 * next.getKey()); } }
	 * 
	 * public static PlayerData getData(String player) { PlayerData d =
	 * datas.get(player); return d == null ? new PlayerData() : d; }
	 * 
	 * public static void setData(String player, PlayerData dat) {
	 * datas.put(player, dat); }
	 */

}
