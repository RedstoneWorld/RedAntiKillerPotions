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
	
}
