import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import processing.core.PImage;

public class Fairy extends Wiggler {
    static final String FAIRY_KEY = "fairy";
    static final int FAIRY_ANIMATION_PERIOD = 0;
    static final int FAIRY_ACTION_PERIOD = 1;
    static final int FAIRY_NUM_PROPERTIES = 2;

    public Fairy(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void executeActivityAction(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest(this, getPosition(), new ArrayList<>(List.of(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {
                Sapling sapling = new Sapling(
                    getSAPLING_KEY() + "_" + fairyTarget.get().getId(), tgtPos, imageStore.getImageList(getSAPLING_KEY()), getSAPLING_ACTION_ANIMATION_PERIOD(), getSAPLING_ACTION_ANIMATION_PERIOD(), 0, getSAPLING_HEALTH_LIMIT()
                    );
                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
    }

    private boolean moveToFairy(WorldModel world, Entity target, EventScheduler scheduler) {
        if (Point.adjacent(this.getPosition(), target.getPosition())) {
            world.removeEntity(target, scheduler);
            return true;
        } else {
            Point nextPos = this.nextPositionFairy(world, target.getPosition());
    
            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    private Point nextPositionFairy(WorldModel world, Point destPos) {
        Point newPos = this.getPosition();
        AStarStrategy pathingStrat = new AStarStrategy();

        // Determine allowance **Fairies can pass though Stumps so we don't need to add: !world.getOccupancyCell(newPos) instanceof Stump
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
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }
}
