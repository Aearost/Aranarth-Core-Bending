package com.aearost.aranarthcore.objects;

public class AranarthPlayer {

	private int rankLevel;
	private boolean isMale;
	private double balance;
	private int saintStatus;
	private String avatarStatus;
	
	public AranarthPlayer(int rankLevel, boolean isMale, double balance) {
		this.rankLevel = rankLevel;
		this.isMale = isMale;
		this.balance = balance;
		saintStatus = 0;
		avatarStatus = "none";
	}
	
	public AranarthPlayer(int rankLevel, boolean isMale, double balance, int saintStatus) {
		this.rankLevel = rankLevel;
		this.isMale = isMale;
		this.balance = balance;
		this.saintStatus = saintStatus;
		this.avatarStatus = "none";
	}
	
	public AranarthPlayer(int rankLevel, boolean isMale, double balance, String avatarStatus) {
		this.rankLevel = rankLevel;
		this.isMale = isMale;
		this.balance = balance;
		this.saintStatus = 0;
		this.avatarStatus = avatarStatus;
	}
	
	public AranarthPlayer(int rankLevel, boolean isMale, double balance, int saintStatus, String avatarStatus) {
		this.rankLevel = rankLevel;
		this.isMale = isMale;
		this.balance = balance;
		this.saintStatus = saintStatus;
		this.avatarStatus = avatarStatus;
	}
	
	public int getRank() {
		return rankLevel;
	}
	
	public void setRank(int rankLevel) {
		this.rankLevel = rankLevel;
	}
	
	public boolean getIsMale() {
		return isMale;
	}
	
	public void setIsMale(boolean gender) {
		this.isMale = gender;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public int getSaintStatus() {
		return saintStatus;
	}
	
	public void setSaintStatus(int saintStatus) {
		this.saintStatus = saintStatus;
	}
	
	public String getAvatarStatus() {
		return avatarStatus;
	}
	
	public void setAvatarStatus(String avatarStatus) {
		this.avatarStatus = avatarStatus;
	}
	
}
