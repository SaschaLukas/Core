package net.teamlife.lifecore.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;

import de.dytanic.cloudnet.api.CloudAPI;
import net.teamlife.bungee.json.JSONArray;
import net.teamlife.bungee.json.JSONObject;
import net.teamlife.lifecore.LifeCore;
import net.teamlife.lifecore.enums.ReportEnums;

public class ReportData {

	private UUID uuid;
	private ReportEnums enums;

	private JSONObject jsonArray;
	private String reportID;

	public ReportData(UUID uuid) {
		setUuid(uuid);
	}

	public ReportData(String reportID) {
		this.reportID = reportID;
	}

	public void create(UUID targetUUID, ReportEnums enums, String beweis) {
		jsonArray = new JSONObject();

		reportID = UUID.randomUUID().toString();

		jsonArray.put("reportID", reportID).put("reportReason", getEnums().getName())
				.put("reportTime", System.currentTimeMillis()).put("reporterUUID", getUuid())
				.put("targetUUID", targetUUID).put("reportBeweis", beweis)
				.put("reportServer", CloudAPI.getInstance().getOnlinePlayer(targetUUID).getServer());

				
		PlayerData playerData = new PlayerData(getUuid()).load();

		JSONObject playerObject = playerData.jsonObject.getJSONObject("NetworkStats");
		int currentReports = playerObject.getInt("currentReports");
		playerObject.remove("currentReports");
		playerObject.put("currentReports", currentReports + 1);
		playerData.setValue("NetworkStats", playerObject);

		try {

			PreparedStatement preparedStatement = LifeCore.getInstance().getSql().getCon()
					.prepareStatement("INSERT INTO reportData(playername, id, jsonString) VALUES (?,?,?)");
			preparedStatement.setString(1, Bukkit.getPlayer(getUuid()).getName());
			preparedStatement.setString(2, reportID.toString());
			preparedStatement.setString(3, jsonArray.toString());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addunsuccessfulReports() {
		PlayerData playerData = new PlayerData(getUuid()).load();

		JSONObject playerObject = playerData.jsonObject.getJSONObject("NetworkStats");
		int currentReports = playerObject.getInt("unsuccessfulReports");
		playerObject.remove("unsuccessfulReports");
		playerObject.put("unsuccessfulReports", currentReports + 1);
		playerData.setValue("NetworkStats", playerObject);
		
	}
	
	public void addSuccesfulReports() {
		PlayerData playerData = new PlayerData(getUuid()).load();

		JSONObject playerObject = playerData.jsonObject.getJSONObject("NetworkStats");
		int currentReports = playerObject.getInt("successfulReports");
		playerObject.remove("successfulReports");
		playerObject.put("successfulReports", currentReports + 1);
		playerData.setValue("NetworkStats", playerObject);
	}
	
	public void delete() {
		if (!(reportID == null)) {
			LifeCore.getInstance().getSql().update("DELETE FROM reportData WHERE id ='" + reportID + "'");
		}
	}

	public UUID getUuid() {
		return uuid;
	}

	public ReportEnums getEnums() {
		return enums;
	}

	public void setEnums(ReportEnums enums) {
		this.enums = enums;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

}
