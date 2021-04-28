package com.aearost.aranarthcore.objects;

/**
 * An AranarthPlayer object stores all variables representing a player on the
 * server. The plugin heavily utilizes this object through the
 * AranarthPlayerUtils class.
 * 
 * @author Aearost
 *
 */
public class AranarthPlayer {

	private String username;
	private int rankLevel;
	private boolean isMale;
	private double balance;
	private int councilStatus;

	public AranarthPlayer(String username, int rankLevel, boolean isMale, double balance, int councilStatus) {
		this.username = username;
		this.rankLevel = rankLevel;
		this.isMale = isMale;
		this.balance = balance;
		this.councilStatus = councilStatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public int getCouncilStatus() {
		return councilStatus;
	}

	public void setCouncilStatus(int councilStatus) {
		this.councilStatus = councilStatus;
	}

}
