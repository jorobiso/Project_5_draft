import java.util.List;
import java.util.Optional;

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
}
