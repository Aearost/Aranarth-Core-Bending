package com.aearost.aranarthcore.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public class RecipeWarpedLogFromWood {

	public RecipeWarpedLogFromWood(Plugin plugin) {
		createRecipe(plugin);
	}
	
	/**
	 * Creates the recipe for getting regular warped log from warped wood.
	 * 
	 * @param plugin
	 */
	private void createRecipe(Plugin plugin) {
		NamespacedKey nk = new NamespacedKey(plugin, "WARPED_LOG_FROM_WOOD");
		ItemStack log = new ItemStack(Material.WARPED_STEM, 4);
		ShapelessRecipe recipe = new ShapelessRecipe(nk, log);
		recipe.addIngredient(4, Material.WARPED_HYPHAE);
        plugin.getServer().addRecipe(recipe);
	}
	
}
