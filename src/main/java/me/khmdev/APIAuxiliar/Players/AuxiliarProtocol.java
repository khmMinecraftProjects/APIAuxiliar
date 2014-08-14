package me.khmdev.APIAuxiliar.Players;

import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;

public class AuxiliarProtocol {
	@SuppressWarnings("deprecation")
	public static void creaListen(JavaPlugin plug) {

		if(!NamesTags.APIprotocol){return;}
		ProtocolLibrary.getProtocolManager().addPacketListener(
				new PacketAdapter(plug, ConnectionSide.SERVER_SIDE,
						ListenerPriority.HIGH, new Integer[] { Integer
								.valueOf(20) }) {
					public void onPacketSending(PacketEvent event) {
						if (event.getPacketID() != 20) {
							return;
						}
						PacketContainer packetContainer = event.getPacket();
						try {
							String s = NamesTags.getName(event.getPlayer());
							packetContainer.getSpecificModifier(String.class)
									.write(0, s);
						} catch (FieldAccessException e) {
							e.printStackTrace();
						}
					}
				});
	}

	public static void removeListen(JavaPlugin pluging) {
		ProtocolLibrary.getProtocolManager().removePacketListeners(pluging);
	}
}
