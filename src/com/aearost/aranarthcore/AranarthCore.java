package com.aearost.aranarthcore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.aearost.aranarthcore.commands.CommandAC;
import com.aearost.aranarthcore.commands.CommandACCompleter;
import com.aearost.aranarthcore.commands.CommandRanks;
import com.aearost.aranarthcore.commands.CommandRanksCompleter;
import com.aearost.aranarthcore.event.ArenaDrops;
import com.aearost.aranarthcore.event.PlayerJoinServer;
import com.aearost.aranarthcore.event.RanksClick;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.PersistenceUtils;

public class AranarthCore extends JavaPlugin {

	@Override
	public void onEnable() {

		new AranarthPlayerUtils(true);
		
		// Initialize Events
		new ArenaDrops(this);
		new RanksClick(this);
		new PlayerJoinServer(this);
		
		// Initialize commands
		getCommand("ac").setExecutor(new CommandAC());
		getCommand("ac").setTabCompleter(new CommandACCompleter());
		getCommand("ranks").setExecutor(new CommandRanks());
		getCommand("ranks").setTabCompleter(new CommandRanksCompleter());
		
		// Updates the players.json file every 15 minutes in case of a crash
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
	        public void run() {
	            PersistenceUtils.writeToFile();
	        }
		}, 0, 18000);
	}
	
	@Override
	public void onDisable() {
		
		new AranarthPlayerUtils(false);
		
	}

}