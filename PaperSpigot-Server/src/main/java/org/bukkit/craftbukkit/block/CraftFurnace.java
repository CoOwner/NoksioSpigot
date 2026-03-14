package org.bukkit.craftbukkit.block;

import com.google.common.base.Preconditions;
import net.minecraft.server.TileEntityFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftInventoryFurnace;
import org.bukkit.inventory.FurnaceInventory;

public class CraftFurnace extends CraftBlockState implements Furnace {
    private final TileEntityFurnace furnace;

    public CraftFurnace(final Block block) {
        super(block);

        furnace = (TileEntityFurnace) ((CraftWorld) block.getWorld()).getTileEntityAt(getX(), getY(), getZ());
    }

    public FurnaceInventory getInventory() {
        return new CraftInventoryFurnace(furnace);
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        if (result) {
            furnace.update();
        }

        return result;
    }

    // PaperSpigot start - furnace cook speed multiplier
    @Override
    public double getCookSpeedMultiplier() {
        return furnace.cookSpeedMultiplier;
    }

    @Override
    public void setCookSpeedMultiplier(double cookSpeedMultiplier) {
        Preconditions.checkArgument(cookSpeedMultiplier >= 0, "Furnace speed multiplier cannot be negative");
        Preconditions.checkArgument(cookSpeedMultiplier <= 100, "Furnace speed multiplier cannot more than 100");
        furnace.cookSpeedMultiplier = cookSpeedMultiplier;
    }
    // PaperSpigot end

    public short getBurnTime() {
        return (short) furnace.burnTime;
    }

    public void setBurnTime(short burnTime) {
        furnace.burnTime = burnTime;
    }

    public short getCookTime() {
        return (short) furnace.cookTime;
    }

    public void setCookTime(short cookTime) {
        furnace.cookTime = cookTime;
    }
}
