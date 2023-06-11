import java.util.List;

public class Phenomenon {

    public static void terraform(String key, WorldModel world, ImageStore imageStore, Point pressed) {
        switch (key) {
            case "portal":
            // set point clicked to corrupted TODO: add portal
            world.setBackgroundCell(pressed, new Background("cgrass", imageStore.getImageList("cgrass")));
            // if (world.isOccupied(pressed)) {
            //     world.removeEntity(world.getOccupancyCell(pressed), scheduler);
            // }
            world.addEntity(new Portal(Portal.getPortalKey(), pressed, imageStore.getImageList(Portal.getPortalKey())));

            List<Point> corruptedArea = pressed.getAdjacentTiles();
            // List<Point> corruptedPtLst = corruptedArea
            //     .filter(canPassThrough);

            // Predicate<Point> canPassThrough = p -> world.withinBounds(p);

            for (Point p : corruptedArea) {
                if (world.withinBounds(p))
                {world.setBackgroundCell(p, new Background("cgrass", imageStore.getImageList("cgrass")));}
            }
            case "sprite":
            case "groot":
        }
    }
}
