package net.teamlife.lifecore.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.google.gson.JsonObject;

import de.dytanic.cloudnet.api.CloudAPI;
import net.teamlife.bungee.json.JSONArray;
import net.teamlife.bungee.json.JSONObject;
import net.teamlife.lifecore.LifeCore;
import net.teamlife.lifecore.game.GameMode;
import net.teamlife.lifecore.game.JoinLevel;
import net.teamlife.lifecore.game.StatusType;

public class ServerData {

	private String serverName;
	private JSONObject jsonObject;

	private int databaseID = 0;

	public ServerData(String serverName) {
		setServerName(serverName);
	}

	public ServerData(int dataBaseID) {
		setDatabaseID(databaseID);
	}

	public ServerData(JSONObject jsonObject) {
		setJsonObject(jsonObject);
	}
	
	/***
	 * Löschen des Servers aus der Datenbank
	 */

	public void delete() {

		String command = null;
		String command2 = null;
		if (serverName != null) {
			command = "SELECT * FROM serverData WHERE serverName = '" + getServerName() + "'";
			command2 = "DELETE FROM serverData WHERE serverName='" + getServerName() + "'";
		} else if (databaseID != 0) {
			command = "SELECT * FROM serverData WHERE id = '" + getDatabaseID() + "'";
			command2 = "DELETE FROM serverData WHERE id='" + getDatabaseID() + "'";

		} else {
			Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(
					LifeCore.prefix + "Bitte wende dich an den Support mit dem Folgenden Fehler Code: #02"));
			Bukkit.getServer().shutdown();
		}

		ResultSet resultSet = LifeCore.getInstance().getSql().getResult(command);

		try {
			if (resultSet.next()) {
				LifeCore.getInstance().getSql().update(command2);
				LifeCore.getInstance().addLog("Der Server: " + CloudAPI.getInstance().getServerId()
						+ " wurde in der Datenbank unregistriert");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/***
	 * Erstellen eines neuen Servers.
	 * @param joinLevel
	 * @param gameMode
	 * @param statusType
	 * @param mapName
	 */

	public void create(JoinLevel joinLevel, GameMode gameMode, StatusType statusType, String mapName) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("startedTime", System.currentTimeMillis()).put("joinLevel", joinLevel.getJoinLevel())
				.put("gameMode", gameMode.getName()).put("statusType", statusType).put("Map", mapName)
				.put("port", Bukkit.getServer().getPort()).put("maxMember", Bukkit.getServer().getMaxPlayers())
				.put("onlinePlayers", Bukkit.getServer().getOnlinePlayers()).put("players", new JSONArray());

		try {

			PreparedStatement preparedStatement = LifeCore.getInstance().getSql().getCon()
					.prepareStatement("INSERT INTO serverData(id, serverName, jsonString) VALUES (?,?,?)");
			preparedStatement.setString(2, CloudAPI.getInstance().getServerId());
			preparedStatement.setString(3, jsonObject.toString());

			preparedStatement.executeUpdate();

			LifeCore.getInstance().addLog(
					"Der Server: " + CloudAPI.getInstance().getServerId() + " wurde in der Datenbank registriert");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/***
	 * Lade der Aktuellen ServerData.
	 * @return ServerData
	 */

	public ServerData load() {
		String command = null;
		if (serverName != null) {
			command = "SELECT * FROM serverData WHERE serverName = '" + getServerName() + "'";
		} else if (databaseID != 0) {
			command = "SELECT * FROM serverData WHERE id = '" + getDatabaseID() + "'";
		} else {
			Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(
					LifeCore.prefix + "Bitte wende dich an den Support mit dem Folgenden Fehler Code: #02"));
			Bukkit.getServer().shutdown();
		}

		ResultSet resultSet = LifeCore.getInstance().getSql().getResult(command);

		try {
			if (resultSet.next()) {
				setJsonObject(new JSONObject(resultSet.getString(3)));

				if (serverName == null) {
					setServerName(resultSet.getString(2));
				}

				if (databaseID == 0) {
					setDatabaseID(resultSet.getInt(1));
				}

				return new ServerData(getJsonObject());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Bukkit.getOnlinePlayers().forEach(player -> player
				.sendMessage(LifeCore.prefix + "Bitte wende dich an den Support mit dem Folgenden Fehler Code: #01"));
		Bukkit.getServer().shutdown();
		return null;
	}
	
	/***
	 * Update des neue Value in die Datenbank.
	 */

	public void save() {
		LifeCore.getInstance().getSql().update("UPDATE serverName SET jsonString ='" + getJsonObject().toString()
				+ "' WHERE serverName ='" + getServerName() + "'");
	}

	/***
	 * Setzen eines neues Values für einen Key
	 * @param key
	 * @param value
	 */
	
	public void setValue(String key, Object value) {
		jsonObject.remove(key);
		jsonObject.put(key, value);
		save();
	}

	public int getDatabaseID() {
		return databaseID;
	}

	public void setDatabaseID(int databaseID) {
		this.databaseID = databaseID;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

}
