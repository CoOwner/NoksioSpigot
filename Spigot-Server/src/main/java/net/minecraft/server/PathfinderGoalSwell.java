package net.minecraft.server;

public class PathfinderGoalSwell extends PathfinderGoal {

    EntityCreeper a;
    // EntityLiving b; // Spigot

    public PathfinderGoalSwell(EntityCreeper entitycreeper) {
        this.a = entitycreeper;
        this.a(1);
    }

    public boolean a() {
        EntityLiving entityliving = this.a.getGoalTarget();

        return this.a.cb() > 0 || entityliving != null && this.a.f(entityliving) < 9.0D;
    }

    public void c() {
        this.a.getNavigation().h();
        // this.b = this.a.getGoalTarget(); // Spigot
    }

    public void d() {
        // this.b = null; // Spigot
    }

    public void e() {
        // Spigot start
        EntityLiving b = this.a.getGoalTarget();
        if (b == null) {
            this.a.a(-1);
        } else if (this.a.f(b) > 49.0D) {
            this.a.a(-1);
        } else if (!this.a.getEntitySenses().canSee(b)) {
            this.a.a(-1);
        } else {
            this.a.a(1);
        }
        // Spigot end
    }
}
