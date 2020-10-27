package com.aearost.aranarthcore;

import org.bukkit.plugin.java.JavaPlugin;

import com.aearost.aranarthcore.commands.CommandAC;
import com.aearost.aranarthcore.commands.CommandACCompleter;
import com.aearost.aranarthcore.commands.CommandRanks;
import com.aearost.aranarthcore.commands.CommandRanksCompleter;
import com.aearost.aranarthcore.event.ArenaDrops;
import com.aearost.aranarthcore.event.RanksClick;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {

		// Initialize Events
		new ArenaDrops(this);
		new RanksClick(this);
		
		// Initialize commands
		getCommand("ac").setExecutor(new CommandAC());
		getCommand("ac").setTabCompleter(new CommandACCompleter());
		getCommand("ranks").setExecutor(new CommandRanks());
		getCommand("ranks").setTabCompleter(new CommandRanksCompleter());
	}

}