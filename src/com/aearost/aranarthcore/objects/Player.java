package com.aearost.aranarthcore.objects;

public class Player {

	private String rank;
	private String gender;
	private int saintStatus;
	private String avatarStatus;
	
	public Player(String rank, String gender) {
		this.rank = rank;
		this.gender = gender;
		saintStatus = 0;
		avatarStatus = "none";
	}
	
	public Player(String rank, String gender, int saintStatus) {
		this.rank = rank;
		this.gender = gender;
		this.saintStatus = saintStatus;
		this.avatarStatus = "none";
	}
	
	public Player(String rank, String gender, String avatarStatus) {
		this.rank = rank;
		this.gender = gender;
		this.saintStatus = 0;
		this.avatarStatus = avatarStatus;
	}
	
	public Player(String rank, String gender, int saintStatus, String avatarStatus) {
		this.rank = rank;
		this.gender = gender;
		this.saintStatus = saintStatus;
		this.avatarStatus = avatarStatus;
	}
	
	public String getRank() {
		return rank;
	}
	
	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
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
