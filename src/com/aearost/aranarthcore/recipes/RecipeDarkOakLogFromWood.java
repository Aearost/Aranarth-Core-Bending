package com.aearost.aranarthcore.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public class RecipeDarkOakLogFromWood {

	public RecipeDarkOakLogFromWood(Plugin plugin) {
		createRecipe(plugin);
	}
	
	/**
	 * Creates the recipe for getting regular dark oak log from dark oak wood.
	 * 
	 * @param plugin
	 */
	private void createRecipe(Plugin plugin) {
		NamespacedKey nk = new NamespacedKey(plugin, "DARK_OAK_LOG_FROM_WOOD");
		ItemStack log = new ItemStack(Material.DARK_OAK_LOG, 4);
		ShapelessRecipe recipe = new ShapelessRecipe(nk, log);
		recipe.addIngredient(4, Material.DARK_OAK_WOOD);
        plugin.getServer().addRecipe(recipe);
	}
	
}
