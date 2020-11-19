package com.aearost.aranarthcore.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import com.aearost.aranarthcore.AranarthCore;

public class AranarthAnnouncer {
	
	private List<String> messages;
	
	public AranarthAnnouncer(AranarthCore plugin) {
		messages = new ArrayList<>();
		messages.add("&7Don't forget to &e/vote&7!");
		messages.add("&7Be sure to know the &e/rules&7!");
		messages.add("&7Join us on discord with &e/discord&7!");
		messages.add("&7You can change your element for &e$250&7!");
		messages.add("&7Earn money by selling items at &e/warp market&7!");
		makeAnnouncements(plugin);
	}
	
	private void makeAnnouncements(AranarthCore plugin) {
		
		// Update the files every 30 minutes to protect from loss of data
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			int messageCounter = 0;
			
			
			@Override
			public void run() {
				Bukkit.broadcastMessage(ChatUtils.chatMessage(messages.get(messageCounter)));
				
				if (messageCounter < 4) {
					messageCounter++;
				} else {
					messageCounter = 0;
				}
			}
		}, 0, 12000);
	}
	
}
