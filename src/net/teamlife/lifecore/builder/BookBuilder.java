package net.teamlife.lifecore.builder;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookBuilder extends ItemBuilder{
	
	  private BookMeta bm;
	  
	  public BookBuilder(ItemStack itemStack)
	  {
	    super(itemStack);
	  }
	  
	  public BookBuilder(int amount) { super(Material.WRITTEN_BOOK, amount); }
	  
	  public BookBuilder setAuthor(String name)
	  {
	    bm = ((BookMeta)is.getItemMeta());
	    bm.setAuthor(name);
	    is.setItemMeta(bm);
	    return this;
	  }
	  
	  public BookBuilder addPage(String content) { bm = ((BookMeta)is.getItemMeta());
	    bm.addPage(new String[] { content });
	    is.setItemMeta(bm);
	    return this;
	  }
	  
	  public BookBuilder addPages(List<String> contents) { bm = ((BookMeta)is.getItemMeta());
	    for (String content : contents) {
	      bm.addPage(new String[] { content });
	    }
	    is.setItemMeta(bm);
	    return this;
	  }
	  
	  public int getPageCount() { return bm.getPageCount(); }
	}