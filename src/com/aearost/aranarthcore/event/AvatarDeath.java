package com.aearost.aranarthcore.event;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.aearost.aranarthcore.AranarthCore;
import com.aearost.aranarthcore.objects.AranarthPlayer;
import com.aearost.aranarthcore.utils.AranarthPlayerUtils;
import com.aearost.aranarthcore.utils.ChatUtils;

public class AvatarDeath implements Listener {

	public AvatarDeath(AranarthCore plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Handles automatically selecting a new avatar on the current one's death.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onPlayerJoin(final PlayerDeathEvent e) {

		AranarthPlayer currentAvatar = AranarthPlayerUtils.getPlayer(e.getEntity().getUniqueId());
		if (currentAvatar.getAvatarStatus().equals("current")) {

			Player[] onlinePlayers = new Player[Bukkit.getOnlinePlayers().size()];
			Bukkit.getOnlinePlayers().toArray(onlinePlayers);
			Random random = new Random();
			boolean isNewAvatarSelected = false;

			if (onlinePlayers.length > 1) {
				while (!isNewAvatarSelected) {
					Player player = onlinePlayers[random.nextInt(onlinePlayers.length)];
					AranarthPlayer newAvatar = AranarthPlayerUtils.getPlayer(player);
					if (!newAvatar.getAvatarStatus().equals("current")) {
						AranarthPlayerUtils.replaceAvatar(player);
						isNewAvatarSelected = true;

						String previousAvatar = AranarthPlayerUtils.replaceAvatar(player);
						Bukkit.broadcastMessage(ChatUtils.translateToColor(
								"&5&lAvatar &d&l" + e.getEntity().getName() + " &5&lhas passed away..."));
						Bukkit.broadcastMessage(ChatUtils.translateToColor(
								"&5&lWelcome their successor, Avatar &d&l" + player.getName() + "&5&l!"));

						for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
							onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH, 1.3F,
									2.0F);
						}
						ChatUtils.updatePlayerGroupsAndPrefix(Bukkit.getOfflinePlayer(e.getEntity().getUniqueId()));
						ChatUtils.updatePlayerGroupsAndPrefix(
								Bukkit.getOfflinePlayer(AranarthPlayerUtils.getUUID(previousAvatar)));
					}
				}

			}

		}

	}

}
