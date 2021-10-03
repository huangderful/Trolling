package me.cheeezair;

import org.bukkit.plugin.java.JavaPlugin;

import me.cheeezair.commands.Explode;
import me.cheeezair.listeners.Listeners;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
	}
}
