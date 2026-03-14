package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerPreJoinEvent extends PlayerEvent {
	private static final HandlerList handlers = new HandlerList();
	private boolean sendLocationOnJoin = true;

	public PlayerPreJoinEvent(Player playerJoined) {
		super(playerJoined);
	}

	public boolean isSendLocationOnJoin() {
		return this.sendLocationOnJoin;
	}

	public void setSendLocationOnJoin(boolean sendLocationOnJoin) {
		this.sendLocationOnJoin = sendLocationOnJoin;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
