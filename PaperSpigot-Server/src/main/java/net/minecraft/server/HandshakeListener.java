package net.minecraft.server;

// CraftBukkit start
import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.HashMap;

import net.minecraft.util.com.mojang.authlib.properties.Property; // Spigot
import net.minecraft.util.com.mojang.util.UUIDTypeAdapter;
import us.noks.rinny.spigot.RinnyNetworkClient;
// CraftBukkit end

public class HandshakeListener implements PacketHandshakingInListener {

    private static final com.google.gson.Gson gson = new com.google.gson.Gson(); // Spigot
    // CraftBukkit start - add fields
    private static final HashMap<InetAddress, Long> throttleTracker = new HashMap<InetAddress, Long>();
    private static int throttleCounter = 0;
    // CraftBukkit end

    private final MinecraftServer a;
    private final NetworkManager networkManager;
    
    private NetworkManager getNetworkManager() { 
    	return networkManager; 
    } // Paper - OBFHELPER

    public HandshakeListener(MinecraftServer minecraftserver, NetworkManager networkmanager) {
        this.a = minecraftserver;
        this.networkManager = networkmanager;
    }

    public void a(PacketHandshakingInSetProtocol packethandshakinginsetprotocol) {
        // Spigot start
        if (NetworkManager.SUPPORTED_VERSIONS.contains(packethandshakinginsetprotocol.d())) {
        	NetworkManager.a(this.networkManager).attr(NetworkManager.protocolVersion).set(packethandshakinginsetprotocol.d());
        }
        // Spigot end
        switch (ProtocolOrdinalWrapper.a[packethandshakinginsetprotocol.c().ordinal()]) {
        case 1:
            this.networkManager.a(EnumProtocol.LOGIN);
            ChatComponentText chatcomponenttext;

            // CraftBukkit start - Connection throttle
            try {
                long currentTime = System.currentTimeMillis();
                long connectionThrottle = MinecraftServer.getServer().server.getConnectionThrottle();
                InetAddress address = ((java.net.InetSocketAddress) this.networkManager.getSocketAddress()).getAddress();

                synchronized (throttleTracker) {
                    if ((throttleTracker.containsKey(address)) && (!"127.0.0.1".equals(address.getHostAddress())) && (currentTime - throttleTracker.get(address) < connectionThrottle)) {
                        throttleTracker.put(address, currentTime);
                        chatcomponenttext = new ChatComponentText("Connection throttled! Please wait before reconnecting.");
                        this.networkManager.sendPacket(new PacketLoginOutDisconnect(chatcomponenttext));
                        this.networkManager.close(chatcomponenttext);
                        return;
                    }

                    throttleTracker.put(address, currentTime);
                    throttleCounter++;
                    if (throttleCounter > 200) {
                        throttleCounter = 0;

                        // Cleanup stale entries
                        java.util.Iterator iter = throttleTracker.entrySet().iterator();
                        while (iter.hasNext()) {
                            java.util.Map.Entry<InetAddress, Long> entry = (java.util.Map.Entry) iter.next();
                            if (entry.getValue() > connectionThrottle) {
                                iter.remove();
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                org.apache.logging.log4j.LogManager.getLogger().debug("Failed to check connection throttle", t);
            }
            // CraftBukkit end

            if (packethandshakinginsetprotocol.d() > 5 && packethandshakinginsetprotocol.d() != 47) { // Spigot
                chatcomponenttext = new ChatComponentText(MessageFormat.format(org.spigotmc.SpigotConfig.outdatedServerMessage, "1.7.X/1.8.X")); // Spigot
                this.networkManager.sendPacket(new PacketLoginOutDisconnect(chatcomponenttext));
                this.networkManager.close(chatcomponenttext);
            } else if (packethandshakinginsetprotocol.d() < 4) {
                chatcomponenttext = new ChatComponentText(MessageFormat.format(org.spigotmc.SpigotConfig.outdatedClientMessage, "1.7.X/1.8.X")); // Spigot
                this.networkManager.sendPacket(new PacketLoginOutDisconnect(chatcomponenttext));
                this.networkManager.close(chatcomponenttext);
            } else {
                this.networkManager.setPacketListener((PacketListener) (new LoginListener(this.a, this.networkManager)));
                // RinnySpigot start - Improve bungee support
                String ip = ((java.net.InetSocketAddress) this.networkManager.getSocketAddress()).getAddress().getHostAddress();
                boolean proxyLogicEnabled = org.spigotmc.SpigotConfig.bungee && org.spigotmc.SpigotConfig.bungeeAddresses.contains(ip);
                // RinnySpigot end
                // Spigot Start
                if (org.spigotmc.SpigotConfig.bungee) {
                	this.networkManager.isProxied = true; // RawrSpigot
                    String[] split = packethandshakinginsetprotocol.b.split("\00"); // \u0000
                    if (split.length == 3 || split.length == 4) {
                        packethandshakinginsetprotocol.b = split[0];
                        networkManager.n = new java.net.InetSocketAddress(split[1], ((java.net.InetSocketAddress) networkManager.getSocketAddress()).getPort());
                        networkManager.spoofedUUID = UUIDTypeAdapter.fromString(split[2]);
                    } else if (false) { // RinnySpigot
                        chatcomponenttext = new ChatComponentText("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!");
                        this.networkManager.sendPacket(new PacketLoginOutDisconnect(chatcomponenttext));
                        this.networkManager.close(chatcomponenttext);
                        return;
                    }
                    if (split.length == 4) {
                        networkManager.spoofedProfile = gson.fromJson(split[3], Property[].class);
                    }
                }
                // Spigot End
                ((LoginListener) this.networkManager.getPacketListener()).hostname = packethandshakinginsetprotocol.b + ":" + packethandshakinginsetprotocol.c; // CraftBukkit - set hostname
            }
            break;

        case 2:
            this.networkManager.a(EnumProtocol.STATUS);
            this.networkManager.setPacketListener((PacketListener) (new PacketStatusListener(this.a, this.networkManager)));
            break;

        default:
            throw new UnsupportedOperationException("Invalid intention " + packethandshakinginsetprotocol.c());
        }
        
        // Rinny start - NetworkClient implementation
        this.getNetworkManager().protocolVersions = packethandshakinginsetprotocol.getProtocolVersion();
        this.getNetworkManager().virtualHost = RinnyNetworkClient.prepareVirtualHost(packethandshakinginsetprotocol.b, packethandshakinginsetprotocol.c);
        // Rinny end
    }

    public void a(IChatBaseComponent ichatbasecomponent) {}

    public void a(EnumProtocol enumprotocol, EnumProtocol enumprotocol1) {
        if (enumprotocol1 != EnumProtocol.LOGIN && enumprotocol1 != EnumProtocol.STATUS) {
            throw new UnsupportedOperationException("Invalid state " + enumprotocol1);
        }
    }

    public void a() {}
}
