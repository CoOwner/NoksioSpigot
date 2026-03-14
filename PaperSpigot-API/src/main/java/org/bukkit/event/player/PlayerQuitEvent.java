package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Called when a player leaves a server
 */
public class PlayerQuitEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final PlayerQuitType quitType;
    private String quitMessage;
    
    public PlayerQuitEvent(final Player who, final String quitMessage) {
        super(who);
        this.quitMessage = quitMessage;
        this.quitType = PlayerQuitType.NORMAL;
    }

    public PlayerQuitEvent(final Player who, final String quitMessage, final PlayerQuitType quitType) {
        super(who);
        this.quitMessage = quitMessage;
        this.quitType = quitType;
    }

    /**
     * Gets the quit message to send to all online players
     *
     * @return string quit message
     */
    public String getQuitMessage() {
        return quitMessage;
    }

    /**
     * Sets the quit message to send to all online players
     *
     * @param quitMessage quit message
     */
    public void setQuitMessage(String quitMessage) {
        this.quitMessage = quitMessage;
    }
    
    public PlayerQuitType getQuitType() {
		return quitType;
	}

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
