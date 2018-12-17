package net.teamlife.lifecore.game;

public enum StatusType {

	WAITING("Waiting"),
	PREMIUM("Premium"),
	INGAME("Ingame"),
	ENDING("ENDING");
	
	private String type;
	
	StatusType(String type){
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
