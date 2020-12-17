package com.aearost.aranarthcore;

import java.text.ParseException;
import java.util.Date;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.aearost.aranarthcore.commands.CommandAC;
import com.aearost.aranarthcore.commands.CommandACCompleter;
import com.aearost.aranarthcore.commands.CommandBalance;
import com.aearost.aranarthcore.commands.CommandBalanceCompleter;
import com.aearost.aranarthcore.commands.CommandBalancetop;
import com.aearost.aranarthcore.commands.CommandBalancetopCompleter;
import com.aearost.aranarthcore.commands.CommandBroadcast;
import com.aearost.aranarthcore.commands.CommandBuy;
import com.aearost.aranarthcore.commands.CommandEco;
import com.aearost.aranarthcore.commands.CommandEcoCompleter;
import com.aearost.aranarthcore.commands.CommandAldara;
import com.aearost.aranarthcore.commands.CommandPay;
import com.aearost.aranarthcore.commands.CommandPayCompleter;
import com.aearost.aranarthcore.commands.CommandRanks;
import com.aearost.aranarthcore.commands.CommandRanksCompleter;
import com.aearost.aranarthcore.commands.CommandRules;
import com.aearost.aranarthcore.commands.CommandRulesCompleter;
import com.aearost.aranarthcore.commands.CommandTrails;
import com.aearost.aranarthcore.event.ArenaDrops;
import com.aearost.aranarthcore.event.ArmorStandEquipCancel;
import com.aearost.aranarthcore.event.AvatarDeath;
import com.aearost.aranarthcore.event.PlayerJoinServer;
import com.aearost.aranarthcore.event.RanksClick;
import com.aearost.aranarthcore.event.ShopCreate;
import com.aearost.aranarthcore.event.ShopDestroy;
import com.aearost.aranarthcore.event.ShopOpen;
import com.aearost.aranarthcore.event.ShopSignClick;
import com.aearost.aranarthcore.event.TrailArrowClick;
import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.recipes.RecipeAcaciaLogFromWood;
import com.aearost.aranarthcore.recipes.RecipeBirchLogFromWood;
import com.aearost.aranarthcore.recipes.RecipeCrimsonLogFromWood;
import com.aearost.aranarthcore.recipes.RecipeDarkOakLogFromWood;
import com.aearost.aranarthcore.recipes.RecipeJungleLogFromWood;
import com.aearost.aranarthcore.recipes.RecipeOakLogFromWood;
import com.aearost.aranarthcore.recipes.RecipeSpruceLogFromWood;
import com.aearost.aranarthcore.recipes.RecipeWarpedLogFromWood;
import com.aearost.aranarthcore.utils.AranarthAnnouncer;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.AranarthShopUtils;
import com.aearost.aranarthcore.utils.ChatUtils;
import com.aearost.aranarthcore.utils.PersistenceUtils;
import com.aearost.aranarthcore.utils.TrailUtils;

public class AranarthCore extends JavaPlugin {
	
