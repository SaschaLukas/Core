package net.teamlife.lifecore.builder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class PlayerInventoryBuilder {
	Inventory inv;
	Player p;

	public PlayerInventoryBuilder(Player player) {
		p = player;
		inv = player.getInventory();
	}

	public PlayerInventoryBuilder setItems(Map<Integer, ItemStack> items) {
		int i = 0;
		for (ItemStack is : items.values()) {
			if (is != null) {
				inv.setItem(i, is);
			}
			i++;
		}
		return this;
	}

	public String toBase64() {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			dataOutput.writeInt(inv.getSize());

			for (int i = 0; i < inv.getSize(); i++) {
				dataOutput.writeObject(inv.getItem(i));
			}
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks", e);
		}
	}

	public Map<Integer, ItemStack> fromBase64(String from) {
		Map<Integer, ItemStack> items = new HashMap();
		if ((from != null) && (!from.isEmpty())) {
			try {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(from));
				BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
				int size = dataInput.readInt();
				for (int i = 0; i < size; i++) {
					items.put(Integer.valueOf(i), (ItemStack) dataInput.readObject());
				}
				dataInput.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return items;
	}
}
