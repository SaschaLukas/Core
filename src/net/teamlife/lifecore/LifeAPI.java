package net.teamlife.lifecore;

public class LifeAPI {

	private static boolean teaming = true;
	private static boolean chat = true;
	
	public static boolean isTeaming() {
		return teaming;
	}
	
	public static void setTeaming(boolean teaming) {
		LifeAPI.teaming = teaming;
	}
	
	public static boolean isChat() {
		return chat;
	}
	
	public static void setChat(boolean chat) {
		LifeAPI.chat = chat;
	}
	
}
