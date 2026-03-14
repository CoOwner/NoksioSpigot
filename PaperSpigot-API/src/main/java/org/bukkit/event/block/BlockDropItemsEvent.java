package org.bukkit.event.block;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class BlockDropItemsEvent extends BlockEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	private Player player;
	private List<Item> items;

	public BlockDropItemsEvent(Block block, Player player, List<Item> items) {
		super(block);
		this.player = player;
		this.items = items;
	}

	public Player getPlayer() {
		return this.player;
	}

	public List<Item> getItems() {
		return this.items;
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