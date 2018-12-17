package net.teamlife.lifecore.builder;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityBuilder {
	private Entity en;

	public EntityBuilder(EntityType entity, Location loc) {
		en = loc.getWorld().spawnEntity(loc, entity);
	}

	public EntityBuilder(EntityType entity, World world, double x, double y, double z) {
		Location loc = new Location(world, x, y, z);
		en = loc.getWorld().spawnEntity(loc, entity);
	}

	public EntityBuilder(EntityType entity, String world, double x, double y, double z) {
		Location loc = new Location(org.bukkit.Bukkit.getWorld(world), x, y, z);
		en = loc.getWorld().spawnEntity(loc, entity);
	}

	public EntityBuilder setCustomName(String name) {
		en.setCustomName(name);
		return this;
	}

	public EntityBuilder setCustomNameVisible(boolean visible) {
		en.setCustomNameVisible(visible);
		return this;
	}

	public EntityBuilder setPassenger(Entity entity) {
		en.setPassenger(entity);
		return this;
	}

	public EntityBuilder setAge(EntityAge age) {
		if ((en instanceof Ageable)) {
			switch (age) {
			case ADULT:
				((Ageable) en).setBaby();
				break;
			case BABY:
				((Ageable) en).setAdult();
			}
		} else {
			throw new IllegalArgumentException("Entity's age cannot be modified!");
		}
		return this;
	}

	public static enum EntityAge {
		BABY, ADULT;
	}

	public Entity spawn() {
		return en;
	}
}