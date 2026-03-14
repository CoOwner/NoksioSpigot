package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class AntiCheatEvent extends PlayerEvent implements Cancellable{
	
	private static final HandlerList handlers = new HandlerList();
	private String msg;
	private Type type;
	private Level level;

	public static enum Type{
		SPEED, 
		FLY, 
		CRIT, 
		NO_FALL, 
		SPIDER, 
		INVENTORY, 
		TIMER, 
		DEBUG, 
		UNKNOWN, 
		ANTI_KB, 
		FREECAM, 
		PHASE, 
		FAST_EAT_MACHINE_GUN, 
		REGEN;
  
		private Type() {}
	}

	public static enum Level{
		TRIAL,  MOD,  ADMIN;
  
		private Level() {}
	}

	private boolean cancelled = false;

	public AntiCheatEvent(Player player, Type type, Level level, String msg){
		super(player);
		this.type = type;
		this.level = level;
		this.msg = msg;
	}

	public String getMsg(){
		return this.msg;
	}

	public HandlerList getHandlers(){
		return handlers;
	}

	public static HandlerList getHandlerList(){
		return handlers;
	}

	public Type getType(){
		return this.type;
	}

	public Level getLevel(){
		return this.level;
	}

	public boolean isCancelled(){
		return this.cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}
}