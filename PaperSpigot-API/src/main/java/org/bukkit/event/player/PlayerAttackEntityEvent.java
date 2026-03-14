package org.bukkit.event.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerAttackEntityEvent extends PlayerEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	protected Entity clickedEntity;
	boolean cancelled = false;

	public PlayerAttackEntityEvent(Player who, Entity clickedEntity) {
		super(who);
		this.clickedEntity = clickedEntity;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

	public Entity getClickedEntity() {
		return this.clickedEntity;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
