package net.teamlife.lifecore.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.teamlife.bungee.json.JSONArray;
import net.teamlife.bungee.json.JSONObject;
import net.teamlife.lifecore.LifeCore;

public class ClanData {

	private String name;
	private UUID ownerUniqueID;
	private JSONObject jsonObject;

	public ClanData(String name) {
		setName(name);
	}

	public ClanData load() {
		ResultSet resultSet = LifeCore.getInstance().getSql()
				.getResult("SELECT * FROM ClanData WHERE name='" + getName() + "'");

		try {
			if (resultSet.next()) {
				setName(resultSet.getString(1));
				setJsonObject(new JSONObject(resultSet.getString(2)));
				setOwnerUniqueID(UUID.fromString(jsonObject.getString("ownerUniqueId")));
			}
		} catch (SQLException e) {

		}

		return this;

	}

	public void create(UUID owneerUniqueID, String clanTag) {
		jsonObject = new JSONObject();

		jsonObject.put("name", getName()).put("clanTag", clanTag).put("clanBase", "null")
				.put("ownerUniqueId", ownerUniqueID).put("maxMembers", "8")
				.put("createTime", System.currentTimeMillis()).put("members", new JSONArray()).put("clanTokens", 0)
				.put("clanLog", new JSONArray()).put("ClanUpgrades", new JSONArray());

		try {

			PreparedStatement preparedStatement = LifeCore.getInstance().getSql().getCon()
					.prepareStatement("INSERT INTO ClanData(name, jsonString) VALUES (?,?)");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, jsonObject.toString());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void delete() {
		LifeCore.getInstance().getSql().update("DELECT FROM ClanData WHERE name = '" + getName() + "'");
	}

	public void save() {
		LifeCore.getInstance().getSql().update("UPDATE ClanData SET jsonString ='" + getJsonObject().toString()
				+ "' WHERE name = '" + getName() + "'");
	}

	public void addMember(UUID uuid) {
		if (!jsonObject.getJSONArray("members").getMyArrayList().contains(uuid.toString())) {
			jsonObject.getJSONArray("members").getMyArrayList().add(uuid.toString());
		}
		
		save();
	}

	public void removeMember(UUID uuid) {
		if (jsonObject.getJSONArray("members").getMyArrayList().contains(uuid.toString())) {
			jsonObject.getJSONArray("members").getMyArrayList().remove(uuid.toString());
		}
		
		save();
	}

	public List<UUID> getMembers() {
		List<UUID> members = new ArrayList<>();

		for (Object object : jsonObject.getJSONArray("members").getMyArrayList())
			members.add(UUID.fromString(object.toString()));

		return members;
	}
	
	public String getName() {
		return name;
	}

	public UUID getOwnerUniqueID() {
		return ownerUniqueID;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwnerUniqueID(UUID ownerUniqueID) {
		this.ownerUniqueID = ownerUniqueID;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

}
