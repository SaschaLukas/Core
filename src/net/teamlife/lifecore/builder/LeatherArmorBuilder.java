package net.teamlife.lifecore.builder;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class LeatherArmorBuilder extends ItemBuilder {
	
	private LeatherArmorMeta lm;

	public LeatherArmorBuilder(ItemStack itemStack) {
		super(itemStack);
	}

	public LeatherArmorBuilder(Material material, int amount) {
		super(material, amount);
	}

	@Deprecated
	public LeatherArmorBuilder(int id, int amount) {
		super(id, amount);
	}

	public LeatherArmorBuilder setColor(Color color) {
		lm = ((LeatherArmorMeta) is.getItemMeta());
		lm.setColor(color);
		is.setItemMeta(lm);
		return this;
	}

	public LeatherArmorBuilder setColor(int red, int green, int blue) {
		lm = ((LeatherArmorMeta) is.getItemMeta());
		lm.setColor(Color.fromRGB(red, green, blue));
		is.setItemMeta(lm);
		return this;
	}

	public LeatherArmorBuilder setRandomColor() {
		lm = ((LeatherArmorMeta) is.getItemMeta());
		lm.setColor(Color.fromRGB(randomColor(255) + 1, randomColor(255) + 1, randomColor(255) + 1));
		is.setItemMeta(lm);
		return this;
	}

	private int randomColor(int max) {
		Random r = new Random();
		return r.nextInt(max);
	}
}