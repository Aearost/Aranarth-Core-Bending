package com.aearost.aranarthcore.objects;

import java.util.List;

import com.aearost.aranarthcore.utils.SubGroup;

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
	private List<SubGroup> subGroups;
	private boolean isMale;
	private double balance;
	private boolean isAbleToChangeElement;
	private int saintStatus;
	private String avatarStatus;
	private int councilStatus;

	public AranarthPlayer(String username, int rankLevel, List<SubGroup> subGroups, boolean isMale, double balance,
			boolean isAbleToChangeElement) {
		this.username = username;
		this.rankLevel = rankLevel;
		this.subGroups = subGroups;
		this.isMale = isMale;
		this.balance = balance;
		this.isAbleToChangeElement = isAbleToChangeElement;
		saintStatus = 0;
		avatarStatus = "none";
		councilStatus = 0;
	}

	public AranarthPlayer(String username, int rankLevel, List<SubGroup> subGroups, boolean isMale, double balance,
			boolean isAbleToChangeElement, int saintOrCouncilStatus) {
		this.username = username;
		this.rankLevel = rankLevel;
		this.subGroups = subGroups;
		this.isMale = isMale;
		this.balance = balance;
		this.isAbleToChangeElement = isAbleToChangeElement;
		if (saintOrCouncilStatus > 3) {
			this.councilStatus = saintOrCouncilStatus - 3;
			this.saintStatus = 0;
		} else {
			this.saintStatus = saintOrCouncilStatus;
			this.councilStatus = 0;
		}
		this.avatarStatus = "none";
	}

	public AranarthPlayer(String username, int rankLevel, List<SubGroup> subGroups, boolean isMale, double balance,
			boolean isAbleToChangeElement, String avatarStatus) {
		this.username = username;
		this.rankLevel = rankLevel;
		this.subGroups = subGroups;
		this.isMale = isMale;
		this.balance = balance;
		this.isAbleToChangeElement = isAbleToChangeElement;
		this.saintStatus = 0;
		this.avatarStatus = avatarStatus;
		councilStatus = 0;
	}

	public AranarthPlayer(String username, int rankLevel, List<SubGroup> subGroups, boolean isMale, double balance,
			boolean isAbleToChangeElement, String avatarStatus, int councilStatus) {
		this.username = username;
		this.rankLevel = rankLevel;
		this.subGroups = subGroups;
		this.isMale = isMale;
		this.balance = balance;
		this.isAbleToChangeElement = isAbleToChangeElement;
		this.saintStatus = 0;
		this.avatarStatus = avatarStatus;
		this.councilStatus = councilStatus;
	}

	public AranarthPlayer(String username, int rankLevel, List<SubGroup> subGroups, boolean isMale, double balance,
			boolean isAbleToChangeElement, int saintOrCouncilStatus, String avatarStatus) {
		this.username = username;
		this.rankLevel = rankLevel;
		this.subGroups = subGroups;
		this.isMale = isMale;
		this.balance = balance;
		this.isAbleToChangeElement = isAbleToChangeElement;
		if (saintOrCouncilStatus > 3) {
			this.councilStatus = saintOrCouncilStatus - 3;
			this.saintStatus = 0;
		} else {
			this.saintStatus = saintOrCouncilStatus;
			this.councilStatus = 0;
		}
		this.avatarStatus = avatarStatus;
		councilStatus = 0;
	}

	public AranarthPlayer(String username, int rankLevel, List<SubGroup> subGroups, boolean isMale, double balance,
			boolean isAbleToChangeElement, int saintStatus, int councilStatus) {
		this.username = username;
		this.rankLevel = rankLevel;
		this.subGroups = subGroups;
		this.isMale = isMale;
		this.balance = balance;
		this.isAbleToChangeElement = isAbleToChangeElement;
		this.saintStatus = saintStatus;
		this.avatarStatus = "none";
		this.councilStatus = councilStatus;
	}

	public AranarthPlayer(String username, int rankLevel, List<SubGroup> subGroups, boolean isMale, double balance,
			boolean isAbleToChangeElement, int saintStatus, String avatarStatus, int councilStatus) {
		this.username = username;
		this.rankLevel = rankLevel;
		this.subGroups = subGroups;
		this.isMale = isMale;
		this.balance = balance;
		this.isAbleToChangeElement = isAbleToChangeElement;
		this.saintStatus = saintStatus;
		this.avatarStatus = avatarStatus;
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
	
	public List<SubGroup> getSubGroups() {
		return subGroups;
	}
	
	public String getSubGroupsString() {
		List<SubGroup> subGroups = getSubGroups();
		if (subGroups.size() == 1) {
			return subGroups.get(0).name();
		} else if (subGroups.size() == 2) {
			return subGroups.get(0).name() + "," + subGroups.get(0).name();
		} else {
			return "none";
		}
	}
	
	public void setSubGroups(List<SubGroup> subGroups) {
		this.subGroups = subGroups;
	}
	
	public void addSubGroup(SubGroup subGroup) {
		subGroups.add(subGroup);
	}
	
	public void removeSubGroup(SubGroup subGroup) {
		subGroups.remove(subGroup);
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

	public boolean getIsAbleToChangeElement() {
		return isAbleToChangeElement;
	}

	public void setIsAbleToChangeElement(boolean isAbleToChangeElement) {
		this.isAbleToChangeElement = isAbleToChangeElement;
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

	public int getCouncilStatus() {
		return councilStatus;
	}

	public void setCouncilStatus(int councilStatus) {
		this.councilStatus = councilStatus;
	}

}
