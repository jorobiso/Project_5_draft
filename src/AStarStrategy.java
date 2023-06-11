import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

class AStarStrategy implements PathingStrategy {

    /**
     * Creates a new lab pathing strategy the uses the given grid. For this lab, this is a reference to
     * the PathingMain grid, not a new one.
     */
    public AStarStrategy() {
    }

    /**
     * Returns a prefix of a path from the start point to a point within reach
     * of the end point. This path is only valid ("clear") when returned, but
     * may be invalidated by movement of other entities. The prefix includes
     * neither the start nor end points.
     * This implementation is recursive and returns null if no path is found!
     *
     * @param start the point to begin the search from
     * @param end the point to search for a point within reach of
     * @param canPassThrough a function that returns true if the given point is traversable
     * @param withinReach a function that returns true if both points are within reach of each other
     * @param potentialNeighbors a function that returns the neighbors of a given point, as a stream
     */


    public List<Point> computePath(
            Point start,
            Point end,
            Predicate<Point> canPassThrough,
            BiPredicate<Point, Point> withinReach,
            Function<Point, Stream<Point>> potentialNeighbors
    ) {
        if (withinReach.test(start, end)) {
            return new ArrayList<>();
        }
        else
        {
            start.setParent(null);
            start.setG(0);
            // Create an open set represented by a priority queue
            PriorityQueue<Point> openSet = new PriorityQueue<>(Comparator.comparingInt(point -> computeG.apply(point) + computeH.apply(point, end)));    // compute f-value for sorting
            List<Point> closedSet = new ArrayList<>();
            openSet.add(start);
            List<Point> path = new ArrayList<Point>(); // initialize a path

            // path driver
            while (!openSet.isEmpty()) {
                Point cur = openSet.poll();  // poll the lowest f-value neighbor
                closedSet.add(cur);

                // if target found return path
                if (withinReach.test(cur, end)) {
                    cur.getPathPoints(path);  // traces path back to start including cur
                    return path;
                }

                // Get neighboring points
                Stream<Point> neighborStream = potentialNeighbors.apply(cur);

                // Obtain all neighbors filtering out impassable neighbors
                List<Point> neighborList = neighborStream
                    .filter(canPassThrough)
                    .filter(n -> !closedSet.contains(n))
                    .toList();

                // handle backtracking and neighbors
                for (Point n : neighborList) {
                    if (!openSet.contains(n)) {
                        n.setParent(cur);
                        openSet.add(n);
                    }
                }

            }
        }
        return new ArrayList<Point>();
    }


    Function<Point, Integer> computeG = cur -> {
        // handle cur is start case
        if (cur.parent == null) {
            return cur.g;
        }
        cur.g = cur.parent.g + 1;
        return cur.g;
    };

    BiFunction<Point, Point, Integer> computeH = (pos, end) -> {
        int dx = Math.abs(end.x - pos.x);
        int dy = Math.abs(end.y - pos.y);
        return dx + dy;
    };

}