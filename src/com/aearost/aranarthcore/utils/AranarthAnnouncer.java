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
		messages.add("&7&oDon't forget to &e&o/vote&7&o!");
		messages.add("&7&oEarn money by selling items at &e&o/warp market&7&o!");
		messages.add("&7&oBe sure to know the &e&o/rules");
		messages.add("&7&oJoin our &e&o/discord&7&o!");
		messages.add("&7&oDo &e&o/teas guide &7&ofor help with teas");
		messages.add("&7&oView our &e&o/ranks &7&oto rankup!");
		messages.add("&7&oChange elements for &e&o$250 &7&oat &e&o/warp info");
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
		}, 12000, 12000);
	}
	
}
