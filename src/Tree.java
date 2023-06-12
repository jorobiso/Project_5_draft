import java.util.List;

import processing.core.PImage;

public class Tree extends Wiggler {
    public static final String TREE_KEY = "tree";
    public static final int TREE_ANIMATION_PERIOD = 0;
    public static final int TREE_ACTION_PERIOD = 1;
    public static final int TREE_HEALTH = 2;
    public static final int TREE_NUM_PROPERTIES = 3;
    // public final double TREE_ANIMATION_MAX = 0.600;
    // public final double TREE_ANIMATION_MIN = 0.050;
    // public final double TREE_ACTION_MAX = 1.400;
    // public final double TREE_ACTION_MIN = 1.000;
    // public final int TREE_HEALTH_MAX = 3;
    // public final int TREE_HEALTH_MIN = 1;
    private int health;



    public Tree(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void incHealth() {
        health++;
    }

    

    @Override
    public void executeActivityAction(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (!((Wiggler) this).transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            Stump stump = new Stump(Entity.getSTUMP_KEY() + "_" + this.getId(), this.getPosition(), imageStore.getImageList(Entity.getSTUMP_KEY()));
            world.removeEntity(this, scheduler);
            world.addEntity(stump);
            return true;
        }
    
        return false;
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }


    // private static Entity createTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
    //     return new Entity(EntityKind.TREE, id, position, images, 0, 0, actionPeriod, animationPeriod, health, 0);
    // }


    
}
