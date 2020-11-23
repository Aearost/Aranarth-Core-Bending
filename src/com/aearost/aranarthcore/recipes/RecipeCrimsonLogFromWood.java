package com.aearost.aranarthcore.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public class RecipeCrimsonLogFromWood {

	public RecipeCrimsonLogFromWood(Plugin plugin) {
		createRecipe(plugin);
	}
	
	/**
	 * Creates the recipe for getting regular crimson log from crimson wood.
	 * 
	 * @param plugin
	 */
	private void createRecipe(Plugin plugin) {
		NamespacedKey nk = new NamespacedKey(plugin, "CRIMSON_LOG_FROM_WOOD");
		ItemStack log = new ItemStack(Material.CRIMSON_STEM, 4);
		ShapelessRecipe recipe = new ShapelessRecipe(nk, log);
		recipe.addIngredient(4, Material.CRIMSON_HYPHAE);
        plugin.getServer().addRecipe(recipe);
	}
	
}
