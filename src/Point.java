import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A simple class representing a location in 2D space.
 */
public final class Point {
    public final int x;
    public final int y;
    public Point parent;
    public int g;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setParent(Point parent) {
        this.parent = parent;
    }

    public void setG(int num) {
        this.g = num;
    }

    // public Integer getPathLen() {
    //     int sum = 0;
    //     if (this.parent == null) {
    //         return sum;
    //     }
    //     sum ++;
    //     return this.parent.getPathLen();
    // }

    public void getPathPoints(List<Point> path) {
        if (this.parent != null) {
            path.add(0, this);
            this.parent.getPathPoints(path);
        }
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other) {
        return other instanceof Point && ((Point) other).x == this.x && ((Point) other).y == this.y;
    }

    public int hashCode() {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }

    public static int distanceSquared(Point p1, Point p2) {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;
    
        return deltaX * deltaX + deltaY * deltaY;
    }

    public static boolean adjacent(Point p1, Point p2) {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) || (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
    }

    public Optional<Entity> nearestEntity(List<Entity> entities) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.getPosition(), this);
    
            for (Entity other : entities) {
                int otherDistance = distanceSquared(other.getPosition(), this);
    
                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }
    
            return Optional.of(nearest);
        }
    }

    List<Point> getOrdinalTiles(WorldModel world) {
        int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
        int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};

        List<Point> adjacentTiles = new ArrayList<>();
        for (int i = 0; i < dx.length; i++) {
            Point p = new Point(this.x + dx[i], this.y + dy[i]);
            if (world.withinBounds(p)) {
                adjacentTiles.add(p);
            }
        }
            
        return adjacentTiles;
    }

    List<Point> getSpreadTiles(WorldModel world) {
        int[] dx = {-2, -2, -1, -1, 0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 4};
        int[] dy = {-2, 0, -1, 1, -2, -1, 1, 2, -1, 0, 2, -2, 0, 2, -3};

        List<Point> adjacentTiles = new ArrayList<>();
        for (int i = 0; i < dx.length; i++) {
            Point p = new Point(this.x + dx[i], this.y + dy[i]);
            if (world.withinBounds(p)) {
                adjacentTiles.add(p);
            }
        }
            
        return adjacentTiles;
    }


    List<Point> getDiamondTiles(WorldModel world) {
        int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1, -2, 2, 0, 0};
        int[] dy = {0, 0, -1, 1, -1, 1, -1, 1, 0, 0, -2, 2};

        List<Point> adjacentTiles = new ArrayList<>();
        for (int i = 0; i < dx.length; i++) {
            Point p = new Point(this.x + dx[i], this.y + dy[i]);
            if (world.withinBounds(p)) {
                adjacentTiles.add(p);
            }
        }

        return adjacentTiles;
    }
    
}
