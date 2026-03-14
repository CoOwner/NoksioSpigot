package us.noks.rinny.potion;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

//From KSpigot (KohiSpigot)
public class PotionsConfig {
	private static final YamlConfiguration conf = YamlConfiguration.loadConfiguration(new File("potions.yml"));
	private static final List<PotionMatcher> disableBrewing = new ArrayList<PotionMatcher>();
	private static final Map<Integer, Boolean> disableBrewingCache = new HashMap<Integer, Boolean>();

	static {
		List<?> disable = conf.getList("disable-brewing");
		if (disable != null) {
			for (Object obj : disable) {
				if ((obj instanceof Map)) {
					disableBrewing.add(new PotionMatcher((Map) obj));
				}
			}
		}
	}

	public static boolean isBrewingDisabled(int damage) {
		Boolean cached = (Boolean) disableBrewingCache.get(Integer.valueOf(damage));
		if (cached != null) {
			return cached.booleanValue();
		}
		for (PotionMatcher potion : disableBrewing) {
			if (potion.matches(damage)) {
				disableBrewingCache.put(Integer.valueOf(damage), Boolean.valueOf(true));
				return true;
			}
		}
		disableBrewingCache.put(Integer.valueOf(damage), Boolean.valueOf(false));
		return false;
	}
}
