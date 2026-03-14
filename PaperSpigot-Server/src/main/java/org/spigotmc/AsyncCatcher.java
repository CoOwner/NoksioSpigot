package org.spigotmc;

import org.github.paperspigot.PaperSpigotConfig;

import net.minecraft.server.MinecraftServer;

public class AsyncCatcher{

    public static boolean enabled = PaperSpigotConfig.asyncCatcherFeature; // PaperSpigot - Allow disabling of AsyncCatcher from PaperSpigotConfig

    public static void catchOp(String reason){
        if ( enabled && Thread.currentThread() != MinecraftServer.getServer().primaryThread ){
            throw new IllegalStateException( "Asynchronous " + reason + "!" );
        }
    }
}
