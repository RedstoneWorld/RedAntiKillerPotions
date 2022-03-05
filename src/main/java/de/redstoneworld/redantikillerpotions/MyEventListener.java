package de.redstoneworld.redantikillerpotions;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class MyEventListener implements Listener {

	protected final RedAntiKillerPotions plugin;
	private Player player;

	public MyEventListener(RedAntiKillerPotions plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

	}


	@EventHandler
	public void onPotionSplash(PotionSplashEvent event) {
		if (event.getPotion().getShooter() instanceof Player) {
			player = (Player) event.getPotion().getShooter();

			Collection<PotionEffect> effects = event.getPotion().getEffects();

			if (hasInvalidHealEffect(effects, "splash_potion")) {

				if (!hasShootBypassPermissions(player)) {
					event.setCancelled(true);
				}
			}
		}
	}

/** The bug is not present at lingering potions.
 *
 *
	@EventHandler
	public void onLingeringPotionSplash(LingeringPotionSplashEvent event) {
		if (event.getAreaEffectCloud().hasCustomEffects()) {

			List<PotionEffect> effects = event.getAreaEffectCloud().getCustomEffects();

			if (hasInvalidHealEffect(effects, "lingering_potion")) {
				event.setCancelled(true);
			}
		}
	}
**/

	// potion check
	public boolean hasInvalidHealEffect(Collection<PotionEffect> effects, String sourceItemName) {
		for (PotionEffect effect : effects) {

			if (effect.getType().equals(PotionEffectType.HEAL)) {

				int effectAmplifier = effect.getAmplifier();

				if (effectAmplifier > 124) {
					plugin.getLogger().info("Detected potion effect 'HEAL' from " + sourceItemName + " with a amplifier of " + effectAmplifier + ".");
					return true;
				}
			}
		}
		return false;
	}


	// bypass permission check:
	// only possible for splash potions
	public boolean hasShootBypassPermissions(Player p) {

		if (p.hasPermission("rwm.redantikillerpotions.shoot-bypass")) {
			return true;
		}
		return false;
	}

}