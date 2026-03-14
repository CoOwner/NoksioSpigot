package org.bukkit.event.vehicle;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Raised when a living entity exits a vehicle.
 */
public class VehicleExitEvent extends VehicleEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final LivingEntity exited;
    private final Location exit;

    public VehicleExitEvent(final Vehicle vehicle, final LivingEntity exited, final Location exit) {
        super(vehicle);
        this.exited = exited;
        this.exit = exit;
    }

    /**
     * Get the living entity that exited the vehicle.
     *
     * @return The entity.
     */
    public LivingEntity getExited() {
        return exited;
    }

    /**
     * Returns the dismount location for the entity
     * 
     * @return the exit location
     */
    public Location getExitLocation() {
        return exit;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
