package net.teamlife.lifecore.game;

public enum JoinLevel {

	ALL("All"),
	PREMIUM("Premium"),
	ADMIN("Admin"),
	TEAM("Team");
	
	private String joinLevel;
	
	JoinLevel(String joinLevel) {
		this.joinLevel = joinLevel;
	} 
	
	public String getJoinLevel() {
		return joinLevel;
	}
}
