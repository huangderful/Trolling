package me.cheeezair.listeners;

import java.util.ArrayList;
import java.util.List;

import javax.print.CancelablePrintJob;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Item;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Trident;
import org.bukkit.entity.Vex;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.ArrowBodyCountChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Button;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.cheeezair.Main;
import me.cheeezair.customEntities.CustomPhantom;
import me.cheeezair.customEntities.NecroZombie;
import net.minecraft.server.v1_16_R3.EntityEnderDragon;
import net.minecraft.server.v1_16_R3.EntityPhantom;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityTypes.Builder;
import net.minecraft.server.v1_16_R3.EnumCreatureType;
import net.minecraft.server.v1_16_R3.WorldServer;

public class Listeners implements Listener{
	private Main plugin;
	private int fireballCounter = 0, fireCharges = 0, advCounter = 0;
	boolean onLeash;
	public Listeners(Main plugin) {
		this.plugin = plugin;
		onLeash = true;
	}
	@EventHandler
	public void chunkLoad(ChunkLoadEvent event) {
		Chunk chunk = event.getChunk();
		World world = event.getWorld();
		for(int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				Block highestBlock = world.getHighestBlockAt(chunk.getX() * 16 + x, chunk.getZ() * 16 + z);
				if(highestBlock.getType().equals(Material.SAND) 
						&& !(highestBlock.getBiome().equals(Biome.DESERT) || highestBlock.getBiome().equals(Biome.DESERT_HILLS)) || highestBlock.getBiome().equals(Biome.DESERT_LAKES)){
					world.getBlockAt(highestBlock.getLocation().add(0, -1, 0))
					.setType(Material.TNT);
					
				}
			}
		}
	}
	@EventHandler
	public void entityDie(EntityDeathEvent event){
		Location loc = event.getEntity().getLocation();
		World world = event.getEntity().getWorld();
		if(event.getEntityType() == EntityType.PIG) {
			world.createExplosion(loc, 20F);
		}

		else if(event.getEntity() instanceof Sheep) {
			event.getEntity().getWorld().getBlockAt(loc).setType(Material.LAVA);
			event.getEntity().getWorld().getBlockAt(loc.add(1, 0, 0)).setType(Material.LAVA);
			event.getEntity().getWorld().getBlockAt(loc.add(-2, 0, 0)).setType(Material.LAVA);
			event.getEntity().getWorld().getBlockAt(loc.add(0, 0, -1)).setType(Material.LAVA);
			event.getEntity().getWorld().getBlockAt(loc.add(1, 0, 0)).setType(Material.LAVA);
			event.getEntity().getWorld().getBlockAt(loc.add(1, 0, 0)).setType(Material.LAVA);
			event.getEntity().getWorld().getBlockAt(loc.add(0, 0, -1)).setType(Material.LAVA);
			event.getEntity().getWorld().getBlockAt(loc.add(-1, 0, 0)).setType(Material.LAVA);
			event.getEntity().getWorld().getBlockAt(loc.add(-1, 0, 0)).setType(Material.LAVA);

		}
		else if(event.getEntity() instanceof Bat) {
			for(int i = 0; i < 4; i++) {
				world.spawnEntity(loc, EntityType.VEX);
			}
		}
		else if(event.getEntity() instanceof Piglin || 
				event.getEntity() instanceof PiglinBrute) {
			
			Phantom phan = (Phantom) world.spawnEntity(loc, EntityType.PHANTOM);
			new BukkitRunnable() {
				public void run(){
					if(phan.isDead()) {
						cancel();
					}
					else {
						world.spawnEntity(phan.getLocation(), EntityType.PIG);
					}
				} 
			}.runTaskTimer(plugin, 0L, 20L);
			

		}
		else if(event.getEntity() instanceof Spider) {
			for(int i = 0; i < 7; i++) {
				world.spawnEntity(loc, EntityType.PUFFERFISH);
			}		
			
		}
	}
	@EventHandler
	public void playerSwimming(EntityToggleSwimEvent event) {
		World world = event.getEntity().getWorld();
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			Location loc = player.getLocation();
			
			EntityType drowned = EntityType.DROWNED;
			Drowned tridentBoy = (Drowned) world.spawnEntity(loc, drowned);
			
			ItemStack chest = new ItemStack(Material.PUFFERFISH);
			tridentBoy.getEquipment().setHelmet(chest);
			tridentBoy.getEquipment().setItemInHand(new ItemStack(Material.TRIDENT));

			tridentBoy.setLootTable(null);
			
		}
	}
	@EventHandler
	public void entityDamaged(EntityDamageEvent event) {
		if(event.getEntity() instanceof Drowned) {
			Drowned drowned = (Drowned) event.getEntity();
			if(drowned.getEquipment().getHelmet().equals(new ItemStack(Material.PUFFERFISH))) {
				drowned.setArrowCooldown(0);
				drowned.getEquipment().setHelmet(new ItemStack(Material.ZOMBIE_HEAD));
				drowned.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
				
			}
		}
		else if(event.getEntity() instanceof Cow) {
			if(event.getCause() == DamageCause.BLOCK_EXPLOSION
					|| event.getCause() == DamageCause.ENTITY_EXPLOSION) {
				Cow cow = (Cow) event.getEntity();
				cow.setHealth(10);

			}
		}
		
	}
	@EventHandler
	public void damagedByEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Zombie) {
			if(event.getDamager() instanceof Player) {
				Player p = (Player) event.getDamager();
				p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 7));

			}
		}
	}

	@EventHandler
	public void projectileLand(ProjectileHitEvent event) {
		EntityType thrown = event.getEntityType();
        Location loc = event.getEntity().getLocation();
        
        int random = (int) Math.random() * 100;

        if(thrown != null && thrown.equals(EntityType.TRIDENT) && random < 5) {            
            event.getEntity().getWorld().getBlockAt(loc).setType(Material.PINK_WOOL);
            event.getEntity().getWorld().getBlockAt(loc.add(1, 0, 0)).setType(Material.PINK_WOOL);
            event.getEntity().getWorld().getBlockAt(loc.add(-2, 0, 0)).setType(Material.PINK_WOOL);
            event.getEntity().getWorld().getBlockAt(loc.add(1, 1, 0)).setType(Material.PINK_WOOL);
            event.getEntity().getWorld().getBlockAt(loc.add(0, 1, 0)).setType(Material.PINK_WOOL);
        }
        else if(thrown.equals(EntityType.ARROW) && !(event.getEntity().getShooter() instanceof Player) && random == 0){
        	event.getEntity().getWorld().createExplosion(loc, 2F);
        	event.getEntity().remove();
        }
        else if(thrown.equals(EntityType.FIREBALL) && random == 0) {
        		event.getEntity().getWorld().createExplosion(loc, 15F);
        	
        }
	}
	@EventHandler
	public void onBlockBroken(BlockBreakEvent event) {
		Location loc = event.getBlock().getLocation();
		World world = event.getBlock().getWorld();
		Material type = event.getBlock().getType();
		event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 30, 5));
		if(type.equals(Material.DIRT) 
				|| type.equals(Material.GRASS_BLOCK)) {
			EntityType s = EntityType.SKELETON;
			Skeleton skelly = (Skeleton) world.spawnEntity(loc, s);
			skelly.setAI(false);
			ItemStack chest = new ItemStack(Material.SLIME_BLOCK);
			chest.addUnsafeEnchantment(Enchantment.THORNS, 500);
			skelly.getEquipment().setHelmet(chest);
			skelly.setLootTable(null);
		}
		
		else if(type.equals(Material.COAL_ORE)) {
			world.spawnEntity(loc, EntityType.BAT);
		}
		else if(type.equals(Material.POISONOUS_POTATO)) {
			for(int i = 0; i < (int)(20 * Math.random()); i++) {
				world.dropItemNaturally(loc, new ItemStack(Material.ENDER_PEARL));
			}
		}
		else if(type.equals((Material.DEAD_BUSH))){
	
			Location loca = event.getBlock().getLocation();
			CustomPhantom p = new CustomPhantom(loca);
			WorldServer worlds = ((CraftWorld)event.getBlock().getWorld()).getHandle();
			worlds.addEntity(p);
			
			p.setPosition(loc.getX(), loc.getY(), loc.getZ());
			
		}
		else if(type.equals(Material.IRON_ORE)) {
			
		}
	
		
		
		
	}
	@EventHandler
	public void playerMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		World world = event.getPlayer().getWorld();
		if(world.getBlockAt(p.getLocation().add(0, -1, 0)).getBiome().equals(Biome.NETHER_WASTES)
				&& !world.getBlockAt(p.getLocation().add(0, -1, 0)).getType().equals(Material.AIR)) {
			world.getBlockAt(p.getLocation().add(0, -1, 0)).setType(Material.SNOW_BLOCK);
		}
		List<Entity> nearEntities = p.getNearbyEntities(10, 10, 10);
		for(Entity entity : nearEntities) {
			Zombie z = (Zombie) entity;
			z.launchProjectile(null);
			if(entity instanceof Cow) {
				
		    	Cow cow = (Cow) entity;
		    	
				Fireball fire = cow.launchProjectile(Fireball.class);
				Vector velocity = p.getLocation().toVector().subtract(fire.getLocation().toVector()).normalize();
				fire.setVelocity(fire.getVelocity().add(velocity));
				
				

			}
			else if(entity instanceof Chicken) {
				Chicken chick = (Chicken) entity;
				if(chick.isOnGround()) {
					world.dropItemNaturally(chick.getLocation(), new ItemStack(Material.COCOA_BEANS));
					chick.teleport(chick.getLocation().add(0, 12, 0));
				}
				
			}
			else if(entity instanceof EnderDragon) {
//				EnderDragon drag = (EnderDragon) entity;
//				Potion potion = new Potion(PotionType.INSTANT_DAMAGE);
//				 
//				// Make it a splash potion
//				potion.setSplash(true);
//				 
//				// Set it to an item stack
//				ItemStack itemStack = new ItemStack(Material.SPLASH_POTION);
//				potion.apply(itemStack);
//				 
//				// Spawn the potion
//				ThrownPotion thrownPotion = drag.launchProjectile(ThrownPotion.class);
//				thrownPotion.setItem(itemStack);
			}
			else if(entity instanceof Zombie && !(entity instanceof Drowned)){
				Zombie zomb = (Zombie) entity;
				if(!zomb.getEquipment().getHelmet().getType().equals(Material.AIR)) {
					return;
				}
				int random = 30;
				if(random % 51 == 0) {
					zomb.getEquipment().setHelmet(new ItemStack(Material.DRAGON_HEAD));
					
					EntityType elderGuard = EntityType.ELDER_GUARDIAN;
					ElderGuardian elder = (ElderGuardian) world.spawnEntity(zomb.getLocation(), elderGuard);
					
					zomb.addPassenger(elder);
					
				}
				if(random == 52) {
					zomb.getEquipment().setHelmet(new ItemStack(Material.DRAGON_HEAD));
					
					EntityType dragon = EntityType.ENDER_DRAGON;
					EnderDragon ender = (EnderDragon) world.spawnEntity(zomb.getLocation(), dragon);
					ender.setGlowing(true);
					ender.setAI(false);
					zomb.addPassenger(ender);
				}
				else if(random % 2 == 0) {
					
					zomb.getEquipment().setHelmet(new ItemStack(Material.DRAGON_HEAD));
					zomb.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
					EntityType skeleton = EntityType.SKELETON;
					Skeleton pass = (Skeleton) world.spawnEntity(zomb.getLocation(), skeleton);
					pass.setGlowing(true);
					zomb.addPassenger(pass);
					
				for(int i = 0; random > 0; i++) {
						Skeleton passRemember = pass;
						random -= i;
						pass = (Skeleton) world.spawnEntity(pass.getLocation(), skeleton);
						passRemember.addPassenger(pass);
					}
				}
				else if(random % 3 == 0) {
					
				}
				else if(random % 4 == 0) {
					

					
				}
				else if(random % 6 == 0) {
				}
				
			}
		}
		List<Entity> closeEntites = p.getNearbyEntities(20, 20, 20);
		int enderPearlCounter = 0;
		for(int i = 0; i < closeEntites.size(); i++) {
			if(closeEntites.get(i).getType().equals(EntityType.ENDER_PEARL)) {
				enderPearlCounter++;
			}
		}

		if(enderPearlCounter < 50) {
			for(Entity entity : closeEntites) {
				if(entity instanceof Squid) {
					Squid squid = (Squid) entity;
					EnderPearl pearl = squid.launchProjectile(EnderPearl.class);
					Vector velocity = p.getLocation().toVector().subtract(pearl.getLocation().toVector()).normalize();
					pearl.setVelocity(pearl.getVelocity().add(velocity));
					
				}
			}
		}

	}
	@EventHandler
	public void playerInteractEvent(PlayerInteractEvent event) {
		System.out.println("HEY");
		Player p = event.getPlayer();
		if(p.getItemInHand().getType().equals(Material.FIRE_CHARGE) && event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			p.launchProjectile(Fireball.class);		
		
		}
		else if(p.getItemInHand().getType().equals(Material.SAND) && event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			p.getWorld().getBlockAt(p.getLocation().add(1, 0, 0)).setType(Material.TORCH);
			p.getWorld().getBlockAt(p.getLocation().add(1, 1, 0)).setType(Material.SAND);

		}
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			Location loc = event.getPlayer().getLocation();
			NecroZombie z = new NecroZombie(loc, p);
			WorldServer world = ((CraftWorld)event.getPlayer().getWorld()).getHandle();
			world.addEntity(z);
			
			z.setPosition(loc.getX(), loc.getY(), loc.getZ());
		}
		
	}
	@EventHandler
	public void onFertilize(BlockFertilizeEvent event) {
		Player p = event.getPlayer();
		List<Block> blocks = new ArrayList<Block>();
		for(int x = -8; x < 8; x++) {
			for(int z = -8; z < 8; z++) {
				for(int y = -8; y < 8; y++) {
					if((event.getBlock().getWorld().getBlockAt(p.getLocation().add(x, y + 1, z)).getType().equals(Material.AIR)
							&& !event.getBlock().getWorld().getBlockAt(p.getLocation().add(x, y - 1, z)).getType().equals(Material.AIR))
							|| (event.getBlock().getWorld().getBlockAt(p.getLocation().add(x, y + 1, z)).getType().equals(Material.CAVE_AIR)
									&& !event.getBlock().getWorld().getBlockAt(p.getLocation().add(x, y - 1, z)).getType().equals(Material.CAVE_AIR))) {
						blocks.add(event.getBlock().getWorld().getBlockAt(p.getLocation().add(x, y, z)));
						
					}
				}
			}
		}
		int random = (int) (blocks.size() * Math.random());
		Block b = blocks.get(random);
		
		b.setType(Material.DIAMOND_BLOCK);
		EntityType s = EntityType.SKELETON;
		Skeleton skelly = (Skeleton) event.getPlayer().getWorld().spawnEntity(event.getBlock().getLocation().add(0, 1, 0), s);
		ItemStack slime = new ItemStack(Material.SLIME_BLOCK);
		slime.addUnsafeEnchantment(Enchantment.THORNS, 500);
		skelly.getEquipment().setHelmet(slime);	
		
		skelly.teleport(b.getLocation().add(0, 5, 0));

		
	}
	
	@EventHandler 
	public void playerAdvancement(PlayerAdvancementDoneEvent event) {
		advCounter++;
	
	}
	@EventHandler
	public void signChange(SignChangeEvent event) {
		if(Math.random() * 100 > 98) {
			event.setLine(0, "GGGGAAAAYYYYYY");
			event.setLine(2, "8=========D");
		}
		

	}
	@EventHandler
	public void pickUpItem(EntityPickupItemEvent event) {
		Player p = event.getEntity().getWorld().getPlayers().get(0);
		if(event.getItem().getItemStack().getType().equals(Material.DIAMOND)){
			List<Block> blocks = new ArrayList<Block>();
			for(int x = -8; x < 8; x++) {
				for(int z = -8; z < 8; z++) {
					for(int y = -8; y < 8; y++) {
						if((event.getEntity().getWorld().getBlockAt(p.getLocation().add(x, y + 1, z)).getType().equals(Material.AIR)
								&& !event.getEntity().getWorld().getBlockAt(p.getLocation().add(x, y - 1, z)).getType().equals(Material.AIR))
								|| (event.getEntity().getWorld().getBlockAt(p.getLocation().add(x, y + 1, z)).getType().equals(Material.CAVE_AIR)
										&& !event.getEntity().getWorld().getBlockAt(p.getLocation().add(x, y - 1, z)).getType().equals(Material.CAVE_AIR))) {
							blocks.add(event.getEntity().getWorld().getBlockAt(p.getLocation().add(x, y, z)));
							
						}
					}
				}
			}
			int random = (int) (blocks.size() * Math.random());
			Block b = blocks.get(random);
			Material original = b.getType();
			b.setType(Material.DIAMOND_BLOCK);

			new Thread(new Runnable() {
				long time1 = System.currentTimeMillis();
				long time2 = System.currentTimeMillis();
				long difference = 10;
				long originalDiff = 10;
				boolean onBlock = false;
				public void run(){
					
					
					event.getEntity().getWorld().getPlayers().get(0).sendMessage(ChatColor.BLUE + "GET ON THE DIAMOND BLOCK");
					while(time2 - time1 < 15000 && !onBlock) {
						if(p.getLocation().add(0, -1,0).getBlock().equals(b)) {
							onBlock = true;
						}
						difference -= time2 - time1;
						difference = (int)difference/1000;
						if(difference != originalDiff) {
							long showtime = 15 + difference;
							if(showtime == 15 || showtime == 10) {
								p.sendMessage(ChatColor.YELLOW + "Time Left: " + showtime);
	
							} else if(showtime < 10) {
								p.sendMessage(ChatColor.RED + "Time Left: " + showtime);
							}
							
							originalDiff = difference;
						}
						
						
						time2 = System.currentTimeMillis();
	
					}
					if(onBlock) {
						new BukkitRunnable() {
					        public void run() {
					        	p.sendMessage("congrats, you dont get to die.");
								b.setType(original);  
					        }
					    }.runTask(plugin);
						
					} else {
						try {
							event.getEntity().getWorld().getPlayers().get(0).setHealth(0);
						} catch(Exception e) {
							throw new IllegalStateException("lol u died and i have a bug im too lazy to fix.");
						}
					}

	
				 
			}}).start();
		}
	}
	@EventHandler
	public void fishing(PlayerFishEvent event) {
		if(event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH) &&
				(event.getCaught().getType().equals(EntityType.COD) || 
						event.getCaught().getType().equals(EntityType.SALMON) || 
						event.getCaught().getType().equals(EntityType.TROPICAL_FISH))) {
			for(int i = 0; i < 100; i++) {
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_CAT_STRAY_AMBIENT, 10000, 1);
			}
			
		}
	} 
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void testing(BlockBreakEvent event) {
		if(event.getBlock().getType().equals(Material.SAND)) {
			EntityType s = EntityType.ARMOR_STAND;
			ArmorStand skelly = (ArmorStand) event.getPlayer().getWorld().spawnEntity(event.getBlock().getLocation().add(0, 1, 0), s);
			SkullMeta  meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);

			meta.setOwner("MHF_Herobrine");

			ItemStack stack = new ItemStack(Material.PLAYER_HEAD,1 , (byte)3);

			stack.setItemMeta(meta);
			
			skelly.setHelmet(stack);
			skelly.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			skelly.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			skelly.setBoots(new ItemStack(Material.DIAMOND_BOOTS));

		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void enterBed(PlayerBedEnterEvent event) {
		int random = (int) (4 * Math.random());
		Location loc;
		if(random < 1) {
			loc = event.getBed().getLocation().add(2, 0, 0);
		} else if(random == 1) {
			loc = event.getBed().getLocation().add(-2, 0, 0);
		}else if(random == 2) {
			loc = event.getBed().getLocation().add(0, 0, 2);
		}else {
			loc = event.getBed().getLocation().add(0, 0, -2);
		}
		EntityType s = EntityType.ARMOR_STAND;
		ArmorStand skelly = (ArmorStand) event.getPlayer().getWorld().spawnEntity(loc, s);
		SkullMeta  meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);

		meta.setOwner("MHF_Herobrine");

		ItemStack stack = new ItemStack(Material.PLAYER_HEAD,1 , (byte)3);

		stack.setItemMeta(meta);
		
		skelly.setHelmet(stack);
		skelly.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		skelly.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		skelly.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
		
	}
	
	@EventHandler
	public void leaveBed(PlayerBedLeaveEvent event) {
		List<Entity> closeEntites = event.getPlayer().getNearbyEntities(4, 2, 4);
		for(int i = 0; i < closeEntites.size(); i++) {
			if(closeEntites.get(i).getType().equals(EntityType.ARMOR_STAND)) {
				ArmorStand a = (ArmorStand) closeEntites.get(i);
				a.remove();
				
			}
		}	
		
	}
	@EventHandler
	public void playerThrowEgg(PlayerEggThrowEvent event) {
		onLeash = true;
		event.getPlayer().sendMessage(ChatColor.RED + "lol either keep breaking eggs or take care of a chicken or die.");
		Player p = event.getPlayer();
		
		Chicken chicken = (Chicken)p.getWorld().spawnEntity(p.getLocation(), EntityType.CHICKEN);
		chicken.setLeashHolder(p);
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void leashed(PlayerLeashEntityEvent e) {
		if(e.getEntity().getType().equals(EntityType.CHICKEN)) {
			onLeash = true;
			e.getPlayer().sendMessage(ChatColor.GREEN +"Saved a chicken.");
		}
	}
	@EventHandler
	public void leashbreak(EntityUnleashEvent e) {
		onLeash = false;

		new Thread(new Runnable() {
			long time1 = System.currentTimeMillis();
			long time2 = System.currentTimeMillis();
			long difference = 10;
			long originalDiff = 10;
			public void run(){
				while(time2 - time1 < 30000) {
					difference -= time2 - time1;
					difference = (int)difference/1000;
					if(difference != originalDiff) {
						long showtime = 30 + difference;
						if(showtime == 30 || showtime == 25 || showtime == 20) {
							e.getEntity().getWorld().getPlayers().get(0).sendMessage(ChatColor.YELLOW + "Time Left: " + showtime);

						} else if(showtime < 20 && showtime > 9) {
							e.getEntity().getWorld().getPlayers().get(0).sendMessage(ChatColor.GOLD + "Time Left: " + showtime);
						} else if(showtime < 10) {
							e.getEntity().getWorld().getPlayers().get(0).sendMessage(ChatColor.RED + "Time Left: " + showtime);
						}
						
						originalDiff = difference;
					}
					if(onLeash) {
						return;
					}
					
					time2 = System.currentTimeMillis();

			}
				try {
					e.getEntity().getWorld().getPlayers().get(0).setHealth(0);
				} catch(Exception e) {
					throw new IllegalStateException("lol u died and i have a bug im too lazy to fix.");
				}

			 
		}}).start();
	}
	@EventHandler
	public void playerInteractWithEntityEvent(PlayerInteractAtEntityEvent event) {
		if(event.getRightClicked().getType().equals(EntityType.PHANTOM)) {
			event.getRightClicked().addPassenger(event.getPlayer());
		}
//		 else {
//			Location loc = event.getPlayer().getLocation();
//			CustomPhantom p = new CustomPhantom(loc);
//			WorldServer world = ((CraftWorld)event.getPlayer().getWorld()).getHandle();
//			world.addEntity(p);
//			
//			p.setPosition(loc.getX(), loc.getY(), loc.getZ());
//		}
		
	}
	@EventHandler
	public void brewEvent(BrewEvent e) {
		
	}
	@EventHandler
	public void arrowInBodyEvent(ArrowBodyCountChangeEvent event) {
	}
	@EventHandler
	public void blockPlace(BlockPlaceEvent event) {
		
	}
}
