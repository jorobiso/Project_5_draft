import java.util.List;

import processing.core.PImage;

public class Sapling extends Wiggler {
    static final String SAPLING_KEY = "sapling";
    static final int SAPLING_NUM_PROPERTIES = 1;
    static final int SAPLING_HEALTH = 0;
    static final int SAPLING_HEALTH_LIMIT = 5;
    static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    private int healthLimit;
    private int health;

    public Sapling(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.health = health;
        this.healthLimit = healthLimit;
    }

    public int getHealthLimit() {
        return healthLimit;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void incHealth() {
        health = health + 1;
    }


    @Override
    public void executeActivityAction(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.incHealth();
        if (!(this).transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() < 0) {
            Stump stump = new Stump(Stump.getSTUMP_KEY() + "_" + this.getId(), this.getPosition(), ImageStore.getImageList(imageStore, Stump.getSTUMP_KEY()));
    
            world.removeEntity(this, scheduler);
    
            world.addEntity(stump);
    
            return true;
        } else if (this.getHealth() >= this.getHealthLimit()) {
            Tree tree = new Tree(Tree.getTREE_KEY() + "_" + this.getId(), getPosition(), ImageStore.getImageList(imageStore, Tree.getTREE_KEY()), Background.getNumFromRange(getTREE_ACTION_MAX(), getTREE_ACTION_MIN()), Background.getNumFromRange(getTREE_ANIMATION_MAX(), getTREE_ANIMATION_MIN()), Background.getIntFromRange(getTREE_HEALTH_MAX(), getTREE_HEALTH_MIN()));

            world.removeEntity(this, scheduler);
            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);
            return true;
        }
    
        return false;
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }


    // private static Entity createSapling(String id, Point position, List<PImage> images, int health) {
    //     return new Entity(EntityKind.SAPLING, id, position, images, 0, 0, SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, 0, SAPLING_HEALTH_LIMIT);
    // }

}
