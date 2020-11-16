package com.aearost.aranarthcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.aearost.aranarthcore.utils.ChatUtils;

public class CommandRules implements CommandExecutor {

	/**
	 * All logic behind the /rules command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length > 0) {
			if (args[0].toLowerCase().equals("arena")) {
				sender.sendMessage(ChatUtils.translateToColor("&c&l&nArena Rules"));
				sender.sendMessage(ChatUtils.translateToColor(
						"&4&l1. &eHitting and running is not permitted. Once you commence a fight, you cannot run from it."));
				sender.sendMessage(ChatUtils.translateToColor(
						"&4&l2. &eIf somebody attacks you, you are permitted to run. This does not mean you can run from everyone. It’s an arena!"));
				sender.sendMessage(ChatUtils
						.translateToColor("&4&l3. &eTeaming is allowed, however troll-like tactics are not permitted."));
				return true;
			}
		}

		sender.sendMessage(ChatUtils.translateToColor("&6&l&nGeneral Rules"));
		sender.sendMessage(ChatUtils
				.translateToColor("&e&l1. &7&oBe kind to each other, and use common sense."));
		sender.sendMessage(ChatUtils.translateToColor(
				"&e&l2. &7&oRacism, homophobia, transphobia, sexism, and other forms of hate speech will not be tolerated. Sensitive topics should also be avoided."));
		sender.sendMessage(ChatUtils.translateToColor(
				"&e&l3. &7&oNo advertising other servers."));
		sender.sendMessage(ChatUtils.translateToColor(
				"&e&l4. &7&oNo advantage mods, or hacked clients. If you are unsure if you can use a mod, please contact a staff member."));
		sender.sendMessage(ChatUtils.translateToColor(
				"&e&l5. &7&oSpamming is not permitted"));
		sender.sendMessage(
				ChatUtils.translateToColor("&e&l6. &7&oSwearing is permitted, but please do not swear excessively."));
		sender.sendMessage(
				ChatUtils.translateToColor("&e&l7. &7&oRaiding and griefing is allowed."));
		sender.sendMessage(ChatUtils.translateToColor(
				"&e&l8. &7&oAutomatic farms and sorting systems are permitted."));
		sender.sendMessage(ChatUtils
				.translateToColor("&e&l9. &7&oNo using alt accounts to bypass a punishment."));
		return true;

	}

}
