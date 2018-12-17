package net.teamlife.lifecore.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.net.ssl.KeyStoreBuilderParameters;

import org.bukkit.entity.Player;

import com.google.gson.JsonArray;

import de.dytanic.cloudnet.api.CloudAPI;
import io.netty.channel.Channel;
import net.teamlife.bungee.json.JSONObject;
import net.teamlife.lifecore.LifeCore;
import net.teamlife.lifecore.ranksystem.Rank;
import net.teamlife.lifecore.ranksystem.RankManager;
import net.teamlife.lifecore.utils.MapObject;

public class PlayerData {

	public UUID playerUUID;
	public String playerName;

	public JSONObject jsonObject;

	public PlayerData(UUID playerUUID) {
		this.playerUUID = playerUUID;
	}

	@Deprecated
	public PlayerData(String playerName) {
		this.playerName = playerName;
	}

	public PlayerData load() {
		String command;

		if (getPlayerUUID() != null) {
			command = "SELECT * FROM playerData WHERE playerUniqueID = '" + getPlayerUUID().toString() + "'";
		} else {
			command = "SELECT * FROM playerData WHERE playerName = '" + getPlayerName() + "'";
		}

		ResultSet resultSet = LifeCore.getInstance().getSql().getResult(command);

		try {
			if (resultSet.next()) {
				jsonObject = new JSONObject(resultSet.getString(3));

				if (getPlayerName() == null) {
					playerName = resultSet.getString(2);
				}

				if (getPlayerUUID() == null) {
					playerUUID = UUID.fromString(resultSet.getString(1));
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this;
	}

	public void setValue(String key, Object value) {
		jsonObject.remove(key);
		jsonObject.put(key, value);

		if (LifeCore.getInstance().getPlayers().containsKey(getPlayerUUID())) {
			MapObject<PlayerData, Channel> mapObject = LifeCore.getInstance().getPlayers().get(getPlayerUUID());
			mapObject.setKey(this);

			LifeCore.getInstance().getPlayers().replace(getPlayerUUID(), mapObject);
		}

		save();
	}

	public void save() {
		LifeCore.getInstance().getSql().update("UPDATE playerData SET jsonString ='" + jsonObject.toString() + "'");
	}

	public void create(Player player) {

		LifeCore.getInstance().getSql().update(
				"INSERT INTO GeneralData(playerUUID, playerIP, firstLogin, LastLogin, teamSpeakUUID, playerTokens) VALUES ('"
						+ player.getUniqueId() + "','" + player.getAddress().getAddress().toString() + "','"
						+ System.currentTimeMillis() + "','" + System.currentTimeMillis() + "','null','1');");

		LifeCore.getInstance().getSql().update(
				"INSERT INTO NetworkStats(BanPoints, MutePoints, currentReports, successfulReports, unsuccesfulReports, "
				+ "currentServerLogin, currentWebsiteLoggin, currentTeamSpeakLoggin) VALUES ('0','0',");

		jsonObject = new JSONObject();

		jsonObject

				.put("NetworkStats",
						new JSONObject().put("BanPoints", 0).put("MutePoints", 0).put("currentReports", 0)
								.put("successfulReports", 0).put("unsuccessfulReports",
										0)
								.put("currentServerLogin",
										1)
								.put("currentWebsiteLoggin", 0).put("currentTeamSpeakLoggin", 0))
				.put("NetworkData", new JSONObject()
						.put("currentServer", CloudAPI.getInstance().getOnlinePlayer(player.getUniqueId()).getServer())
						.put("Ban", "no").put("Mute", "no")
						.put("currentBungee", CloudAPI.getInstance().getOnlinePlayer(player.getUniqueId()).getProxy())
						.put("currentRank", "User").put("AutoNick", "no").put("Clan", "null"))
				.put("LobbyData",
						new JSONObject().put("LastPosition", player.getLocation()).put("playerHide", "none")
								.put("SilensHub", "no"))
				.put("FriendsData",
						new JSONObject().put("Notify", "true").put("privateMessages", "true")
								.put("allowPartyRequest", "true").put("allowFriendRequest", "true")
								.put("allowServerJump", "true").put("currentFriends", new JsonArray())
								.put("currentFriendsRequest", new JsonArray()));

		try {
			PreparedStatement preparedStatement = LifeCore.getInstance().getSql().getCon().prepareStatement(
					"INSERT INTO playerData (playerUniqueID, playerName, jsonString) VALUES (?,?,?);");
			preparedStatement.setString(1, getPlayerUUID().toString());
			preparedStatement.setString(2, getPlayerName());
			preparedStatement.setString(3, jsonObject.toString());

			preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Rank getRank() {
		return RankManager.getRank(getPlayerUUID());
	}

	public ClanData getClan() {
		return new ClanData("test");
	}

	public void update(Player player) {
		int currentServerLogin = jsonObject.getJSONObject("NetworkStats").getInt("currentServerLogin");

		JSONObject newObject = jsonObject.getJSONObject("NetworkStats");
		JSONObject generalData = jsonObject.getJSONObject("GeneralData");

		newObject.remove("currentServerLogin");
		newObject.put("currentServerLogin", currentServerLogin + 1);

		generalData.remove("playerIP");
		generalData.put("playerIP", player.getAddress().getAddress());

		LifeCore.getInstance().getSql().update("UPDATE playerData SET playerName ='" + player.getName()
				+ "' WHERE playerUniqueID='" + player.getUniqueId() + "'");

		setValue("NetworkStats", newObject);
		setValue("generalData", generalData);
		save();

	}

	public String getPlayerName() {
		return playerName;
	}

	public UUID getPlayerUUID() {
		return playerUUID;
	}

}
