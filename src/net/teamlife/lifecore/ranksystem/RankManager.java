package net.teamlife.lifecore.ranksystem;

import java.util.UUID;

import net.teamlife.bungee.json.JSONObject;
import net.teamlife.lifecore.LifeCore;
import net.teamlife.lifecore.data.PlayerData;

public class RankManager {
	
	
	
	public static Rank getRank(UUID uuid) {
		
			JSONObject jsonObject = null;
			PlayerData data = new PlayerData(uuid).load();
			LifeCore.getInstance().getPlayers2().put(uuid, data);
			jsonObject = data.jsonObject.getJSONObject("NetworkData");
		
		return RankManager.getRankFromString(jsonObject.getString("currentRank"));
	}
	
	
	
	public static Rank getRankFromString(String s) {
		for (Rank r : Rank.values()) {
			if (r.name().equalsIgnoreCase(s))
				return r;
		}
		return Rank.USER;
	}

}
