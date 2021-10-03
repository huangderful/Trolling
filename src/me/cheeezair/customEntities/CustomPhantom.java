package me.cheeezair.customEntities;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityPhantom;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EnumMoveType;
import net.minecraft.server.v1_16_R3.MathHelper;

import net.minecraft.server.v1_16_R3.Vec3D;

public class CustomPhantom extends EntityPhantom{

	public CustomPhantom(Location loc) {
	    super(EntityTypes.PHANTOM, (((CraftWorld)loc.getWorld()).getHandle()));
	    this.setCustomName(new ChatComponentText(ChatColor.AQUA + "Specimen"));
	    this.setCustomNameVisible(true);
	    
	    this.setPosition(loc.getX(), loc.getY(), loc.getZ());
	    
	 }
	@Override
	public void tick() {
		super.tick();

		if(this.getPassengers().size() != 0) {
			this.setNoAI(true);
			this.setGoalTarget((EntityLiving) null);
			EntityPlayer player = (EntityPlayer) this.getPassengers().get(0);
			player.startRiding(this);
			this.setYawPitch(player.yaw, -player.pitch);
			this.move(EnumMoveType.SELF,  new Vec3D(-MathHelper.sin((float) (player.yaw * 3.1415926/180)), -MathHelper.sin((float) (player.pitch * 3.1415926/180)), MathHelper.cos((float) (player.yaw * 3.1415926/180))));

		} else {
			this.setNoAI(false);
		}

	}
	
	@Override
	public void movementTick() {
		super.movementTick();
		this.extinguish();
		
	}

}
