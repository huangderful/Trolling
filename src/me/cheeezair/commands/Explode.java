package me.cheeezair.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

import me.cheeezair.Main;

public class Explode implements CommandExecutor{
	private Main plugin;

	public Explode(Main plugin){
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("Only players can send this command.");
			return true;
		}
		Player player = (Player)sender; 
		player.sendMessage("heyo");

		switch(cmd.getName()){
			case "piggies":
				player.sendMessage("hoi");
				Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
					int pigCounter = 10;
					@Override
					public void run(){
						if(pigCounter > 0){
							for(int i = 0; i < 50 ; i++){
								double randomSignX = 2*Math.random();
								double randomSignY = 2*Math.random();
								double randomX = 12*Math.random();
								double randomZ = 12*Math.random();
								
								if(randomSignX < 1){
									randomX -= 2*randomX;
								}
								if(randomSignY < 1){
									randomZ -= 2*randomZ;
								}
								Location loc = player.getLocation().add(randomX, 75, randomZ);
								player.getWorld().spawnEntity(loc, EntityType.PIG);
							}
							pigCounter--;

						}
					} 
					
				}, 0L, 20L);
			break;
			case "fireball":
				player.launchProjectile(Fireball.class);
			break;
		}
		return false;
	}
	
}
