package com.aearost.aranarthcore;

import org.bukkit.plugin.java.JavaPlugin;

import com.aearost.aranarthcore.commands.CommandAC;
import com.aearost.aranarthcore.commands.CommandACCompleter;
import com.aearost.aranarthcore.event.ArenaDrops;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {

		// Initialize Events
		new ArenaDrops(this);
		
		// Initialize commands
		getCommand("ac").setExecutor(new CommandAC());
		getCommand("ac").setTabCompleter(new CommandACCompleter());
	}

}