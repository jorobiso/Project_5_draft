import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import processing.core.PImage;

public abstract class Dude extends Wiggler {
    public static final String DUDE_KEY = "dude";
    public static int DUDE_ACTION_PERIOD = 0;
    public static int DUDE_ANIMATION_PERIOD = 1;
    public static int DUDE_LIMIT = 2;
    public static int DUDE_NUM_PROPERTIES = 3;
    private int resourceLimit;
    private int resourceCount;

    public Dude(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public void setResourceLimit(int resourceLimit) {
        this.resourceLimit = resourceLimit;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }

    protected Point nextPositionDude(WorldModel world, Point destPos) {
        Point newPos = this.getPosition();
        AStarStrategy pathingStrat = new AStarStrategy();

        // Determine allowance
        Predicate<Point> canPassThrough = p -> world.withinBounds(p) && (!world.isOccupied(p) || (world.getOccupancyCell(p) instanceof Stump));

        // Find reach and neighbors
        BiPredicate<Point, Point> withinReach = (p1, p2) -> (Math.abs(p2.x - p1.x) + Math.abs(p2.y - p1.y)) == 1;
        Function<Point, Stream<Point>> potentialNeighbors = PathingStrategy.CARDINAL_NEIGHBORS;

        // Get path
        List<Point> path = pathingStrat.computePath(this.getPosition(), destPos, canPassThrough, withinReach, potentialNeighbors);

        if (path.isEmpty()) {
            return newPos;
        }
        // System.out.println(path.size());
        return path.get(0);
    }

    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);


}
