package me.cheeezair.customEntities;


import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityPhantom;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityVillagerAbstract;
import net.minecraft.server.v1_16_R3.EntityZombie;
import net.minecraft.server.v1_16_R3.EnumMoveType;
import net.minecraft.server.v1_16_R3.IWorldReader;
import net.minecraft.server.v1_16_R3.MathHelper;
import net.minecraft.server.v1_16_R3.PathfinderGoalGotoTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalPanic;
import net.minecraft.server.v1_16_R3.Vec3D;

public class NecroZombie extends EntityZombie{
	Player owner;
	public NecroZombie(Location loc, Player owner) {
	    super(EntityTypes.ZOMBIE, (((CraftWorld)loc.getWorld()).getHandle()));
	    this.owner = owner;

	    this.setCustomName(new ChatComponentText(ChatColor.RED + "MINION"));
	    this.setCustomNameVisible(true);
	    this.setPosition(loc.getX(), loc.getY(), loc.getZ());
	    this.setBaby(true);
	    List<Block> lineOfSight = owner.getLineOfSight(null, 10);
		List<Entity> nearbyEntities = owner.getNearbyEntities(10, 10, 10);
		for(Block b : lineOfSight) {
			for(Entity e : nearbyEntities) {
				Location locs = new Location(loc.getWorld(), e.getLocation().getBlockX(), e.getLocation().getBlockY(), e.getLocation().getBlockZ());
				if(locs.equals(b.getLocation())) {
					CraftEntity entity = (CraftEntity) e;
					this.setGoalTarget((EntityLiving) entity.getHandle(),TargetReason.CUSTOM, true);
					
					return;
				}
			}
		}
	    
	 }
	@Override
	public void tick() {
		super.tick();
		

	}
	@Override
	protected void m() {
		super.m();
	}
	
	@Override
	public void movementTick() {
		super.movementTick();
		this.extinguish();
		
	}

}
