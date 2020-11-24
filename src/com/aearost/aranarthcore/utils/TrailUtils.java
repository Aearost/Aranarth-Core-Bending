package com.aearost.aranarthcore.utils;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class TrailUtils {

	private static HashMap<Trail, ItemStack> saint1Trails;
	private static HashMap<Trail, ItemStack> saint2Trails;
	private static HashMap<Trail, ItemStack> saint3Trails;

	public TrailUtils() {
		saint1Trails = new HashMap<>();
		saint2Trails = new HashMap<>();
		saint3Trails = new HashMap<>();
		initializeArrows();
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
		saint1Trails.put(Trail.ASH, ash);
		saint2Trails.put(Trail.ASH, ash);
		saint3Trails.put(Trail.ASH, ash);
		
		ItemStack bubblePop = lightBlue.clone();
		ItemMeta bubblePopMeta = bubblePop.getItemMeta();
		bubblePopMeta.setDisplayName(ChatUtils.translateToColor("&a&lBubble Pop"));
		bubblePop.setItemMeta(bubblePopMeta);
		saint3Trails.put(Trail.BUBBLE_POP, bubblePop);
		
		ItemStack campfireCosySmoke = gray.clone();
		ItemMeta campfireCosySmokeMeta = campfireCosySmoke.getItemMeta();
		campfireCosySmokeMeta.setDisplayName(ChatUtils.translateToColor("&8&lCampfire Cosy Smoke"));
		campfireCosySmoke.setItemMeta(campfireCosySmokeMeta);
		saint3Trails.put(Trail.CAMPFIRE_COSY_SMOKE, campfireCosySmoke);
		
		ItemStack crimsonSpore = red.clone();
		ItemMeta crimsonSporeMeta = crimsonSpore.getItemMeta();
		crimsonSporeMeta.setDisplayName(ChatUtils.translateToColor("&c&lCrimson Spore"));
		crimsonSpore.setItemMeta(crimsonSporeMeta);
		saint3Trails.put(Trail.CRIMSON_SPORE, crimsonSpore);
		
		ItemStack crit = orange.clone();
		ItemMeta critMeta = crit.getItemMeta();
		critMeta.setDisplayName(ChatUtils.translateToColor("&6&lCrit"));
		crit.setItemMeta(critMeta);
		saint2Trails.put(Trail.CRIT, crit);
		saint3Trails.put(Trail.CRIT, crit);
		
		ItemStack critMagic = purple.clone();
		ItemMeta critMagicMeta = critMagic.getItemMeta();
		critMagicMeta.setDisplayName(ChatUtils.translateToColor("&5&lCrit Magic"));
		critMagic.setItemMeta(critMagicMeta);
		saint3Trails.put(Trail.CRIT_MAGIC, critMagic);
		
		ItemStack damageIndicator = black.clone();
		ItemMeta damageIndicatorMeta = damageIndicator.getItemMeta();
		damageIndicatorMeta.setDisplayName(ChatUtils.translateToColor("&8&lDamage Indicator"));
		damageIndicator.setItemMeta(damageIndicatorMeta);
		saint3Trails.put(Trail.DAMAGE_INDICATOR, damageIndicator);
		
		ItemStack dripLava = orange.clone();
		ItemMeta dripLavaMeta = dripLava.getItemMeta();
		dripLavaMeta.setDisplayName(ChatUtils.translateToColor("&6&lDrip Lava"));
		dripLava.setItemMeta(dripLavaMeta);
		saint2Trails.put(Trail.DRIP_LAVA, dripLava);
		saint3Trails.put(Trail.DRIP_LAVA, dripLava);
		
		ItemStack dripWater = blue.clone();
		ItemMeta dripWaterMeta = dripWater.getItemMeta();
		dripWaterMeta.setDisplayName(ChatUtils.translateToColor("&1&lDrip Water"));
		dripWater.setItemMeta(dripWaterMeta);
		saint2Trails.put(Trail.DRIP_WATER, dripWater);
		saint3Trails.put(Trail.DRIP_WATER, dripWater);
		
		ItemStack enchantmentTable = white.clone();
		ItemMeta enchantmentTableMeta = enchantmentTable.getItemMeta();
		enchantmentTableMeta.setDisplayName(ChatUtils.translateToColor("&f&lEnchantment Table"));
		enchantmentTable.setItemMeta(enchantmentTableMeta);
		saint3Trails.put(Trail.ENCHANTMENT_TABLE, enchantmentTable);
		
		ItemStack endRod = white.clone();
		ItemMeta endRodMeta = endRod.getItemMeta();
		endRodMeta.setDisplayName(ChatUtils.translateToColor("&f&lEnd Rod"));
		endRod.setItemMeta(endRodMeta);
		saint2Trails.put(Trail.END_ROD, endRod);
		saint3Trails.put(Trail.END_ROD, endRod);
		
		ItemStack explosionLarge = lightGray.clone();
		ItemMeta explosionLargeMeta = explosionLarge.getItemMeta();
		explosionLargeMeta.setDisplayName(ChatUtils.translateToColor("&7&lExplosion (Large)"));
		explosionLarge.setItemMeta(explosionLargeMeta);
		saint3Trails.put(Trail.EXPLOSION_LARGE, explosionLarge);
		
		ItemStack explosionNormal = lightGray.clone();
		ItemMeta explosionNormalMeta = explosionNormal.getItemMeta();
		explosionNormalMeta.setDisplayName(ChatUtils.translateToColor("&7&lExplosion (Normal)"));
		explosionNormal.setItemMeta(explosionNormalMeta);
		saint2Trails.put(Trail.EXPLOSION_NORMAL, explosionNormal);
		saint3Trails.put(Trail.EXPLOSION_NORMAL, explosionNormal);
		
		ItemStack fallingHoney = orange.clone();
		ItemMeta fallingHoneyMeta = fallingHoney.getItemMeta();
		fallingHoneyMeta.setDisplayName(ChatUtils.translateToColor("&6&lFalling Honey"));
		fallingHoney.setItemMeta(fallingHoneyMeta);
		saint3Trails.put(Trail.FALLING_HONEY, fallingHoney);
		
		ItemStack fallingNectar = white.clone();
		ItemMeta fallingNectarMeta = fallingNectar.getItemMeta();
		fallingNectarMeta.setDisplayName(ChatUtils.translateToColor("&f&lFalling Nectar"));
		fallingNectar.setItemMeta(fallingNectarMeta);
		saint3Trails.put(Trail.FALLING_NECTAR, fallingNectar);
		
		ItemStack fireworksSpark = white.clone();
		ItemMeta fireworksSparkMeta = fireworksSpark.getItemMeta();
		fireworksSparkMeta.setDisplayName(ChatUtils.translateToColor("&f&lFireworks Spark"));
		fireworksSpark.setItemMeta(fireworksSparkMeta);
		saint2Trails.put(Trail.FIREWORKS_SPARK, fireworksSpark);
		saint3Trails.put(Trail.FIREWORKS_SPARK, fireworksSpark);
		
		ItemStack flame = red.clone();
		ItemMeta flameMeta = flame.getItemMeta();
		flameMeta.setDisplayName(ChatUtils.translateToColor("&4&lFlame"));
		flame.setItemMeta(flameMeta);
		saint2Trails.put(Trail.FLAME, flame);
		saint3Trails.put(Trail.FLAME, flame);
		
		ItemStack heart = red.clone();
		ItemMeta heartMeta = heart.getItemMeta();
		heartMeta.setDisplayName(ChatUtils.translateToColor("&4&lHeart"));
		heart.setItemMeta(heartMeta);
		saint2Trails.put(Trail.HEART, heart);
		saint3Trails.put(Trail.HEART, heart);
		
		ItemStack lava = orange.clone();
		ItemMeta lavaMeta = lava.getItemMeta();
		lavaMeta.setDisplayName(ChatUtils.translateToColor("&6&lLava"));
		lava.setItemMeta(lavaMeta);
		saint3Trails.put(Trail.LAVA, lava);
		
		ItemStack nautilus = darkBlue.clone();	
		ItemMeta nautilusMeta = nautilus.getItemMeta();
		nautilusMeta.setDisplayName(ChatUtils.translateToColor("&3&lNautilus"));
		nautilus.setItemMeta(nautilusMeta);
		saint2Trails.put(Trail.NAUTILUS, nautilus);
		saint3Trails.put(Trail.NAUTILUS, nautilus);
		
		ItemStack note = lime.clone();
		ItemMeta noteMeta = note.getItemMeta();
		noteMeta.setDisplayName(ChatUtils.translateToColor("&a&lNote"));
		note.setItemMeta(noteMeta);
		saint2Trails.put(Trail.NOTE, note);
		saint3Trails.put(Trail.NOTE, note);
		
		ItemStack reversePortal = purple.clone();
		ItemMeta reversePortalMeta = reversePortal.getItemMeta();
		reversePortalMeta.setDisplayName(ChatUtils.translateToColor("&d&lReverse Portal"));
		reversePortal.setItemMeta(reversePortalMeta);
		saint1Trails.put(Trail.REVERSE_PORTAL, reversePortal);
		saint2Trails.put(Trail.REVERSE_PORTAL, reversePortal);
		saint3Trails.put(Trail.REVERSE_PORTAL, reversePortal);
		
		ItemStack smokeLarge = black.clone();
		ItemMeta smokeLargeMeta = smokeLarge.getItemMeta();
		smokeLargeMeta.setDisplayName(ChatUtils.translateToColor("&8&lSmoke (Large)"));
		smokeLarge.setItemMeta(smokeLargeMeta);
		saint3Trails.put(Trail.SMOKE_LARGE, smokeLarge);
		
		ItemStack smokeNormal = black.clone();
		ItemMeta smokeNormalMeta = smokeNormal.getItemMeta();
		smokeNormalMeta.setDisplayName(ChatUtils.translateToColor("&8&lSmoke (Normal)"));
		smokeNormal.setItemMeta(smokeNormalMeta);
		saint2Trails.put(Trail.SMOKE_NORMAL, smokeNormal);
		saint3Trails.put(Trail.SMOKE_NORMAL, smokeNormal);
		
		ItemStack sneeze = lime.clone();
		ItemMeta sneezeMeta = sneeze.getItemMeta();
		sneezeMeta.setDisplayName(ChatUtils.translateToColor("&a&lSneeze"));
		sneeze.setItemMeta(sneezeMeta);
		saint1Trails.put(Trail.SNEEZE, sneeze);
		saint2Trails.put(Trail.SNEEZE, sneeze);
		saint3Trails.put(Trail.SNEEZE, sneeze);
		
		ItemStack snowball = lightBlue.clone();
		ItemMeta snowballMeta = snowball.getItemMeta();
		snowballMeta.setDisplayName(ChatUtils.translateToColor("&b&lSnowball"));
		snowball.setItemMeta(snowballMeta);
		saint1Trails.put(Trail.SNOWBALL, snowball);
		saint2Trails.put(Trail.SNOWBALL, snowball);
		saint3Trails.put(Trail.SNOWBALL, snowball);
		
		ItemStack soul = darkGray.clone();
		ItemMeta soulMeta = soul.getItemMeta();
		soulMeta.setDisplayName(ChatUtils.translateToColor("&8&lSoul"));
		soul.setItemMeta(soulMeta);
		saint3Trails.put(Trail.SOUL, soul);
		
		ItemStack soulFireFlame = lightBlue.clone();
		ItemMeta soulFireFlameMeta = soulFireFlame.getItemMeta();
		soulFireFlameMeta.setDisplayName(ChatUtils.translateToColor("&b&lSoul Fire Flame"));
		soulFireFlame.setItemMeta(soulFireFlameMeta);
		saint3Trails.put(Trail.SOUL_FIRE_FLAME, soulFireFlame);
		
		ItemStack spell = white.clone();
		ItemMeta spellMeta = spell.getItemMeta();
		spellMeta.setDisplayName(ChatUtils.translateToColor("&f&lSpell"));
		spell.setItemMeta(soulMeta);
		saint2Trails.put(Trail.SPELL, spell);
		saint3Trails.put(Trail.SPELL, spell);
		
		ItemStack spellInstant = white.clone();
		ItemMeta spellInstantMeta = spellInstant.getItemMeta();
		spellInstantMeta.setDisplayName(ChatUtils.translateToColor("&f&lSpell Instant"));
		spellInstant.setItemMeta(spellInstantMeta);
		saint3Trails.put(Trail.SPELL_INSTANT, spellInstant);
		
		ItemStack spellMob = black.clone();
		ItemMeta spellMobMeta = spellMob.getItemMeta();
		spellMobMeta.setDisplayName(ChatUtils.translateToColor("&8&lSpell Mob"));
		spellMob.setItemMeta(spellMobMeta);
		saint1Trails.put(Trail.SPELL_MOB, spellMob);
		saint2Trails.put(Trail.SPELL_MOB, spellMob);
		saint3Trails.put(Trail.SPELL_MOB, spellMob);
		
		ItemStack spellWitch = purple.clone();
		ItemMeta spellWitchMeta = spellWitch.getItemMeta();
		spellWitchMeta.setDisplayName(ChatUtils.translateToColor("&5&lSpell Witch"));
		spellWitch.setItemMeta(spellWitchMeta);
		saint2Trails.put(Trail.SPELL_WITCH, spellWitch);
		saint3Trails.put(Trail.SPELL_WITCH, spellWitch);
		
		ItemStack squidInk = black.clone();
		ItemMeta squidInkMeta = squidInk.getItemMeta();
		squidInkMeta.setDisplayName(ChatUtils.translateToColor("&8&lSquid Ink"));
		squidInk.setItemMeta(squidInkMeta);
		saint3Trails.put(Trail.SQUID_INK, squidInk);
		
		ItemStack totem = green.clone();
		ItemMeta totemMeta = totem.getItemMeta();
		totemMeta.setDisplayName(ChatUtils.translateToColor("&2&lTotem"));
		totem.setItemMeta(totemMeta);
		saint1Trails.put(Trail.TOTEM, totem);
		saint2Trails.put(Trail.TOTEM, totem);
		saint3Trails.put(Trail.TOTEM, totem);
		
		ItemStack townAura = darkGray.clone();
		ItemMeta townAuraMeta = townAura.getItemMeta();
		townAuraMeta.setDisplayName(ChatUtils.translateToColor("&8&lTown Aura"));
		townAura.setItemMeta(townAuraMeta);
		saint1Trails.put(Trail.TOWN_AURA, townAura);
		saint2Trails.put(Trail.TOWN_AURA, townAura);
		saint3Trails.put(Trail.TOWN_AURA, townAura);
		
		ItemStack villagerAngry = gray.clone();
		ItemMeta villagerAngryMeta = villagerAngry.getItemMeta();
		villagerAngryMeta.setDisplayName(ChatUtils.translateToColor("&7&lVillager Angry"));
		villagerAngry.setItemMeta(villagerAngryMeta);
		saint3Trails.put(Trail.VILLAGER_ANGRY, villagerAngry);
		
		ItemStack villagerHappy = lime.clone();
		ItemMeta villagerHappyMeta = villagerHappy.getItemMeta();
		villagerHappyMeta.setDisplayName(ChatUtils.translateToColor("&a&lVillager Happy"));
		villagerHappy.setItemMeta(villagerHappyMeta);
		saint3Trails.put(Trail.VILLAGER_HAPPY, villagerHappy);
		
		ItemStack warpedSpore = darkBlue.clone();
		ItemMeta warpedSporeMeta = warpedSpore.getItemMeta();
		warpedSporeMeta.setDisplayName(ChatUtils.translateToColor("&3&lWarped Spore"));
		warpedSpore.setItemMeta(warpedSporeMeta);
		saint3Trails.put(Trail.WARPED_SPORE, warpedSpore);
		
		ItemStack waterSplash = blue.clone();
		ItemMeta waterSplashMeta = waterSplash.getItemMeta();
		waterSplashMeta.setDisplayName(ChatUtils.translateToColor("&9&lWater Splash"));
		waterSplash.setItemMeta(waterSplashMeta);
		saint1Trails.put(Trail.WATER_SPLASH, waterSplash);
		saint2Trails.put(Trail.WATER_SPLASH, waterSplash);
		saint3Trails.put(Trail.WATER_SPLASH, waterSplash);
		
		ItemStack waterWake = blue.clone();
		ItemMeta waterWakeMeta = waterWake.getItemMeta();
		waterWakeMeta.setDisplayName(ChatUtils.translateToColor("&9&lWater Wake"));
		waterWake.setItemMeta(waterWakeMeta);
		saint2Trails.put(Trail.WATER_WAKE, waterWake);
		saint3Trails.put(Trail.WATER_WAKE, waterWake);
		
		ItemStack whiteAsh = lightGray.clone();
		ItemMeta whiteAshMeta = whiteAsh.getItemMeta();
		whiteAshMeta.setDisplayName(ChatUtils.translateToColor("&7&lWhite Ash"));
		whiteAsh.setItemMeta(whiteAshMeta);
		saint1Trails.put(Trail.WHITE_ASH, whiteAsh);
		saint2Trails.put(Trail.WHITE_ASH, whiteAsh);
		saint3Trails.put(Trail.WHITE_ASH, whiteAsh);
		
	}

	public static HashMap<Trail, ItemStack> getSaint1Trails() {
		return saint1Trails;
	}

	public static HashMap<Trail, ItemStack> getSaint2Trails() {
		return saint2Trails;
	}

	public static HashMap<Trail, ItemStack> getSaint3Trails() {
		return saint3Trails;
	}
	
	

}
