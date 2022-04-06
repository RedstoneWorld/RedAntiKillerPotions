package de.redstoneworld.redantikillerpotions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class PotionListener implements Listener {
	protected final RedAntiKillerPotions plugin;

	public PotionListener(RedAntiKillerPotions plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPotionSplash(PotionSplashEvent event) {
		Collection<PotionEffect> effects = event.getPotion().getEffects();
		boolean hasBypassPermissions = false;
		if (event.getPotion().getShooter() instanceof Player)
			hasBypassPermissions = hasShootBypassPermissions((Player) event.getPotion().getShooter());
		if (hasInvalidHealEffect(effects, "splash_potion")
			&& !hasBypassPermissions)
			event.setCancelled(true);
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
		if (!event.getEntity().hasCustomEffects()) return;
		Collection<PotionEffect> effects = event.getEntity().getCustomEffects();
		if (!hasInvalidHealEffect(effects, "Effect-Cloud (lingering_potion or mob-spawn with entity-tag 'effects')"))
			return;
		event.getEntity().removeCustomEffect(PotionEffectType.HEAL);
	}

	// potion check
	public boolean hasInvalidHealEffect(Collection<PotionEffect> effects, String sourceItemName) {
		for (PotionEffect effect : effects) {
			// "instant_health", Effect ID: 6
			if (!effect.getType().equals(PotionEffectType.HEAL)) continue;
			int effectAmplifier = effect.getAmplifier();
			if (effectAmplifier <= 124) continue;
			plugin.getLogger().info("Detected potion effect 'HEAL' from " + sourceItemName + " with a amplifier of " + effectAmplifier + ".");
			return true;
		}
		return false;
	}

	// bypass permission check:
	// only possible for splash potions
	public boolean hasShootBypassPermissions(Player p) {
		return p.hasPermission("rwm.redantikillerpotions.shoot-bypass");
	}
}