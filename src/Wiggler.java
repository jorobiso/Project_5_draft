import java.util.List;

import processing.core.PImage;

public abstract class Wiggler extends Schedulable implements ActionInterface{
    private double actionPeriod;
    private final double TREE_ANIMATION_MAX = 0.600;
    private final double TREE_ANIMATION_MIN = 0.050;
    private final double TREE_ACTION_MAX = 1.400;
    private final double TREE_ACTION_MIN = 1.000;
    private final int TREE_HEALTH_MAX = 3;
    private final int TREE_HEALTH_MIN = 1;

    
    public Wiggler(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, animationPeriod, images);
        this.actionPeriod = actionPeriod;
        // this.animationPeriod = animationPeriod;
        // this.health = health;
        // this.healthLimit = healthLimit;
    }

    // public Plant(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health) {
    //     super(id, position, images, actionPeriod, animationPeriod);
    //     this.health = health;
    // }

	// public Wiggler(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
    //     super(id, position, images);
    // }

    public double getTREE_ANIMATION_MAX() {return TREE_ANIMATION_MAX;}

    public double getTREE_ANIMATION_MIN() {return TREE_ANIMATION_MIN;}

    public double getTREE_ACTION_MAX() {return TREE_ACTION_MAX;}

    public double getTREE_ACTION_MIN() {return TREE_ACTION_MIN;}

    public int getTREE_HEALTH_MAX() {return TREE_HEALTH_MAX;}

    public int getTREE_HEALTH_MIN() {return TREE_HEALTH_MIN;}


    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this instanceof Tree) {
            return ((Tree) this).transform(world, scheduler, imageStore);
        } else if (this instanceof Sapling) {
            return ((Sapling) this).transform(world, scheduler, imageStore);
        } else if (this instanceof Twistedoak) {
            return ((Twistedoak) this).transform(world, scheduler, imageStore);
        } else {
            throw new UnsupportedOperationException(String.format("transformPlant not supported for %s", this));
        }
    }

    // public int getHealth() {
    //     return health;
    // }

    // public void setHealth(int health) {
    //     this.health = health;
    // }

    // public void incHealth() {
    //     health++;
    // }

    public double getActionPeriod() {
        return actionPeriod;
    }

    // public double getAnimationPeriod() {
    //     return animationPeriod;
    // }

    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    
    public abstract void executeActivityAction(WorldModel world, ImageStore imageStore, EventScheduler scheduler);



}
