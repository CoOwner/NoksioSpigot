package us.noks.rinny;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.spigotmc.Metrics;

import com.google.common.base.Throwables;

import net.minecraft.server.MinecraftServer;
import us.noks.rinny.command.CreatorsCommand;
import us.noks.rinny.command.LantenceCommand;
import us.noks.rinny.command.TrackingCommand;
import us.noks.rinny.command.KnockbackCommand;

public class NoksConfig {
	
	private static File CONFIG_FILE = new File("rinny.yml");
	private static YamlConfiguration config;
	public static String HEADER = "This is the main configuration file for RinnySpigot.\n"
            					+ "The creator of this PaperSpigot fork is Noksio.\n"
            					+ "Thanks to the RinnySpigot dev team.\n"
            					+ "Prout mdrlolxd. (Faut quand même rigoler dans la vie!)";
	static Map<String, Command> commands;
	private static Metrics metrics;
	public static int version;

	public static void init() {
		config = new YamlConfiguration();
		try {
			config.load(CONFIG_FILE);
		} catch (IOException ex) {
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load rinny.yml, please correct your syntax errors", ex);
            throw Throwables.propagate(ex);
        }
		
		config.options().header(HEADER);
		config.options().copyDefaults(true);
		
		commands = new HashMap<String, Command>();
		
		version = getInt("config-version", 1);
		set("config-version", 3);
		readConfig(NoksConfig.class, null);
	}
	
	public static void registerCommands() {
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            MinecraftServer.getServer().server.getCommandMap().register(entry.getKey(), "RySpigot", entry.getValue());
        }

        if (metrics == null) {
            try {
                metrics = new Metrics();
                metrics.start();
            } catch (IOException ex) {
                Bukkit.getServer().getLogger().log(Level.SEVERE, "Could not start metrics service", ex);
            }
        }
    }
	
	static void readConfig(Class<?> clazz, Object instance) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPrivate( method.getModifiers())) {
                if (method.getParameterTypes().length == 0 && method.getReturnType() == Void.TYPE) {
                    try {
                        method.setAccessible(true);
                        method.invoke(instance);
                    } catch (InvocationTargetException ex) {
                        throw Throwables.propagate(ex.getCause());
                    } catch (Exception ex) {
                        Bukkit.getLogger().log(Level.SEVERE, "Error invoking " + method, ex);
                    }
                }
            }
        }

        try {
            config.save(CONFIG_FILE);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save " + CONFIG_FILE, ex);
        }
    }
	
	private static void set(String path, Object val) {
        config.set(path, val);
    }

    private static boolean getBoolean(String path, boolean def) {
        config.addDefault(path, def);
        return config.getBoolean(path, config.getBoolean(path));
    }

    private static double getDouble(String path, double def) {
        config.addDefault(path, def);
        return config.getDouble(path, config.getDouble(path));
    }
    
    private static long getLong(String path, long def) {
        config.addDefault(path, def);
        return config.getLong(path, config.getLong(path));
    }

    private static float getFloat(String path, float def) {
        // TODO: Figure out why getFloat() always returns the default value.
        return (float) getDouble(path, (double) def);
    }

    private static int getInt(String path, int def) {
        config.addDefault(path, def);
        return config.getInt(path, config.getInt(path));
    }

    private static <T> List getList(String path, T def) {
        config.addDefault(path, def);
        return (List<T>) config.getList(path, config.getList(path));
    }

    private static String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, config.getString(path));
    }
    
    // CONFIG
	
	public static double knockbackSprintForce;
	public static double knockbackSprintHeight;
	public static double knockbackFriction;
	public static double knockbackForce;
	public static double knockbackHeight;
	public static double knockbackHeightLimit;
	public static boolean knockbackAsync;
	private static void setupKnockback() {
		knockbackSprintForce = getDouble("knockback.sprint-force", 0.5D); // 0.49F
		knockbackSprintHeight = getDouble("knockback.sprint-height", 0.1D); // 0.085D
		knockbackFriction = getDouble("knockback.friction", 2.0D); // 2.0D
		knockbackForce = getDouble("knockback.force", 0.4D); // 0.4D
		knockbackHeight = getDouble("knockback.height", 0.4D); // 0.4D
		knockbackHeightLimit = getDouble("knockback.height-limit", 0.4000000059604645D); // 0.4000000059604645D
		knockbackAsync = getBoolean("knockback.async", false);
	}
	
	public static int playerInvulnerableTicks;
	private static void playerInvulnerableTicks() {
		playerInvulnerableTicks = getInt("player-invulnerable-ticks", 100);
	}
	
	public static boolean fastCook;
	public static boolean fastBrew;
	private static void fastSettings() {
		fastCook = getBoolean("settings.fast-cook", false);
		fastBrew = getBoolean("settings.fast-brew", false);
	}
	
	public static boolean hidePlayerOnTablist;
	private static void hidePlayerOnTablist() {
		hidePlayerOnTablist = getBoolean("settings.hide-player-on-tab-list", true);
	}
	
	public static boolean anticheatActivated;
	private static void anticheatActivated() {
		anticheatActivated = getBoolean("settings.anticheat-enabled", false);
	}
	
	public static boolean tickHoppers;
	private static void tickHoppers() {
		tickHoppers = getBoolean("settings.tick-hoppers", true);
	}
	
	public static boolean tcpNoDelay;
	private static void tcpNoDelay() {
		tcpNoDelay = getBoolean("settings.tcp-no-delay", true);
	}
	
	public static boolean playerMoveEvent;
	private static void playerMoveEvent() {
		playerMoveEvent = getBoolean("settings.enable-PlayerMoveEvent", true);
	}
	
	public static boolean isFaction;
	private static void isFaction() {
		isFaction = getBoolean("settings.isfaction", false);
	}
	
	public static String playerDefaultLocale;
	private static void playerDefaultLocale() {
		playerDefaultLocale = getString("player-default-locale", "en_US");
	}
	
	public static boolean maintainCursorInventoryOpens;
	private static void maintainCursorInventoryOpens() {
		maintainCursorInventoryOpens = getBoolean("settings.maintain-cursor-inventory-opens", false);
	}
	
	public static boolean smoothMovement;
	private static void smoothMovement() {
		smoothMovement = getBoolean("settings.smooth-movement", false);
	}
	
	public static boolean doLeafDecay;
	private static void doLeafDecay() {
		doLeafDecay = getBoolean("world.do-leaf-decay", true);
	}
	
	public static String kickspammessage;
	private static void kickSpamMessage() {
		kickspammessage = getString("message.kick-spam", "Disconnect for spam.");
	}
	
	public static boolean usePlayerData;
	private static void usePlayerData() {
		usePlayerData = getBoolean("world.use-player-data", true);
	}
	
	public static boolean disableMobAi;
	private static void disableMobAi() {
		disableMobAi = getBoolean("mobs.disable-mob-ai", false);
	}
	
	public static boolean disableMetrics;
	private static void disableMetrics() {
		disableMetrics = getBoolean("disable-metrics", false);
	}
	
	// COMMANDS
	
	private static void latenceCommand() {
    	commands.put("ping", new LantenceCommand("ping"));
    }
    
	private static void trackingCommand(){
		commands.put("tracking", new TrackingCommand());
	}

	private static void knockbackCommand() {
		commands.put("setknockback", new KnockbackCommand("setknockback"));
	}
	
	private static void creatorsCommand(){
		commands.put("creator", new CreatorsCommand("creator"));
	}
}
