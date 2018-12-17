package net.teamlife.lifecore.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SkullBuilder extends ItemBuilder {
	private org.bukkit.inventory.meta.SkullMeta sm;

	public SkullBuilder(ItemStack itemStack) {
		super(itemStack);
	}

	@Deprecated
	public SkullBuilder(int amount) {
		super(Material.SKULL_ITEM, amount, 3);
	}

	public SkullBuilder(Material material, int amount) {
		super(material, amount);
	}

	public SkullBuilder setOwner(String owner) {
		sm = ((org.bukkit.inventory.meta.SkullMeta) is.getItemMeta());
		sm.setOwner(owner);
		is.setItemMeta(sm);
		return this;
	}
}