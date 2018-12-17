package net.teamlife.lifecore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

import io.netty.channel.Channel;
import net.teamlife.lifecore.data.PlayerData;
import net.teamlife.lifecore.sql.MySQL;
import net.teamlife.lifecore.utils.MapObject;

public class LifeCore extends JavaPlugin {

	private MySQL sql;
	private static LifeCore instance;
	

	public static String prefix = "§7▌ §cTeamLifeNET»§7 ";
	
	public HashMap<UUID, PlayerData> players2 = new HashMap<>(); 
	private Map<UUID, MapObject<PlayerData, Channel>> players = new HashMap<>();

	@Override
	public void onEnable() {
		setInstance(this);
		setSql(new MySQL("localhost", 3306, "LifeSystem", "LifeSystem", "ISeWl4F6TRhot7i8"));

	}

	@Override
	public void onDisable() {

	}

	public void addLog(String logging) {
		getSql().update("INSERT INTO serverLog (id, time, log) VALUES ('null','" + System.currentTimeMillis() + "','"
				+ logging + "'=;");
	}

	public MySQL getSql() {
		return sql;
	}

	public static LifeCore getInstance() {
		return instance;
	}

	private void setInstance(LifeCore instance) {
		this.instance = instance;
	}

	private void setSql(MySQL sql) {
		this.sql = sql;

	}
	
	public Map<UUID, MapObject<PlayerData, Channel>> getPlayers() {
		return players;
	}
	
	public HashMap<UUID, PlayerData> getPlayers2() {
		return players2;
	}
}
