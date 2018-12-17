package net.teamlife.lifecore.builder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class ItemBuilder {

	protected ItemStack is;
	protected ItemMeta im;

	public ItemBuilder() {
	}

	@SuppressWarnings("deprecation")
	public ItemBuilder(String from) {
		int id = 0;
		int subid = 0;
		int amount = 1;
		if (from.contains(":")) {
			String[] array = from.split(":");
			id = Integer.valueOf(array[0]).intValue();
			String a = array[1];
			if (array[1].startsWith("1")) {
				a = a.substring(0, 2);
			} else {
				a = a.substring(0, 1);
			}
			subid = Integer.valueOf(a).intValue();
		}
		if (from.contains(", ")) {
			String[] array = from.split(", ");
			amount = Integer.valueOf(array[1]).intValue();
		}
		is = new ItemStack(id, amount, (short) subid);
	}

	public ItemBuilder(ItemStack itemStack) {
		is = new ItemStack(itemStack);
	}

	public ItemBuilder(Material material) {
		is = new ItemStack(material);
	}

	public ItemBuilder(Material material, int amount) {
		is = new ItemStack(material, amount);
	}

	public ItemBuilder(Material material, int amount, int subid) {
		is = new ItemStack(material, amount, (short) subid);
	}

	@Deprecated
	public ItemBuilder(int id) {
		is = new ItemStack(id);
	}

	@Deprecated
	public ItemBuilder(int id, int amount) {
		is = new ItemStack(id, amount);
	}

	@Deprecated
	public ItemBuilder(int id, int amount, int subid) {
		is = new ItemStack(id, amount, (short) subid);
	}

	public ItemBuilder setDurability(int durability) {
		is.setDurability((short) durability);
		return this;
	}

	public ItemBuilder setDisplayName(String name) {
		im = is.getItemMeta();
		im.setDisplayName(name);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilder addEnchant(Enchantment enchantment, int level) {
		im = is.getItemMeta();
		im.addEnchant(enchantment, level, true);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilder addEnchants(Map<Enchantment, Integer> enchantments) {
		im = is.getItemMeta();
		if (!enchantments.isEmpty()) {
			for (Enchantment ench : enchantments.keySet()) {
				im.addEnchant(ench, ((Integer) enchantments.get(ench)).intValue(), true);
			}
		}
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilder addItemFlag(ItemFlag itemflag) {
		im = is.getItemMeta();
		im.addItemFlags(new ItemFlag[] { itemflag });
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		im = is.getItemMeta();
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}

	public String toBase64() {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
			dataOutput.writeObject(is);
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
		}
		return null;
	}

	public ItemBuilder fromBase64(String from) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(from));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			is = ((ItemStack) dataInput.readObject());
			dataInput.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}

	public ItemStack build() {
		return is;
	}
}
