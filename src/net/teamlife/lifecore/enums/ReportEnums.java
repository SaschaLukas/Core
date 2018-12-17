package net.teamlife.lifecore.enums;

public enum ReportEnums {

	BELEIDIGUNG("Beleidigung"),
	SPAM("SPAM"),
	RADIKALISMUS("Radikalismus"),
	WERBUNG("Werbung"),
	DROHUNG("Drohungen"),
	Respektlose("Respektlos"),
	CLIENTMODIFIKATIONEN("Client Modifikationen"),
	BUGUSING("Bug Using"),
	SKIN("Skin"),
	GRIEFING("Griefing"),
	BETRUG("Betrug"),
	BANUMGEHUNG("Ban Umgehung"),
	ACCOUNTLIST("Accountlist"),
	ANDERES("Anderes");
	
	String name;
	
	
	private ReportEnums(String name) {
		setName(name);
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
