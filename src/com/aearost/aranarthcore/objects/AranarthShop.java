package com.aearost.aranarthcore.objects;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class AranarthShop {

	private UUID uuid;
	private int transactionQuantity;
	private double buyAmount;
	private ItemStack item;
	private double sellAmount;
	private Location chestLocation;

	public AranarthShop(UUID uuid, int transactionQuantity, double buyAmount, ItemStack item, double sellAmount,
			Location chestLocation) {
		this.uuid = uuid;
		this.transactionQuantity = transactionQuantity;
		this.buyAmount = buyAmount;
		this.item = item;
		this.sellAmount = sellAmount;
		this.chestLocation = chestLocation;
	}

	public AranarthShop(UUID uuid, int transactionQuantity, double buyAmount, ItemStack item,
			Location chestLocation) {
		this.uuid = uuid;
		this.transactionQuantity = transactionQuantity;
		this.buyAmount = buyAmount;
		this.item = item;
		this.sellAmount = -1;
		this.chestLocation = chestLocation;
	}

	public AranarthShop(UUID uuid, int transactionQuantity, ItemStack item, double sellAmount,
			Location chestLocation) {
		this.uuid = uuid;
		this.transactionQuantity = transactionQuantity;
		this.buyAmount = -1;
		this.item = item;
		this.sellAmount = sellAmount;
		this.chestLocation = chestLocation;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
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
