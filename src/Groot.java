import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Groot extends Wiggler {
    private static final String GROOT_KEY = "groot";
    private static final double GROOT_ANIMATION_PERIOD = 0.4;
    private static final double GROOT_ACTION_PERIOD = 1;


    public Groot(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
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
    //Should have Groot search for and replace twistedOaks with sapling
    @Override
    public void executeActivityAction(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> grootTarget = world.findNearest(this, this.getPosition(), new ArrayList<>(List.of(Twistedoak.class)));

        if (grootTarget.isPresent()) {
            Point tgtPos = grootTarget.get().getPosition();

            if (this.moveTo(world, grootTarget.get(), scheduler)) {
                // delete dude
                world.removeEntity(grootTarget.get(), scheduler);
                Sapling sapling = new Sapling(
                        getSAPLING_KEY() + "_" + grootTarget.get().getId(), tgtPos,
                        imageStore.getImageList(getSAPLING_KEY()), getSAPLING_ACTION_ANIMATION_PERIOD(),
                        getSAPLING_ACTION_ANIMATION_PERIOD(), 0, getSAPLING_HEALTH_LIMIT()
                );
                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }


        }

        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
    }


    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }


    public static String getGrootKey() {
        return GROOT_KEY;
    }


    public static double getGrootAnimationPeriod() {
        return GROOT_ANIMATION_PERIOD;
    }


    public static double getGrootActionPeriod() {
        return GROOT_ACTION_PERIOD;
    }

}
