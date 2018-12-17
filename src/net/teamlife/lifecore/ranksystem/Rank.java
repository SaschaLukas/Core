package net.teamlife.lifecore.ranksystem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum Rank {

	ADMIN(100, "Admin", ChatColor.DARK_RED, ChatColor.DARK_RED + "Admin§7 | §8"),
	DEVELOPER(90, "Dev", ChatColor.AQUA, ChatColor.AQUA + "Dev§7 | §8"),
	MODERATOR(80, "Mod", ChatColor.RED, ChatColor.RED + "Mod§7 | §8"),
	SUPPORTER(70, "Sup", ChatColor.GREEN, ChatColor.GREEN + "Sup§7 | §8"),
	BUILDER(60, "Builder", ChatColor.YELLOW, ChatColor.YELLOW + "Builder§7 | §8"),
	YOUTUBER(50, "YouTuber", ChatColor.DARK_PURPLE, ChatColor.DARK_PURPLE + "YouTuber§7 | §8"),
	VIPPLUS(40, "VIP+", ChatColor.DARK_PURPLE, ChatColor.DARK_PURPLE + "VIP+§7 | §8"),
	PREMIUM(30, "Premium", ChatColor.GOLD, ChatColor.GOLD + "Premium§7 | §8"),
	USER(20, "User", ChatColor.GREEN, ChatColor.GREEN + "User§7 | §8"),
	HACKER(2, "Hacker", ChatColor.BLACK, ChatColor.BLACK + "Hacker§7 | §8");
	
	private int ranking;
	private ChatColor color;
	private String name;
	private String prefix;
	
	Rank(int ranking, String name, ChatColor color, String prefix){
		this.ranking = ranking;
		this.name = name;
		this.color = color;
		this.prefix = prefix;
	}
	
	public static boolean isTeam(Player player) {
		return RankManager.getRank(player.getUniqueId()).getRanking() >= 60;
	}
		
	public boolean isRank(Player player) {
		return RankManager.getRank(player.getUniqueId()).getRanking() >= this.getRanking();
	}
	
	public ChatColor getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
	
	public int getRanking() {
		return ranking;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
}
