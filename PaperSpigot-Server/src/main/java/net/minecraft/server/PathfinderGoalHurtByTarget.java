package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class PathfinderGoalHurtByTarget extends PathfinderGoalTarget {

    boolean a;
    private int b;

    public PathfinderGoalHurtByTarget(EntityCreature entitycreature, boolean flag) {
        super(entitycreature, false);
        this.a = flag;
        this.a(1);
    }

    public boolean a() {
        int i = this.c.aK();

        return i != this.b && this.a(this.c.getLastDamager(), false);
    }

    public void c() {
    	this.c.setGoalTarget(this.c.getLastDamager());
        this.b = this.c.aK();
        if (this.a) {
            double d0 = this.f();
            List list = this.c.world.a(this.c.getClass(), AxisAlignedBB.a(this.c.locX, this.c.locY, this.c.locZ, this.c.locX + 1.0D, this.c.locY + 1.0D, this.c.locZ + 1.0D).grow(d0, 10.0D, d0));
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityCreature entitycreature = (EntityCreature) iterator.next();

                if (this.c != entitycreature && entitycreature.getGoalTarget() == null && !entitycreature.c(this.c.getLastDamager())) {
                    // CraftBukkit start - call EntityTargetEvent
                    EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(entitycreature, this.c.getLastDamager(), EntityTargetEvent.TargetReason.TARGET_ATTACKED_NEARBY_ENTITY);
                    if (event.isCancelled()) {
                        continue;
                    }
                    entitycreature.setGoalTarget(event.getTarget() == null ? null : ((CraftLivingEntity) event.getTarget()).getHandle());
                    // CraftBukkit end
                }
            }
        }
        super.c();
    }
}
