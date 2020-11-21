package com.aearost.aranarthcore.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;

import com.aearost.aranarthcore.AranarthCore;

public class AranarthAnnouncer {
	
	private List<String> messages;
	
	public AranarthAnnouncer(AranarthCore plugin) {
		messages = new ArrayList<>();
		messages.add("&7Don't forget to &e/vote&7!");
		messages.add("&7Earn money by selling items at &e/warp market&7!");
		messages.add("&7Be sure to know the &e/rules&7.");
		messages.add("&7Join our &e/discord&7!");
		messages.add("&7Do &e/teas guide &7for help with teas.");
		messages.add("&7View our &e/ranks &7to rankup!");
		messages.add("&7Change elements for &e$250 &7at &e/warp info.");
		makeAnnouncements(plugin);
	}
	
	private void makeAnnouncements(AranarthCore plugin) {
		
		// Update the files every 30 minutes to protect from loss of data
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			int messageCounter = 0;
			
			
			@Override
			public void run() {
				Bukkit.broadcastMessage(ChatUtils.translateToColor("&8[&6Aranarth&8] &r" + messages.get(messageCounter)));
				
				if (messageCounter < 4) {
					messageCounter++;
				} else {
					messageCounter = 0;
					Collections.shuffle(messages);
				}
			}
		}, 0, 12000);
	}
	
}
