import java.util.List;

import processing.core.PImage;

public class Obstacle extends Schedulable {
    static final String OBSTACLE_KEY = "obstacle";
    static final int OBSTACLE_ANIMATION_PERIOD = 0;
    static final int OBSTACLE_NUM_PROPERTIES = 1;

    public Obstacle(String id, Point position, double animationPeriod, List<PImage> images) {
        super(id, position, animationPeriod, images);
        
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }


    // public Entity createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
    //     return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0, animationPeriod, 0, 0);
    // }
}
