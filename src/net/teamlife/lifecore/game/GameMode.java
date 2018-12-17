package net.teamlife.lifecore.game;

public enum GameMode {

	LOBBY("Lobby");
	
	private String name;
	
	GameMode(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
}
