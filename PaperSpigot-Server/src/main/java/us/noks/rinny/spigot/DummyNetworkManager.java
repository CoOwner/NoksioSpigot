package us.noks.rinny.spigot;

import java.net.SocketAddress;

import javax.crypto.SecretKey;

import net.minecraft.server.EnumProtocol;
import net.minecraft.server.IChatBaseComponent;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet;
import net.minecraft.server.PacketListener;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;

public class DummyNetworkManager extends NetworkManager {
	
	private IChatBaseComponent ichatbasecomponent;

	public DummyNetworkManager() {
		super(true);
	}

	public int getVersion() {
		return 5;
	}

	public void channelActive(ChannelHandlerContext channelhandlercontext) throws Exception {
	}

	public void a(EnumProtocol enumprotocol) {
	}

	public void channelInactive(ChannelHandlerContext channelhandlercontext) {
	}

	public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) {
	}

	protected void a(ChannelHandlerContext channelhandlercontext, Packet packet) {
	}

	public void setPacketListener(PacketListener packetlistener) {
		super.setPacketListener(packetlistener);
	}

	public void handle(Packet packet, GenericFutureListener... agenericfuturelistener) {
	}

	private void b(Packet packet, GenericFutureListener[] agenericfuturelistener) {
	}

	private void h() {
	}

	public void a() {
	}

	public SocketAddress getSocketAddress() {
		return new SocketAddress() {
		};
	}

	public void a(IChatBaseComponent ichatbasecomponent) {
		this.ichatbasecomponent = ichatbasecomponent;
	}

	public boolean c() {
		return false;
	}

	public void a(SecretKey secretkey) {
	}

	public boolean d() {
		return true;
	}

	public PacketListener getPacketListener() {
		return super.getPacketListener();
	}

	public IChatBaseComponent f() {
		return this.ichatbasecomponent;
	}

	public void g() {
	}

	protected void channelRead0(ChannelHandlerContext channelhandlercontext, Object object) {
	}

	static Channel a(NetworkManager networkmanager) {
		return null;
	}
}
