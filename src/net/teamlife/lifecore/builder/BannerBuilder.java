package net.teamlife.lifecore.builder;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class BannerBuilder extends ItemBuilder {
	private BannerMeta bm;

	public BannerBuilder(ItemStack itemStack) {
		super(itemStack);
	}

	@Deprecated
	public BannerBuilder(int amount) {
		super(Material.BANNER, amount);
	}

	public BannerBuilder(Material material, int amount) {
		super(material, amount);
	}

	@Deprecated
	public BannerBuilder setBaseColor(DyeColor color) {
		bm = ((BannerMeta) is.getItemMeta());
		bm.setBaseColor(color);
		is.setItemMeta(bm);
		return this;
	}

	public BannerBuilder addPattern(DyeColor color, PatternType pattern) {
		bm = ((BannerMeta) is.getItemMeta());
		bm.addPattern(new org.bukkit.block.banner.Pattern(color, pattern));
		is.setItemMeta(bm);
		return this;
	}
}