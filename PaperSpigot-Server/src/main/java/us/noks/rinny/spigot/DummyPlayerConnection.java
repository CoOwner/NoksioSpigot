package us.noks.rinny.spigot;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EnumProtocol;
import net.minecraft.server.IChatBaseComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet;
import net.minecraft.server.PacketPlayInAbilities;
import net.minecraft.server.PacketPlayInArmAnimation;
import net.minecraft.server.PacketPlayInBlockDig;
import net.minecraft.server.PacketPlayInBlockPlace;
import net.minecraft.server.PacketPlayInChat;
import net.minecraft.server.PacketPlayInClientCommand;
import net.minecraft.server.PacketPlayInCloseWindow;
import net.minecraft.server.PacketPlayInCustomPayload;
import net.minecraft.server.PacketPlayInEnchantItem;
import net.minecraft.server.PacketPlayInEntityAction;
import net.minecraft.server.PacketPlayInFlying;
import net.minecraft.server.PacketPlayInHeldItemSlot;
import net.minecraft.server.PacketPlayInKeepAlive;
import net.minecraft.server.PacketPlayInSetCreativeSlot;
import net.minecraft.server.PacketPlayInSettings;
import net.minecraft.server.PacketPlayInSteerVehicle;
import net.minecraft.server.PacketPlayInTabComplete;
import net.minecraft.server.PacketPlayInTransaction;
import net.minecraft.server.PacketPlayInUpdateSign;
import net.minecraft.server.PacketPlayInUseEntity;
import net.minecraft.server.PacketPlayInWindowClick;
import net.minecraft.server.PlayerConnection;
import net.minecraft.server.WorldServer;

public class DummyPlayerConnection extends PlayerConnection {
	
	private boolean disconnected = false;

	public DummyPlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager,
			EntityPlayer entityplayer) {
		super(minecraftserver, networkmanager, entityplayer);
	}

	public CraftPlayer getPlayer() {
		return this.player == null ? null : this.player.getBukkitEntity();
	}

	public void a() {
	}

	public NetworkManager b() {
		return this.networkManager;
	}

	public void disconnect(String s) {
		WorldServer worldserver = this.player.r();

		worldserver.kill(this.player);
		worldserver.getPlayerChunkMap().removePlayer(this.player);
		((CraftServer) Bukkit.getServer()).getHandle().players.remove(this.player);
		this.disconnected = true;
	}

	public void a(PacketPlayInSteerVehicle packetplayinsteervehicle) {
	}

	public void a(PacketPlayInFlying packetplayinflying) {
	}

	public void a(double d0, double d1, double d2, float f, float f1) {
	}

	public void teleport(Location dest) {
	}

	public void a(PacketPlayInBlockDig packetplayinblockdig) {
	}

	public void a(PacketPlayInBlockPlace packetplayinblockplace) {
	}

	public void a(IChatBaseComponent ichatbasecomponent) {
	}

	public void sendPacket(Packet packet) {
	}

	public void sendPacketProtocolHack(Packet packet) {
	}

	public void a(PacketPlayInHeldItemSlot packetplayinhelditemslot) {
	}

	public void a(PacketPlayInChat packetplayinchat) {
	}

	public void chat(String s, boolean async) {
	}

	public void a(PacketPlayInArmAnimation packetplayinarmanimation) {
	}

	public void a(PacketPlayInEntityAction packetplayinentityaction) {
	}

	public void a(PacketPlayInUseEntity packetplayinuseentity) {
	}

	public void a(PacketPlayInClientCommand packetplayinclientcommand) {
	}

	public void a(PacketPlayInCloseWindow packetplayinclosewindow) {
	}

	public void a(PacketPlayInWindowClick packetplayinwindowclick) {
	}

	public void a(PacketPlayInEnchantItem packetplayinenchantitem) {
	}

	public void a(PacketPlayInSetCreativeSlot packetplayinsetcreativeslot) {
	}

	public void a(PacketPlayInTransaction packetplayintransaction) {
	}

	public void a(PacketPlayInUpdateSign packetplayinupdatesign) {
	}

	public void a(PacketPlayInKeepAlive packetplayinkeepalive) {
	}

	public void a(PacketPlayInAbilities packetplayinabilities) {
	}

	public void a(PacketPlayInTabComplete packetplayintabcomplete) {
	}

	public void a(PacketPlayInSettings packetplayinsettings) {
	}

	public void a(PacketPlayInCustomPayload packetplayincustompayload) {
	}

	public void a(EnumProtocol enumprotocol, EnumProtocol enumprotocol1) {
	}
}
