package com.aearost.aranarthcore.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class TrailUtils {

	private static List<ItemStack> saint1Trails;
	private static List<ItemStack> saint2Trails;
	private static List<ItemStack> saint3Trails;

	public TrailUtils() {
		saint1Trails = new ArrayList<>();
		saint2Trails = new ArrayList<>();
		saint3Trails = new ArrayList<>();
		initializeArrows();
		initializeEmptyAndExit();
	}

	private void initializeArrows() {

		// Initialize Base Arrow Items
		ItemStack gray = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta grayMeta = (PotionMeta) gray.getItemMeta();
		grayMeta.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER));
		gray.setItemMeta(grayMeta);

		ItemStack purple = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta purpleMeta = (PotionMeta) purple.getItemMeta();
		purpleMeta.setBasePotionData(new PotionData(PotionType.REGEN));
		purple.setItemMeta(purpleMeta);

		ItemStack lime = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta limeMeta = (PotionMeta) lime.getItemMeta();
		limeMeta.setBasePotionData(new PotionData(PotionType.JUMP));
		lime.setItemMeta(limeMeta);

		ItemStack white = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta whiteMeta = (PotionMeta) white.getItemMeta();
		whiteMeta.setBasePotionData(new PotionData(PotionType.SLOW_FALLING));
		white.setItemMeta(whiteMeta);

		ItemStack black = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta blackMeta = (PotionMeta) black.getItemMeta();
		blackMeta.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE));
		black.setItemMeta(blackMeta);

		ItemStack green = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta greenMeta = (PotionMeta) green.getItemMeta();
		greenMeta.setBasePotionData(new PotionData(PotionType.LUCK));
		green.setItemMeta(greenMeta);

		ItemStack blue = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta blueMeta = (PotionMeta) blue.getItemMeta();
		blueMeta.setBasePotionData(new PotionData(PotionType.WATER_BREATHING));
		blue.setItemMeta(blueMeta);

		ItemStack orange = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta orangeMeta = (PotionMeta) orange.getItemMeta();
		orangeMeta.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE));
		orange.setItemMeta(orangeMeta);

		ItemStack red = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta redMeta = (PotionMeta) red.getItemMeta();
		redMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
		red.setItemMeta(redMeta);

		ItemStack lightBlue = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta lightBlueMeta = (PotionMeta) lightBlue.getItemMeta();
		lightBlueMeta.setBasePotionData(new PotionData(PotionType.SPEED));
		lightBlue.setItemMeta(lightBlueMeta);

		ItemStack darkGray = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta darkGrayMeta = (PotionMeta) darkGray.getItemMeta();
		darkGrayMeta.setBasePotionData(new PotionData(PotionType.SLOWNESS));
		darkGray.setItemMeta(darkGrayMeta);

		ItemStack lightGray = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta lightGrayMeta = (PotionMeta) lightGray.getItemMeta();
		lightGrayMeta.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
		lightGray.setItemMeta(lightGrayMeta);

		ItemStack darkBlue = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta darkBlueMeta = (PotionMeta) darkBlue.getItemMeta();
		darkBlueMeta.setBasePotionData(new PotionData(PotionType.NIGHT_VISION));
		darkBlue.setItemMeta(darkBlueMeta);

		// Initialize Trails Options
		ItemStack ash = black.clone();
		ItemMeta ashMeta = ash.getItemMeta();
		ashMeta.setDisplayName(ChatUtils.translateToColor("&8&lAsh"));
		ash.setItemMeta(ashMeta);
		saint1Trails.add(ash);
		saint2Trails.add(ash);
		saint3Trails.add(ash);

		ItemStack bubblePop = lightBlue.clone();
		ItemMeta bubblePopMeta = bubblePop.getItemMeta();
		bubblePopMeta.setDisplayName(ChatUtils.translateToColor("&a&lBubble Pop"));
		bubblePop.setItemMeta(bubblePopMeta);
		saint3Trails.add(bubblePop);

		ItemStack campfireCosySmoke = gray.clone();
		ItemMeta campfireCosySmokeMeta = campfireCosySmoke.getItemMeta();
		campfireCosySmokeMeta.setDisplayName(ChatUtils.translateToColor("&8&lCampfire Cosy Smoke"));
		campfireCosySmoke.setItemMeta(campfireCosySmokeMeta);
		saint3Trails.add(campfireCosySmoke);

		ItemStack crimsonSpore = red.clone();
		ItemMeta crimsonSporeMeta = crimsonSpore.getItemMeta();
		crimsonSporeMeta.setDisplayName(ChatUtils.translateToColor("&c&lCrimson Spore"));
		crimsonSpore.setItemMeta(crimsonSporeMeta);
		saint3Trails.add(crimsonSpore);

		ItemStack crit = orange.clone();
		ItemMeta critMeta = crit.getItemMeta();
		critMeta.setDisplayName(ChatUtils.translateToColor("&6&lCrit"));
		crit.setItemMeta(critMeta);
		saint2Trails.add(crit);
		saint3Trails.add(crit);

		ItemStack critMagic = purple.clone();
		ItemMeta critMagicMeta = critMagic.getItemMeta();
		critMagicMeta.setDisplayName(ChatUtils.translateToColor("&5&lCrit Magic"));
		critMagic.setItemMeta(critMagicMeta);
		saint3Trails.add(critMagic);

		ItemStack damageIndicator = black.clone();
		ItemMeta damageIndicatorMeta = damageIndicator.getItemMeta();
		damageIndicatorMeta.setDisplayName(ChatUtils.translateToColor("&8&lDamage Indicator"));
		damageIndicator.setItemMeta(damageIndicatorMeta);
		saint3Trails.add(damageIndicator);

		ItemStack dripLava = orange.clone();
		ItemMeta dripLavaMeta = dripLava.getItemMeta();
		dripLavaMeta.setDisplayName(ChatUtils.translateToColor("&6&lDrip Lava"));
		dripLava.setItemMeta(dripLavaMeta);
		saint2Trails.add(dripLava);
		saint3Trails.add(dripLava);

		ItemStack dripWater = blue.clone();
		ItemMeta dripWaterMeta = dripWater.getItemMeta();
		dripWaterMeta.setDisplayName(ChatUtils.translateToColor("&1&lDrip Water"));
		dripWater.setItemMeta(dripWaterMeta);
		saint2Trails.add(dripWater);
		saint3Trails.add(dripWater);

		ItemStack enchantmentTable = white.clone();
		ItemMeta enchantmentTableMeta = enchantmentTable.getItemMeta();
		enchantmentTableMeta.setDisplayName(ChatUtils.translateToColor("&f&lEnchantment Table"));
		enchantmentTable.setItemMeta(enchantmentTableMeta);
		saint3Trails.add(enchantmentTable);

		ItemStack endRod = white.clone();
		ItemMeta endRodMeta = endRod.getItemMeta();
		endRodMeta.setDisplayName(ChatUtils.translateToColor("&f&lEnd Rod"));
		endRod.setItemMeta(endRodMeta);
		saint2Trails.add(endRod);
		saint3Trails.add(endRod);

		ItemStack explosionLarge = lightGray.clone();
		ItemMeta explosionLargeMeta = explosionLarge.getItemMeta();
		explosionLargeMeta.setDisplayName(ChatUtils.translateToColor("&7&lExplosion (Large)"));
		explosionLarge.setItemMeta(explosionLargeMeta);
		saint3Trails.add(explosionLarge);

		ItemStack explosionNormal = lightGray.clone();
		ItemMeta explosionNormalMeta = explosionNormal.getItemMeta();
		explosionNormalMeta.setDisplayName(ChatUtils.translateToColor("&7&lExplosion (Normal)"));
		explosionNormal.setItemMeta(explosionNormalMeta);
		saint2Trails.add(explosionNormal);
		saint3Trails.add(explosionNormal);

		ItemStack fallingHoney = orange.clone();
		ItemMeta fallingHoneyMeta = fallingHoney.getItemMeta();
		fallingHoneyMeta.setDisplayName(ChatUtils.translateToColor("&6&lFalling Honey"));
		fallingHoney.setItemMeta(fallingHoneyMeta);
		saint3Trails.add(fallingHoney);

		ItemStack fallingNectar = white.clone();
		ItemMeta fallingNectarMeta = fallingNectar.getItemMeta();
		fallingNectarMeta.setDisplayName(ChatUtils.translateToColor("&f&lFalling Nectar"));
		fallingNectar.setItemMeta(fallingNectarMeta);
		saint3Trails.add(fallingNectar);

		ItemStack fireworksSpark = white.clone();
		ItemMeta fireworksSparkMeta = fireworksSpark.getItemMeta();
		fireworksSparkMeta.setDisplayName(ChatUtils.translateToColor("&f&lFireworks Spark"));
		fireworksSpark.setItemMeta(fireworksSparkMeta);
		saint2Trails.add(fireworksSpark);
		saint3Trails.add(fireworksSpark);

		ItemStack flame = red.clone();
		ItemMeta flameMeta = flame.getItemMeta();
		flameMeta.setDisplayName(ChatUtils.translateToColor("&4&lFlame"));
		flame.setItemMeta(flameMeta);
		saint2Trails.add(flame);
		saint3Trails.add(flame);

		ItemStack heart = red.clone();
		ItemMeta heartMeta = heart.getItemMeta();
		heartMeta.setDisplayName(ChatUtils.translateToColor("&4&lHeart"));
		heart.setItemMeta(heartMeta);
		saint2Trails.add(heart);
		saint3Trails.add(heart);

		ItemStack lava = orange.clone();
		ItemMeta lavaMeta = lava.getItemMeta();
		lavaMeta.setDisplayName(ChatUtils.translateToColor("&6&lLava"));
		lava.setItemMeta(lavaMeta);
		saint3Trails.add(lava);

		ItemStack nautilus = darkBlue.clone();
		ItemMeta nautilusMeta = nautilus.getItemMeta();
		nautilusMeta.setDisplayName(ChatUtils.translateToColor("&3&lNautilus"));
		nautilus.setItemMeta(nautilusMeta);
		saint2Trails.add(nautilus);
		saint3Trails.add(nautilus);

		ItemStack note = lime.clone();
		ItemMeta noteMeta = note.getItemMeta();
		noteMeta.setDisplayName(ChatUtils.translateToColor("&a&lNote"));
		note.setItemMeta(noteMeta);
		saint2Trails.add(note);
		saint3Trails.add(note);

		ItemStack reversePortal = purple.clone();
		ItemMeta reversePortalMeta = reversePortal.getItemMeta();
		reversePortalMeta.setDisplayName(ChatUtils.translateToColor("&d&lReverse Portal"));
		reversePortal.setItemMeta(reversePortalMeta);
		saint1Trails.add(reversePortal);
		saint2Trails.add(reversePortal);
		saint3Trails.add(reversePortal);

		ItemStack smokeLarge = black.clone();
		ItemMeta smokeLargeMeta = smokeLarge.getItemMeta();
		smokeLargeMeta.setDisplayName(ChatUtils.translateToColor("&8&lSmoke (Large)"));
		smokeLarge.setItemMeta(smokeLargeMeta);
		saint3Trails.add(smokeLarge);

		ItemStack smokeNormal = black.clone();
		ItemMeta smokeNormalMeta = smokeNormal.getItemMeta();
		smokeNormalMeta.setDisplayName(ChatUtils.translateToColor("&8&lSmoke (Normal)"));
		smokeNormal.setItemMeta(smokeNormalMeta);
		saint2Trails.add(smokeNormal);
		saint3Trails.add(smokeNormal);

		ItemStack sneeze = lime.clone();
		ItemMeta sneezeMeta = sneeze.getItemMeta();
		sneezeMeta.setDisplayName(ChatUtils.translateToColor("&a&lSneeze"));
		sneeze.setItemMeta(sneezeMeta);
		saint1Trails.add(sneeze);
		saint2Trails.add(sneeze);
		saint3Trails.add(sneeze);

		ItemStack snowball = lightBlue.clone();
		ItemMeta snowballMeta = snowball.getItemMeta();
		snowballMeta.setDisplayName(ChatUtils.translateToColor("&b&lSnowball"));
		snowball.setItemMeta(snowballMeta);
		saint1Trails.add(snowball);
		saint2Trails.add(snowball);
		saint3Trails.add(snowball);

		ItemStack soul = darkGray.clone();
		ItemMeta soulMeta = soul.getItemMeta();
		soulMeta.setDisplayName(ChatUtils.translateToColor("&8&lSoul"));
		soul.setItemMeta(soulMeta);
		saint3Trails.add(soul);

		ItemStack soulFireFlame = lightBlue.clone();
		ItemMeta soulFireFlameMeta = soulFireFlame.getItemMeta();
		soulFireFlameMeta.setDisplayName(ChatUtils.translateToColor("&b&lSoul Fire Flame"));
		soulFireFlame.setItemMeta(soulFireFlameMeta);
		saint3Trails.add(soulFireFlame);

		ItemStack spell = white.clone();
		ItemMeta spellMeta = spell.getItemMeta();
		spellMeta.setDisplayName(ChatUtils.translateToColor("&f&lSpell"));
		spell.setItemMeta(soulMeta);
		saint2Trails.add(spell);
		saint3Trails.add(spell);

		ItemStack spellInstant = white.clone();
		ItemMeta spellInstantMeta = spellInstant.getItemMeta();
		spellInstantMeta.setDisplayName(ChatUtils.translateToColor("&f&lSpell Instant"));
		spellInstant.setItemMeta(spellInstantMeta);
		saint3Trails.add(spellInstant);

		ItemStack spellMob = black.clone();
		ItemMeta spellMobMeta = spellMob.getItemMeta();
		spellMobMeta.setDisplayName(ChatUtils.translateToColor("&8&lSpell Mob"));
		spellMob.setItemMeta(spellMobMeta);
		saint1Trails.add(spellMob);
		saint2Trails.add(spellMob);
		saint3Trails.add(spellMob);

		ItemStack spellWitch = purple.clone();
		ItemMeta spellWitchMeta = spellWitch.getItemMeta();
		spellWitchMeta.setDisplayName(ChatUtils.translateToColor("&5&lSpell Witch"));
		spellWitch.setItemMeta(spellWitchMeta);
		saint2Trails.add(spellWitch);
		saint3Trails.add(spellWitch);

		ItemStack squidInk = black.clone();
		ItemMeta squidInkMeta = squidInk.getItemMeta();
		squidInkMeta.setDisplayName(ChatUtils.translateToColor("&8&lSquid Ink"));
		squidInk.setItemMeta(squidInkMeta);
		saint3Trails.add(squidInk);

		ItemStack totem = green.clone();
		ItemMeta totemMeta = totem.getItemMeta();
		totemMeta.setDisplayName(ChatUtils.translateToColor("&2&lTotem"));
		totem.setItemMeta(totemMeta);
		saint1Trails.add(totem);
		saint2Trails.add(totem);
		saint3Trails.add(totem);

		ItemStack townAura = darkGray.clone();
		ItemMeta townAuraMeta = townAura.getItemMeta();
		townAuraMeta.setDisplayName(ChatUtils.translateToColor("&8&lTown Aura"));
		townAura.setItemMeta(townAuraMeta);
		saint1Trails.add(townAura);
		saint2Trails.add(townAura);
		saint3Trails.add(townAura);

		ItemStack villagerAngry = gray.clone();
		ItemMeta villagerAngryMeta = villagerAngry.getItemMeta();
		villagerAngryMeta.setDisplayName(ChatUtils.translateToColor("&7&lVillager Angry"));
		villagerAngry.setItemMeta(villagerAngryMeta);
		saint3Trails.add(villagerAngry);

		ItemStack villagerHappy = lime.clone();
		ItemMeta villagerHappyMeta = villagerHappy.getItemMeta();
		villagerHappyMeta.setDisplayName(ChatUtils.translateToColor("&a&lVillager Happy"));
		villagerHappy.setItemMeta(villagerHappyMeta);
		saint3Trails.add(villagerHappy);

		ItemStack warpedSpore = darkBlue.clone();
		ItemMeta warpedSporeMeta = warpedSpore.getItemMeta();
		warpedSporeMeta.setDisplayName(ChatUtils.translateToColor("&3&lWarped Spore"));
		warpedSpore.setItemMeta(warpedSporeMeta);
		saint3Trails.add(warpedSpore);

		ItemStack waterSplash = blue.clone();
		ItemMeta waterSplashMeta = waterSplash.getItemMeta();
		waterSplashMeta.setDisplayName(ChatUtils.translateToColor("&9&lWater Splash"));
		waterSplash.setItemMeta(waterSplashMeta);
		saint1Trails.add(waterSplash);
		saint2Trails.add(waterSplash);
		saint3Trails.add(waterSplash);

		ItemStack waterWake = blue.clone();
		ItemMeta waterWakeMeta = waterWake.getItemMeta();
		waterWakeMeta.setDisplayName(ChatUtils.translateToColor("&9&lWater Wake"));
		waterWake.setItemMeta(waterWakeMeta);
		saint2Trails.add(waterWake);
		saint3Trails.add(waterWake);

		ItemStack whiteAsh = lightGray.clone();
		ItemMeta whiteAshMeta = whiteAsh.getItemMeta();
		whiteAshMeta.setDisplayName(ChatUtils.translateToColor("&7&lWhite Ash"));
		whiteAsh.setItemMeta(whiteAshMeta);
		saint1Trails.add(whiteAsh);
		saint2Trails.add(whiteAsh);
		saint3Trails.add(whiteAsh);

	}

	private void initializeEmptyAndExit() {
		// Initialize items
		ItemStack none = new ItemStack(Material.AIR);
		ItemStack exit = new ItemStack(Material.BARRIER);
		ItemMeta exitMeta = exit.getItemMeta();
		exitMeta.setDisplayName(ChatUtils.translateToColor("&c&lExit"));
		exit.setItemMeta(exitMeta);

		// Fills rest of the last row of available trails
		saint2Trails.add(none);
		saint2Trails.add(none);
		saint2Trails.add(none);
		saint2Trails.add(none);

		saint3Trails.add(none);
		saint3Trails.add(none);
		saint3Trails.add(none);
		saint3Trails.add(none);

		// Adds proper
		saint1Trails.add(9, none);
		saint1Trails.add(10, none);
		saint1Trails.add(11, none);
		saint1Trails.add(12, none);
		saint1Trails.add(13, exit);
		saint1Trails.add(14, none);
		saint1Trails.add(15, none);
		saint1Trails.add(16, none);
		saint1Trails.add(17, none);

		saint2Trails.add(27, none);
		saint2Trails.add(28, none);
		saint2Trails.add(29, none);
		saint2Trails.add(30, none);
		saint2Trails.add(31, exit);
		saint2Trails.add(32, none);
		saint2Trails.add(33, none);
		saint2Trails.add(34, none);
		saint2Trails.add(35, none);

		saint3Trails.add(45, none);
		saint3Trails.add(46, none);
		saint3Trails.add(47, none);
		saint3Trails.add(48, none);
		saint3Trails.add(49, exit);
		saint3Trails.add(50, none);
		saint3Trails.add(51, none);
		saint3Trails.add(52, none);
		saint3Trails.add(53, none);
	}

	public static List<ItemStack> getSaint1Trails() {
		return saint1Trails;
	}

	public static List<ItemStack> getSaint2Trails() {
		return saint2Trails;
	}

	public static List<ItemStack> getSaint3Trails() {
		return saint3Trails;
	}

}
