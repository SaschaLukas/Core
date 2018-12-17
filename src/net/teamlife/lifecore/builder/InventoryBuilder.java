package net.teamlife.lifecore.builder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class InventoryBuilder {
	private Inventory inv;

	public InventoryBuilder(InventoryHolder owner, int size) {
		inv = Bukkit.createInventory(owner, size);
	}

	public InventoryBuilder(InventoryHolder owner, InventoryType type) {
		inv = Bukkit.createInventory(owner, type);
	}

	public InventoryBuilder(InventoryHolder owner, int size, String title) {
		inv = Bukkit.createInventory(owner, size, title);
	}

	public InventoryBuilder(InventoryHolder owner, InventoryType type, String title) {
		inv = Bukkit.createInventory(owner, type, title);
	}

	public InventoryBuilder(Inventory inv) {
		this.inv = inv;
	}

	public InventoryBuilder setItem(int slot, ItemStack itemstack) {
		inv.setItem(slot, itemstack);
		return this;
	}

	public InventoryBuilder setItems(Map<Integer, ItemStack> items) {
		int i = 0;
		for (ItemStack is : items.values()) {
			if (is != null) {
				inv.setItem(i, is);
			}
			i++;
		}
		return this;
	}

	public InventoryBuilder addItem(ItemStack itemstack) {
		inv.addItem(new ItemStack[] { itemstack });
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
		}
		return null;
	}

	public Map<Integer, ItemStack> fromBase64(String from) {
		Map<Integer, ItemStack> items = new HashMap();
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
		return items;
	}

	public Inventory create() {
		return inv;
	}
}