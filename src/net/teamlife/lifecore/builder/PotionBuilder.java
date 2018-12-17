package net.teamlife.lifecore.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionBuilder extends ItemBuilder
{
	  private PotionMeta pm;
	  
	  public PotionBuilder(ItemStack itemStack)
	  {
	    super(itemStack);
	  }
	  
	  public PotionBuilder(Material material, int amount) { super(material, amount); }
	  
	  @Deprecated
	  public PotionBuilder(int id, int amount) {
	    super(id, amount);
	  }
	  
	  @Deprecated
	  public PotionBuilder(Material material, int amount, int subid) { super(material, amount, subid); }
	  
	  @Deprecated
	  public PotionBuilder(int id, int amount, int subid) {
	    super(id, amount, subid);
	  }
	  
	  public PotionBuilder setColor(org.bukkit.Color color) {
	    pm = ((PotionMeta)is.getItemMeta());
	    is.setItemMeta(pm);
	    return this;
	  }
	  
	  public PotionBuilder addCustomEffect(PotionEffectType type, int duration, int amplifier) { pm = ((PotionMeta)is.getItemMeta());
	    PotionEffect effect = new PotionEffect(type, duration, amplifier, true);
	    pm.addCustomEffect(effect, true);
	    is.setItemMeta(pm);
	    return this;
	  }
	}