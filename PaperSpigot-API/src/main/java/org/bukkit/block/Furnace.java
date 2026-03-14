package org.bukkit.block;

import org.bukkit.inventory.FurnaceInventory;

/**
 * Represents a furnace.
 */
public interface Furnace extends BlockState, ContainerBlock {

    /**
     * Gets the speed multiplier that this {@link Furnace} will cook
     * compared to vanilla.
     *
     * @return the multiplier, a value between 0 and 200
     */
    double getCookSpeedMultiplier() throws IllegalArgumentException;

    /**
     * Sets the speed multiplier that this {@link Furnace} will cook
     * compared to vanilla.
     *
     * @param multiplier the multiplier to set, a value between 0 and 200
     * @throws IllegalArgumentException if value is less than 0
     * @throws IllegalArgumentException if value is more than 200
     */
    void setCookSpeedMultiplier(double multiplier);

    /**
     * Get burn time.
     *
     * @return Burn time
     */
    public short getBurnTime();

    /**
     * Set burn time.
     *
     * @param burnTime Burn time
     */
    public void setBurnTime(short burnTime);

    /**
     * Get cook time.
     *
     * @return Cook time
     */
    public short getCookTime();

    /**
     * Set cook time.
     *
     * @param cookTime Cook time
     */
    public void setCookTime(short cookTime);

    public FurnaceInventory getInventory();
}
