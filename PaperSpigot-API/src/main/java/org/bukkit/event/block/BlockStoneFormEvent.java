package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class BlockStoneFormEvent extends BlockEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;

	public BlockStoneFormEvent(Block block) {
		super(block);
	}

	public boolean isCancelled() {
		return this.cancelled;
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