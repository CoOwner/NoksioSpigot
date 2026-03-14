package net.minecraft.server;

// CraftBukkit start
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftEnderPearl;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.entity.EnderpearlLandEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
// CraftBukkit end

public class EntityEnderPearl extends EntityProjectile {
	
	private Location lastValidTeleport; // RinnySpigot - Fix block glitch with pearl

    public EntityEnderPearl(World world) {
        super(world);
        this.loadChunks = world.paperSpigotConfig.loadUnloadedEnderPearls; // PaperSpigot
    }

    public EntityEnderPearl(World world, EntityLiving entityliving) {
        super(world, entityliving);
        this.loadChunks = world.paperSpigotConfig.loadUnloadedEnderPearls; // PaperSpigot
    }
    
    // RinnySpigot start - Fix block glitch with pearl
	public void h(){
		if (this.world.getCubes(this, this.boundingBox.grow(0.25D, 0.25D, 0.25D)).isEmpty()) {
			this.lastValidTeleport = getBukkitEntity().getLocation();
		}
		super.h();
	}
	// RinnySpigot end

    protected void a(MovingObjectPosition movingobjectposition) {
        if (movingobjectposition.entity != null) {
            movingobjectposition.entity.damageEntity(DamageSource.projectile(this, this.getShooter()), 0.0F);
        }

        // PaperSpigot start - Remove entities in unloaded chunks
        if (inUnloadedChunk && world.paperSpigotConfig.removeUnloadedEnderPearls) {
            die();
        }
        // PaperSpigot end

        for (int i = 0; i < 32; ++i) {
            this.world.addParticle("portal", this.locX, this.locY + this.random.nextDouble() * 2.0D, this.locZ, this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
        }

        if (!this.world.isStatic) {
            if (this.getShooter() != null && this.getShooter() instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) this.getShooter();

                if (entityplayer.playerConnection.b().isConnected() && entityplayer.world == this.world) {
                    // CraftBukkit start - Fire PlayerTeleportEvent
                    org.bukkit.craftbukkit.entity.CraftPlayer player = entityplayer.getBukkitEntity();
                    if(this.lastValidTeleport != null){
	                    EnderpearlLandEvent.Reason reason = movingobjectposition.entity != null ? EnderpearlLandEvent.Reason.ENTITY : EnderpearlLandEvent.Reason.BLOCK;
	                    // RinnySpigot start
	                    CraftEntity bukkitHitEntity = movingobjectposition.entity != null ? movingobjectposition.entity.getBukkitEntity() : null;
	                    EnderpearlLandEvent landEvent = new EnderpearlLandEvent((CraftEnderPearl) getBukkitEntity(), reason, bukkitHitEntity);
	                    // RinnySpigot end
	                    Bukkit.getPluginManager().callEvent(landEvent);
	                    
	                    // RinnySpigot start
	                    if (landEvent.isCancelled()) {
	                    	this.die();
	                    	return;
	                    }
	                    // RinnySpigot end
	
	                    org.bukkit.Location location = lastValidTeleport.clone(); // RinnySpigot - Fix block glitch with pearl
	                    location.setPitch(player.getLocation().getPitch());
	                    location.setYaw(player.getLocation().getYaw());
	
	                    PlayerTeleportEvent teleEvent = new PlayerTeleportEvent(player, player.getLocation(), location, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
	                    Bukkit.getPluginManager().callEvent(teleEvent);
	
	                    if (!teleEvent.isCancelled() && !entityplayer.playerConnection.isDisconnected()) {
	                        if (this.getShooter().am()) {
	                            this.getShooter().mount((Entity) null);
	                        }
	
	                        entityplayer.playerConnection.teleport(teleEvent.getTo());
	                        this.getShooter().fallDistance = 0.0F;
	                        CraftEventFactory.entityDamage = this;
	                        this.getShooter().damageEntity(DamageSource.FALL, 5.0F);
	                        CraftEventFactory.entityDamage = null;
	                    }
                    }
                    // CraftBukkit end
                }
            }
            this.die();
        }
    }
}
