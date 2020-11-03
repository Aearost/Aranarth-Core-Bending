package com.aearost.aranarthcore.objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AranarthShop {

	private Player owner;
	private int transactionQuantity;
	private double buyAmount;
	private ItemStack item;
	private double sellAmount;
	private Location chestLocation;

	public AranarthShop(Player owner, int transactionQuantity, double buyAmount, ItemStack item, double sellAmount,
			Location chestLocation) {
		this.owner = owner;
		this.transactionQuantity = transactionQuantity;
		this.buyAmount = buyAmount;
		this.item = item;
		this.sellAmount = sellAmount;
		this.chestLocation = chestLocation;
	}

	public AranarthShop(Player owner, int transactionQuantity, double buyAmount, ItemStack item,
			Location chestLocation) {
		this.owner = owner;
		this.transactionQuantity = transactionQuantity;
		this.buyAmount = buyAmount;
		this.item = item;
		this.sellAmount = -1;
		this.chestLocation = chestLocation;
	}

	public AranarthShop(Player owner, int transactionQuantity, ItemStack item, double sellAmount,
			Location chestLocation) {
		this.owner = owner;
		this.transactionQuantity = transactionQuantity;
		this.buyAmount = -1;
		this.item = item;
		this.sellAmount = sellAmount;
		this.chestLocation = chestLocation;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public int getTransactionQuantity() {
		return transactionQuantity;
	}
	
	public void setTransactionQuantity(int transactionQuantity) {
		this.transactionQuantity = transactionQuantity;
	}
	
	public double getBuyAmount() {
		return buyAmount;
	}
	
	public void setBuyAmount(double buyAmount) {
		this.buyAmount = buyAmount;
	}
	
	public ItemStack getItem() {
		return this.item;
	}
	
	public void setItem(ItemStack item) {
		this.item = item;
	}
	
	public double getSellAmount() {
		return sellAmount;
	}
	
	public void setSellAmount(double sellAmount) {
		this.sellAmount = sellAmount;
	}
	
	public Location getChestLocation() {
		return chestLocation;
	}
	
	public void setChestLocation(Location chestLocation) {
		this.chestLocation = chestLocation;
	}

}
