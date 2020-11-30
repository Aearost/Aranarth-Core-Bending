package com.aearost.aranarthcore.event;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.aearost.aranarthcore.AranarthCore;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;
import com.aearost.aranarthcore.utils.TrailUtils;

public class TrailArrowClick implements Listener {

	public TrailArrowClick(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Deals with all clicks of the trails GUI elements.
	 * 
	 * @author Aearost
	 *
	 */
	@EventHandler
	public void onRankClick(final InventoryClickEvent e) {

		if (ChatUtils.stripColor(e.getView().getTitle()).equals("Arrow Trails")) {
			e.setCancelled(true);

			int slot = e.getSlot();
			UUID uuid = e.getWhoClicked().getUniqueId();
			int saintStatus = AranarthPlayerUtils.getPlayer(uuid).getSaintStatus();

			List<ItemStack> arrows = new ArrayList<>();
			if (saintStatus == 2) {
				arrows = TrailUtils.getSaint2Trails();
			} else if (saintStatus == 3) {
				arrows = TrailUtils.getSaint3Trails();
			} else {
				arrows = TrailUtils.getSaint1Trails();
			}
			
			Player player = Bukkit.getPlayer(uuid);
			if (arrows.get(slot).getType() == Material.AIR) {
				return;
			} else if (arrows.get(slot).getType() == Material.BARRIER) {
				player.sendMessage(ChatUtils.translateToColor("&7&l» Your arrow trail has been removed"));
				player.closeInventory();
				CommandSender commandSender = Bukkit.getServer().getConsoleSender();
				Bukkit.dispatchCommand(commandSender, "arrowtrail " + player.getName() + " disable");
				return;
			}
			
			equipArrowTrail(arrows.get(slot), player);
			player.closeInventory();
		}
	}

	private void equipArrowTrail(ItemStack arrow, Player player) {
		String effectName = arrow.getItemMeta().getDisplayName();
		player.sendMessage(ChatUtils.translateToColor("&7&l» You have equipped the arrow trail: " + effectName));
		player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
		effectName = ChatUtils.stripColor(effectName);
		effectName = effectName.replaceAll("[()]", "");
		effectName = effectName.replaceAll(" ", "_");
		CommandSender commandSender = Bukkit.getServer().getConsoleSender();
		Bukkit.dispatchCommand(commandSender, "arrowtrail " + player.getName() + " " + effectName.toLowerCase());
	}

}
