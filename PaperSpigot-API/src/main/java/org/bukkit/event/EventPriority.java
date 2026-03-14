package org.bukkit.event;

/**
 * Represents an event's priority in execution
 */
public enum EventPriority {

	FIRST(0), 
	LOWEST(1), 
	LOW(2), 
	NORMAL(3), 
	HIGH(4), 
	HIGHEST(5), 
	MONITOR(6), 
	LAST(7), 
	LASTER(8), 
	LASTEST(9), 
	FINAL(10);

    private final int slot;

    private EventPriority(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}
