package com.aearost.aranarthcore.utils;

import java.util.HashMap;

import com.aearost.aranarthcore.objects.Player;

public class PlayerUtils {

	private HashMap<String, Player> players;
	
	public PlayerUtils(boolean isServerStarting) {
		if (isServerStarting) {
			readFromFile();
		} else {
			writeToFile();
		}
	}
	
	private void readFromFile() {
		
	}
	
	private void writeToFile() {
		
	}
	
}
