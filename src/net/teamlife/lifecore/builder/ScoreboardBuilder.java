package net.teamlife.lifecore.builder;

import com.google.common.collect.Lists;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreboardBuilder {
	
	private ScoreboardManager mg = Bukkit.getScoreboardManager();
	private Scoreboard sb = mg.getMainScoreboard();

	public ScoreboardBuilder(String objective, String title, List<String> entrys) {
		Objective obj = sb.registerNewObjective(objective, "dummy");
		int i = 0;
		for (String score : Lists.reverse(entrys)) {
			i++;
			obj.getScore(score).setScore(i);
		}
		obj.setDisplayName(title);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public Scoreboard build() {
		return sb;
	}
}