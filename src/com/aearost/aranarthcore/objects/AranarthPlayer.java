package com.aearost.aranarthcore.objects;

public class AranarthPlayer {

	private String rank;
	private boolean isMale;
	private double balance;
	private int saintStatus;
	private String avatarStatus;
	
	public AranarthPlayer(String rank, boolean isMale, double balance) {
		this.rank = rank;
		this.isMale = isMale;
		this.balance = balance;
		saintStatus = 0;
		avatarStatus = "none";
	}
	
	public AranarthPlayer(String rank, boolean isMale, double balance, int saintStatus) {
		this.rank = rank;
		this.isMale = isMale;
		this.balance = balance;
		this.saintStatus = saintStatus;
		this.avatarStatus = "none";
	}
	
	public AranarthPlayer(String rank, boolean isMale, double balance, String avatarStatus) {
		this.rank = rank;
		this.isMale = isMale;
		this.balance = balance;
		this.saintStatus = 0;
		this.avatarStatus = avatarStatus;
	}
	
	public AranarthPlayer(String rank, boolean isMale, double balance, int saintStatus, String avatarStatus) {
		this.rank = rank;
		this.isMale = isMale;
		this.balance = balance;
		this.saintStatus = saintStatus;
		this.avatarStatus = avatarStatus;
	}
	
	public String getRank() {
		return rank;
	}
	
	public void setRank(String rank) {
		this.rank = rank;
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
