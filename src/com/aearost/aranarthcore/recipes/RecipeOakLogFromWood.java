package com.aearost.aranarthcore.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public class RecipeOakLogFromWood {

	public RecipeOakLogFromWood(Plugin plugin) {
		createRecipe(plugin);
	}
	
	/**
	 * Creates the recipe for getting regular oak log from oak wood.
	 * 
	 * @param plugin
	 */
	private void createRecipe(Plugin plugin) {
		NamespacedKey nk = new NamespacedKey(plugin, "OAK_LOG_FROM_WOOD");
		ItemStack log = new ItemStack(Material.OAK_LOG, 4);
		ShapelessRecipe recipe = new ShapelessRecipe(nk, log);
		recipe.addIngredient(4, Material.OAK_WOOD);
        plugin.getServer().addRecipe(recipe);
	}
	
}
