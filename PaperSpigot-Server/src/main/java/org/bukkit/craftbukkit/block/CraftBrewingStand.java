package org.bukkit.craftbukkit.block;

import net.minecraft.server.TileEntityBrewingStand;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftInventoryBrewer;
import org.bukkit.inventory.BrewerInventory;

import com.google.common.base.Preconditions;

public class CraftBrewingStand extends CraftBlockState implements BrewingStand {
    private final TileEntityBrewingStand brewingStand;

    public CraftBrewingStand(Block block) {
        super(block);

        brewingStand = (TileEntityBrewingStand) ((CraftWorld) block.getWorld()).getTileEntityAt(getX(), getY(), getZ());
    }

    public BrewerInventory getInventory() {
        return new CraftInventoryBrewer(brewingStand);
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        if (result) {
            brewingStand.update();
        }

        return result;
    }
    
    // PaperSpigot start - brewingstand brew speed multiplier
    @Override
    public double getBrewSpeedMultiplier() {
        return brewingStand.brewSpeedMultiplier;
    }

    @Override
    public void setBrewSpeedMultiplier(double brewSpeedMultiplier) {
        Preconditions.checkArgument(brewSpeedMultiplier >= 0, "Furnace speed multiplier cannot be negative");
        Preconditions.checkArgument(brewSpeedMultiplier <= 100, "Furnace speed multiplier cannot more than 100");
        brewingStand.brewSpeedMultiplier = brewSpeedMultiplier;
    }
    // PaperSpigot end

    public int getBrewingTime() {
        return brewingStand.brewTime;
    }

    public void setBrewingTime(int brewTime) {
        brewingStand.brewTime = brewTime;
    }
}
