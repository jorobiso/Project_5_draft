import java.util.List;

import processing.core.PImage;

public abstract class Schedulable extends Entity {
    private double animationPeriod;

    public Schedulable(String id, Point position, double animationPeriod, List<PImage> images) {
        super(id, position, images);
        this.animationPeriod = animationPeriod;

    }
    
    public double getAnimationPeriod() {
        return animationPeriod;
    }


    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
}
