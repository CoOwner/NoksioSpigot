package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerUndisguiseEvent extends PlayerEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancel = false;
	private boolean fromCommand;

	public PlayerUndisguiseEvent(final Player player, final boolean fromCommand) {
		super(player);
		this.fromCommand = fromCommand;
	}

	public boolean isFromCommand() {
		return this.fromCommand;
	}
	
	@Override
	public boolean isCancelled() {
		return this.cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}