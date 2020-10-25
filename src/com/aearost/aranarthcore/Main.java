package com.aearost.aranarthcore;

import org.bukkit.plugin.java.JavaPlugin;

import com.aearost.aranarthcore.event.ArenaDrops;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {

		new ArenaDrops(this);
		
	}

}