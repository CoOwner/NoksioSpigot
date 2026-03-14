package net.minecraft.server;

import java.net.SocketAddress;
import java.util.Queue;

import javax.crypto.SecretKey;

import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.spigotmc.SpigotCompressor;
import org.spigotmc.SpigotDecompressor;
// Spigot end

// Spigot start
import com.google.common.collect.ImmutableSet;

import net.minecraft.util.com.google.common.collect.Queues;
import net.minecraft.util.com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.minecraft.util.com.mojang.authlib.properties.Property;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelFutureListener;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.util.io.netty.channel.local.LocalChannel;
import net.minecraft.util.io.netty.channel.local.LocalServerChannel;
import net.minecraft.util.io.netty.channel.nio.NioEventLoopGroup;
import net.minecraft.util.io.netty.handler.timeout.TimeoutException;
import net.minecraft.util.io.netty.util.AttributeKey;
import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.util.org.apache.commons.lang3.Validate;

public class NetworkManager extends SimpleChannelInboundHandler {

	private static final GenericFutureListener[] NO_FUTURE_LISTENER = new GenericFutureListener[0];
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Marker a = MarkerManager.getMarker("NETWORK");
    public static final Marker b = MarkerManager.getMarker("NETWORK_PACKETS", a);
    public static final Marker c = MarkerManager.getMarker("NETWORK_STAT", a);
    public static final AttributeKey d = new AttributeKey("protocol");
    public static final AttributeKey e = new AttributeKey("receivable_packets");
    public static final AttributeKey f = new AttributeKey("sendable_packets");
    public static final NioEventLoopGroup g = new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Client IO #%d").setDaemon(true).build());
    public static final NetworkStatistics h = new NetworkStatistics();
    private final boolean j;
    private final Queue k = Queues.newConcurrentLinkedQueue();
    private final Queue l = Queues.newConcurrentLinkedQueue();
    private Channel channel;
    // Spigot Start
    public SocketAddress n;
    public java.util.UUID spoofedUUID;
    public Property[] spoofedProfile;
    public boolean preparing = true;
    public boolean isProxied = false; // RinnySpigot
    // Spigot End
    private PacketListener o;
    private EnumProtocol p;
    private IChatBaseComponent q;
    private boolean r;
    // Spigot Start
    public static final AttributeKey<Integer> protocolVersion = new AttributeKey<Integer>("protocol_version");
    public static final ImmutableSet<Integer> SUPPORTED_VERSIONS = ImmutableSet.of(4, 5, 47);
    public static final int CURRENT_VERSION = 5;
    public long currentTime = System.currentTimeMillis();
    
	// Rinny start - NetworkClient implementation
	public int protocolVersions;
	public java.net.InetSocketAddress virtualHost;
	// Rinny end
    
    public static int getVersion(Channel attr) {
        Integer ver = attr.attr(protocolVersion).get();
        return (ver != null) ? ver : CURRENT_VERSION;
    }
    
    public int getVersion() {
        return getVersion(this.channel);
    }
    // Spigot End

    public NetworkManager(boolean flag) {
        this.j = flag;
    }

    public void channelActive(ChannelHandlerContext channelhandlercontext) throws Exception { // CraftBukkit - throws Exception
        super.channelActive(channelhandlercontext);
        this.channel = channelhandlercontext.channel();
        this.n = this.channel.remoteAddress();
        // Spigot Start
        this.preparing = false;
        // Spigot End
        this.a(EnumProtocol.HANDSHAKING);
    }

    public void a(EnumProtocol enumprotocol) {
        this.p = (EnumProtocol) this.channel.attr(d).getAndSet(enumprotocol);
        this.channel.attr(e).set(enumprotocol.a(this.j));
        this.channel.attr(f).set(enumprotocol.b(this.j));
        this.channel.config().setAutoRead(true);
        LOGGER.debug("Enabled auto read");
    }

    public void channelInactive(ChannelHandlerContext channelhandlercontext) {
        this.close(new ChatMessage("disconnect.endOfStream", ArrayUtils.EMPTY_OBJECT_ARRAY));
    }

