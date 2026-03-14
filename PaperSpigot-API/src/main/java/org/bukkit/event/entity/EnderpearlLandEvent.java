package org.bukkit.event.entity;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class EnderpearlLandEvent extends EntityEvent implements Cancellable { // RinnySpigot - Make the event cancellable

    private static final HandlerList handlers = new HandlerList();
    private final Reason reason;
	private final Entity hitEntity; // RinnySpigot
	private boolean cancelled; // RinnySpigot

    public EnderpearlLandEvent(final EnderPearl enderPearl, final Reason reason) {
    	//super(enderPearl);
        //this.reason = reason;
    	this(enderPearl, reason, null);
    }
    
    public EnderpearlLandEvent(final EnderPearl enderPearl, final Reason reason, final Entity hitEntity) {
        super(enderPearl);
        this.reason = reason;
        this.hitEntity = hitEntity;
    }

    @Override
    public EnderPearl getEntity() {
        return (EnderPearl) entity;
    }
    
	/**
	 * Returns the reason for the Enderpearl landing
	 *
	 * @return Reason the Enderpearl landed
	 */
    public Reason getReason() {
        return reason;
    }
    
	/**
	 * Returns the entity that was hit by the Enderpearl
	 *
	 * @return Entity hit
	 */
	public Entity getHitEntity() {
		return hitEntity;
	}

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

    public enum Reason {
        /**
         * Called when the pearl hits a block
         */
        BLOCK,
        /**
         * Called when the pearl hits an entity
         */
        ENTITY
    }
}
