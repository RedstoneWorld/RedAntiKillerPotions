package de.redstoneworld.redantikillerpotions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.List;

public class MyEventListener implements Listener {

	protected final RedAntiKillerPotions plugin;
	private Player player;

	public MyEventListener(RedAntiKillerPotions plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

	}


	@EventHandler(priority = EventPriority.HIGH)
	public void onPotionSplash(PotionSplashEvent event) {
		if (event.getPotion().getShooter() instanceof Player) {
			player = (Player) event.getPotion().getShooter();

			Collection<PotionEffect> effects = event.getPotion().getEffects();

			if (hasInvalidHealEffect(effects, "splash_potion")) {

				if (!hasShootBypassPermissions(player)) {
					event.setCancelled(true);
				}
			}

		} else {

			Collection<PotionEffect> effects = event.getPotion().getEffects();

			if (hasInvalidHealEffect(effects, "splash_potion")) {
				event.setCancelled(true);
			}
		}
	}


/*
	@EventHandler(priority = EventPriority.HIGH)
	public void onLingeringPotionSplash(LingeringPotionSplashEvent event) {
		if (event.getAreaEffectCloud().hasCustomEffects()) {

			List<PotionEffect> effects = event.getAreaEffectCloud().getCustomEffects();

			if (hasInvalidHealEffect(effects, "lingering_potion")) {
				event.setCancelled(true);
			}
		}
	}
*/


	@EventHandler(priority = EventPriority.HIGH)
	public void onAreaEffectCloudApply(AreaEffectCloudApplyEvent event) {
		if (event.getEntity().hasCustomEffects()) {

			List<PotionEffect> effects = event.getEntity().getCustomEffects();

			if (hasInvalidHealEffect(effects, "Effect-Cloud (lingering_potion or mob-spawn with entity-tag 'effects')")) {
				event.getEntity().removeCustomEffect(PotionEffectType.HEAL);
				event.setCancelled(true);
			}
		}
	}


	// potion check
	public boolean hasInvalidHealEffect(Collection<PotionEffect> effects, String sourceItemName) {
		for (PotionEffect effect : effects) {

			// "instant_health", Effect ID: 6
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