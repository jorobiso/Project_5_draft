import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import processing.core.PImage;

public class Worm extends Wiggler {
    private static final String WORM_KEY = "worm";
    private static final double WORM_ANIMATION_PERIOD = 0.4;
    private static final double WORM_ACTION_PERIOD = 1;
    private static final int WORM_NUM_PROPERTIES = 2;

    public Worm(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    

    public static String getWormKey() {
        return WORM_KEY;
    }



    public static double getWormAnimationPeriod() {
        return WORM_ANIMATION_PERIOD;
    }



    public static double getWormActionPeriod() {
        return WORM_ACTION_PERIOD;
    }


    public static int getWormNumProperties() {
        return WORM_NUM_PROPERTIES;
    }
    

    private boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (Point.adjacent(this.getPosition(), target.getPosition())) {
            world.removeEntity(target, scheduler);
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());
    
            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    private Point nextPosition(WorldModel world, Point destPos) {
        Point newPos = this.getPosition();
        AStarStrategy pathingStrat = new AStarStrategy();

        // Determine allowance
        Predicate<Point> canPassThrough = p -> world.withinBounds(p) && (!world.isOccupied(p));
        
        // Find reach and neighbors
        BiPredicate<Point, Point> withinReach = (p1, p2) -> (Math.abs(p2.x - p1.x) + Math.abs(p2.y - p1.y)) == 1;
        Function<Point, Stream<Point>> potentialNeighbors = PathingStrategy.CARDINAL_NEIGHBORS;

        // Get path
        List<Point> path = pathingStrat.computePath(this.getPosition(), destPos, canPassThrough, withinReach, potentialNeighbors);

        if (path.isEmpty()) {
            return newPos;
        }
        return path.get(0);

    }

    @Override
    public void executeActivityAction(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> wormTarget = world.findNearest(this, this.getPosition(), new ArrayList<>(List.of(Dude.class)));

        if (wormTarget.isPresent()) {
            Point tgtPos = wormTarget.get().getPosition();

            if (this.moveTo(world, wormTarget.get(), scheduler)) {
                // delete dude
                world.removeEntity(wormTarget.get(), scheduler);
            }
        }

        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
    }


    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }





    
}
