package us.noks.rinny.potion;

import java.util.Map;

import org.bukkit.potion.PotionType;

//From KSpigot (KohiSpigot)
public class PotionMatcher {
	private PotionType type;
	private Integer level;
	private Boolean extended;
	private Boolean splash;

	public PotionMatcher(Map conf) {
		if (conf.containsKey("type")) {
			try {
				this.type = PotionType.valueOf((String) conf.get("type"));
			} catch (IllegalArgumentException ex) {
			}
		}
		if (conf.containsKey("level")) {
			this.level = ((Integer) conf.get("level"));
		}
		if (conf.containsKey("extended")) {
			this.extended = ((Boolean) conf.get("extended"));
		}
		if (conf.containsKey("splash")) {
			this.splash = ((Boolean) conf.get("splash"));
		}
	}

	public boolean matches(int damage) {
		if ((this.type != null) && (this.type.getDamageValue() != (damage & 0xF))) {
			return false;
		}
		if ((this.level != null) && (this.level.intValue() != (damage >> 5 & 0x1) + 1)) {
			return false;
		}
		if (this.extended != null) {
			if (this.extended.booleanValue() != ((damage >> 6 & 0x1) == 1)) {
				return false;
			}
		}
		if (this.splash != null) {
			if (this.splash.booleanValue() != ((damage >> 14 & 0x1) == 1)) {
				return false;
			}
		}
		return true;
	}
}