	@Override
	public void onEnable() {		

		// Initialize Utils
		new AranarthShopUtils(this);
		new AranarthPlayerUtils(true);
		new AranarthAnnouncer(this);
		new TrailUtils();
		
		// Display Holograms
		AranarthShopUtils.displayAllPlayerShopHolograms();

		// Initialize Events
		new ArenaDrops(this);
		new ArmorStandEquipCancel(this);
		new AvatarDeath(this);
		new PlayerJoinServer(this);
		new RanksClick(this);
		new ShopCreate(this);
		new ShopDestroy(this);
		new ShopOpen(this);
		new ShopSignClick(this);
		new TrailArrowClick(this);

		// Initialize Commands
		getCommand("ac").setExecutor(new CommandAC());
		getCommand("ac").setTabCompleter(new CommandACCompleter());
		getCommand("aldara").setExecutor(new CommandAldara());
		getCommand("balance").setExecutor(new CommandBalance());
		getCommand("balance").setTabCompleter(new CommandBalanceCompleter());
		getCommand("broadcast").setExecutor(new CommandBroadcast());
		getCommand("balancetop").setExecutor(new CommandBalancetop());
		getCommand("balancetop").setTabCompleter(new CommandBalancetopCompleter());
		getCommand("buy").setExecutor(new CommandBuy());
		getCommand("eco").setExecutor(new CommandEco());
		getCommand("eco").setTabCompleter(new CommandEcoCompleter());
		getCommand("pay").setExecutor(new CommandPay());
		getCommand("pay").setTabCompleter(new CommandPayCompleter());
		getCommand("ranks").setExecutor(new CommandRanks());
		getCommand("ranks").setTabCompleter(new CommandRanksCompleter());
		getCommand("rules").setExecutor(new CommandRules());
		getCommand("rules").setTabCompleter(new CommandRulesCompleter());
		getCommand("trails").setExecutor(new CommandTrails());
		
		// Initialize Recipes
		new RecipeAcaciaLogFromWood(this);
		new RecipeBirchLogFromWood(this);
		new RecipeCrimsonLogFromWood(this);
		new RecipeDarkOakLogFromWood(this);
		new RecipeJungleLogFromWood(this);
		new RecipeOakLogFromWood(this);
		new RecipeSpruceLogFromWood(this);
		new RecipeWarpedLogFromWood(this);
		
		
		// Update the files every 30 minutes to protect from loss of data
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				PersistenceUtils.writePlayersToFile();
				PersistenceUtils.writeShopSignsToFile();
				Bukkit.getLogger().info("Players and shops have been written to file");
			}
		}, 36000, 36000);
		//This is long and can be simplified for sure but it works in time limiting the avatar
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				try {
					//First get the date the last avatar was born
					Date readDate = PersistenceUtils.readDateFromFile();
					if(AranarthPlayerUtils.isAvatarTimeLimit(readDate)) //check if time is up
					{
						//start replacing the avatar (stolen from AvatarDeath.java for consistency)
						Player[] onlinePlayers = new Player[Bukkit.getOnlinePlayers().size()];
						Bukkit.getOnlinePlayers().toArray(onlinePlayers);
						Random random = new Random();
						boolean isNewAvatarSelected = false;
						
						if (onlinePlayers.length > 1) {
							while (!isNewAvatarSelected) {
								Player player = onlinePlayers[random.nextInt(onlinePlayers.length)];
								AranarthPlayer newAvatar = AranarthPlayerUtils.getPlayer(player);
								Player currentAvatar = Bukkit.getPlayer(AranarthPlayerUtils.zukoSearch());
								if (!newAvatar.getAvatarStatus().equals("current")) {
									AranarthPlayerUtils.replaceAvatar(player);
									isNewAvatarSelected = true;

									String previousAvatar = AranarthPlayerUtils.replaceAvatar(player);
									Bukkit.broadcastMessage(ChatUtils.translateToColor(
											"&5&lAvatar &d&l" + currentAvatar.getName() + " &5&lhas passed away..."));
									Bukkit.broadcastMessage(ChatUtils.translateToColor(
											"&5&lWelcome their successor, Avatar &d&l" + player.getName() + "&5&l!"));

									for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
										onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH, 1.3F,
												2.0F);
									}
									ChatUtils.updatePlayerGroupsAndPrefix(Bukkit.getOfflinePlayer(currentAvatar.getUniqueId()));
									ChatUtils.updatePlayerGroupsAndPrefix(
											Bukkit.getOfflinePlayer(AranarthPlayerUtils.getUUID(previousAvatar)));
								}
							}

						}
						else //if no one is online then just take away the avatar stuff but don't set a new one
						{
							Player currentAvatar = Bukkit.getPlayer(AranarthPlayerUtils.zukoSearch());
							AranarthPlayerUtils.replaceAvatar(null);
							Bukkit.broadcastMessage(ChatUtils.translateToColor(
									"&5&lAvatar &d&l" + currentAvatar.getName() + " &5&lhas passed away..."));
							for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
								onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH, 1.3F,
										2.0F);
							}
							ChatUtils.updatePlayerGroupsAndPrefix(Bukkit.getOfflinePlayer(currentAvatar.getUniqueId()));
						}
					}
				} catch (ParseException e) { //if the date is unreadable for some reason
					Bukkit.getLogger().info("No date to read or the file is corrupted :/");
					e.printStackTrace();
				}
			}
	
		}, 72000, 72000); //check every hour (it will only execute the majority of code every 7 days)
	}

	@Override
	public void onDisable() {

		new AranarthPlayerUtils(false);
		AranarthShopUtils.removeAllPlayerShopHolograms();

	}

}