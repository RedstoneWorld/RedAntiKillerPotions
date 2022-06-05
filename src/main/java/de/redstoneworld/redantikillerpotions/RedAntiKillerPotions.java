/**
 * This is a small plugin to prevent the "killer potion".
 * The restriction is as minimal as it gets.
 * 
 * Permissions:
 * rwm.redantikillerpotions.shoot-bypass
 *
 * @author Robert Rauh alias RedstoneFuture
 */

package de.redstoneworld.redantikillerpotions;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class RedAntiKillerPotions extends JavaPlugin {
	
	public void onEnable() {
		
        // register events
        new PotionListener(this);

		// save default config
		// saveDefaultConfig();
		
	}

	public void onDisable() {
		
	}

	/**
	 * This method reads the specific messages in config.yml and replaces
	 * the minecraft color codes with a valid character.
	 * 
	 * @param key YAML key
	 * @param args placeholder without "%" and value for the placeholder
	 * 
	 * @return the config messages (String)
	 */
	String getLang(String key, String... args) {
		String lang = getConfig().getString("messages." + key, "&cUnknown language key &6" + key);
		for (int i = 0; i + 1 < args.length; i += 2) {
			lang = lang.replace("%" + args[i] + "%", args[i + 1]);
		}
		return ChatColor.translateAlternateColorCodes('&', lang);
	}

}
