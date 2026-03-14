package org.bukkit.block;

import org.bukkit.inventory.BrewerInventory;

/**
 * Represents a brewing stand.
 */
public interface BrewingStand extends BlockState, ContainerBlock {

	/**
     * Gets the speed multiplier that this {@link BrewingStand} will brew
     * compared to vanilla.
     *
     * @return the multiplier, a value between 0 and 200
     */
    double getBrewSpeedMultiplier() throws IllegalArgumentException;

    /**
     * Sets the speed multiplier that this {@link BrewingStand} will brew
     * compared to vanilla.
     *
     * @param multiplier the multiplier to set, a value between 0 and 200
     * @throws IllegalArgumentException if value is less than 0
     * @throws IllegalArgumentException if value is more than 200
     */
    void setBrewSpeedMultiplier(double multiplier);
    
    /**
     * How much time is left in the brewing cycle
     *
     * @return Brew Time
     */
    int getBrewingTime();

    /**
     * Set the time left before brewing completes.
     *
     * @param brewTime Brewing time
     */
    void setBrewingTime(int brewTime);

    public BrewerInventory getInventory();
}