    public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) {
        ChatMessage chatmessage;

        if (throwable instanceof TimeoutException) {
            chatmessage = new ChatMessage("disconnect.timeout", ArrayUtils.EMPTY_OBJECT_ARRAY);
        } else {
            chatmessage = new ChatMessage("disconnect.genericReason", "Internal Exception: " + throwable);
        }

        this.close(chatmessage);
        if (MinecraftServer.getServer().isDebugging()) throwable.printStackTrace(); // Spigot
    }

    protected void a(ChannelHandlerContext channelhandlercontext, Packet packet) {
        if (this.channel.isOpen()) {
            if (packet.async()) {
                packet.handle(this.o);
            } else {
                this.k.add(packet);
            }
        }
    }

    public void setPacketListener(PacketListener packetlistener) {
        Validate.notNull(packetlistener, "packetListener", new Object[0]);
        LOGGER.debug("Set listener of {} to {}", new Object[] { this, packetlistener});
        this.o = packetlistener;
    }
    
	public void sendPacket(Packet packet) {
		handle(packet, NO_FUTURE_LISTENER);
	}

    public void handle(Packet packet, GenericFutureListener... agenericfuturelistener) {
        if (this.channel != null && this.channel.isOpen()) {
            this.i();
            this.b(packet, agenericfuturelistener);
        } else {
            this.l.add(new QueuedPacket(packet, agenericfuturelistener));
        }
    }

    private void b(Packet packet, GenericFutureListener[] agenericfuturelistener) {
        EnumProtocol enumprotocol = EnumProtocol.a(packet);
        EnumProtocol enumprotocol1 = (EnumProtocol) this.channel.attr(d).get();

        if (enumprotocol1 != enumprotocol) {
            LOGGER.debug("Disabled auto read");
            this.channel.config().setAutoRead(false);
        }

        if (this.channel.eventLoop().inEventLoop()) {
            if (enumprotocol != enumprotocol1) {
                this.a(enumprotocol);
            }

            this.channel.writeAndFlush(packet).addListeners(agenericfuturelistener).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        } else {
            this.channel.eventLoop().execute(new QueuedProtocolSwitch(this, enumprotocol, enumprotocol1, packet, agenericfuturelistener));
        }
    }

    private void i() {
        if (this.channel != null && this.channel.isOpen()) {
            // PaperSpigot  start - Improve Network Manager packet handling
            QueuedPacket queuedpacket;
            while ((queuedpacket = (QueuedPacket) this.l.poll()) != null) {
                this.b(QueuedPacket.a(queuedpacket), QueuedPacket.b(queuedpacket));
            }
            // PaperSpigot end
        }
    }

    public void a() {
        this.i();
        EnumProtocol enumprotocol = (EnumProtocol) this.channel.attr(d).get();

        if (this.p != enumprotocol) {
            if (this.p != null) {
                this.o.a(this.p, enumprotocol);
            }

            this.p = enumprotocol;
        }

        if (this.o != null) {
            // PaperSpigot start - Improve Network Manager packet handling - Configurable packets per player per tick processing
            Packet packet;
            for (int i = org.github.paperspigot.PaperSpigotConfig.maxPacketsPerPlayer; (packet = (Packet) this.k.poll()) != null && i >= 0; --i) {
                // PaperSpigot end

                // CraftBukkit start
                if (!this.isConnected() || !this.channel.config().isAutoRead()) {
                    continue;
                }
                // CraftBukkit end
                packet.handle(this.o);
            }

            this.o.a();
        }

        this.channel.flush();
    }

    public SocketAddress getSocketAddress() {
        return this.n;
    }

    public void close(IChatBaseComponent ichatbasecomponent) {
        // Spigot Start
        this.preparing = false;
        this.k.clear();
        this.l.clear();
        // Spigot End
        if (this.channel.isOpen()) {
            this.channel.close();
            this.q = ichatbasecomponent;
        }
    }

    public boolean isLocal() {
        return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
    }

    public void a(SecretKey secretkey) {
        this.channel.pipeline().addBefore("splitter", "decrypt", new PacketDecrypter(MinecraftEncryption.a(2, secretkey)));
        this.channel.pipeline().addBefore("prepender", "encrypt", new PacketEncrypter(MinecraftEncryption.a(1, secretkey)));
        this.r = true;
    }

    public boolean isConnected() {
        return this.channel != null && this.channel.isOpen();
    }

    public PacketListener getPacketListener() {
        return this.o;
    }

    public IChatBaseComponent f() {
        return this.q;
    }

    public void g() {
        this.channel.config().setAutoRead(false);
    }

    protected void channelRead0(ChannelHandlerContext channelhandlercontext, Object object) {
        this.a(channelhandlercontext, (Packet) object);
    }

    static Channel a(NetworkManager networkmanager) {
        return networkmanager.channel;
    }

    // Spigot Start
    public SocketAddress getRawAddress() {
        return this.channel.remoteAddress();
    }
    // Spigot End


    // Spigot start - protocol patch
    public void enableCompression() {
        // Fix ProtocolLib compatibility
        if (channel.pipeline().get("protocol_lib_decoder") != null) {
        	channel.pipeline().addBefore("protocol_lib_decoder", "decompress", new SpigotDecompressor());
        } else {
        	channel.pipeline().addBefore("decoder", "decompress", new SpigotDecompressor());
        }
        channel.pipeline().addBefore("encoder", "compress", new SpigotCompressor());
    }
    // Spigot end
}
