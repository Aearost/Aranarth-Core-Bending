package com.aearost.aranarthcore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.aearost.aranarthcore.commands.CommandAC;
import com.aearost.aranarthcore.commands.CommandACCompleter;
import com.aearost.aranarthcore.commands.CommandBalance;
import com.aearost.aranarthcore.commands.CommandBalanceCompleter;
import com.aearost.aranarthcore.commands.CommandBalancetop;
import com.aearost.aranarthcore.commands.CommandBalancetopCompleter;
import com.aearost.aranarthcore.commands.CommandBroadcast;
import com.aearost.aranarthcore.commands.CommandEco;
import com.aearost.aranarthcore.commands.CommandEcoCompleter;
import com.aearost.aranarthcore.commands.CommandMap;
import com.aearost.aranarthcore.commands.CommandPay;
import com.aearost.aranarthcore.commands.CommandPayCompleter;
import com.aearost.aranarthcore.commands.CommandRanks;
import com.aearost.aranarthcore.commands.CommandRanksCompleter;
import com.aearost.aranarthcore.commands.CommandRules;
import com.aearost.aranarthcore.commands.CommandRulesCompleter;
import com.aearost.aranarthcore.event.ArenaDrops;
import com.aearost.aranarthcore.event.ArmorStandEquipCancel;
import com.aearost.aranarthcore.event.AvatarDeath;
import com.aearost.aranarthcore.event.PlayerJoinServer;
import com.aearost.aranarthcore.event.RanksClick;
import com.aearost.aranarthcore.event.ShopCreate;
import com.aearost.aranarthcore.event.ShopDestroy;
import com.aearost.aranarthcore.event.ShopOpen;
import com.aearost.aranarthcore.event.ShopSignClick;
import com.aearost.aranarthcore.utils.AranarthAnnouncer;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.AranarthShopUtils;
import com.aearost.aranarthcore.utils.PersistenceUtils;

public class AranarthCore extends JavaPlugin {
	
	@Override
	public void onEnable() {		

		// Initialize Utils
		new AranarthShopUtils(this);
		new AranarthPlayerUtils(true);
		new AranarthAnnouncer(this);
		
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

		// Initialize commands
		getCommand("ac").setExecutor(new CommandAC());
		getCommand("ac").setTabCompleter(new CommandACCompleter());
		getCommand("balance").setExecutor(new CommandBalance());
		getCommand("balance").setTabCompleter(new CommandBalanceCompleter());
		getCommand("broadcast").setExecutor(new CommandBroadcast());
		getCommand("balancetop").setExecutor(new CommandBalancetop());
		getCommand("balancetop").setTabCompleter(new CommandBalancetopCompleter());
		getCommand("eco").setExecutor(new CommandEco());
		getCommand("eco").setTabCompleter(new CommandEcoCompleter());
		getCommand("map").setExecutor(new CommandMap());
		getCommand("pay").setExecutor(new CommandPay());
		getCommand("pay").setTabCompleter(new CommandPayCompleter());
		getCommand("ranks").setExecutor(new CommandRanks());
		getCommand("ranks").setTabCompleter(new CommandRanksCompleter());
		getCommand("rules").setExecutor(new CommandRules());
		getCommand("rules").setTabCompleter(new CommandRulesCompleter());

		// Update the files every 30 minutes to protect from loss of data
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				PersistenceUtils.writePlayersToFile();
				PersistenceUtils.writeShopSignsToFile();
			}
		}, 0, 36000);
		
	}

	@Override
	public void onDisable() {

		new AranarthPlayerUtils(false);
		AranarthShopUtils.removeAllPlayerShopHolograms();

	}

}