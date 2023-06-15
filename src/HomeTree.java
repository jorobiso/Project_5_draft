import processing.core.PImage;

import java.util.List;

public class HomeTree extends Entity {

    protected static final String HOME_TREE_KEY = "homeTree";

    public HomeTree(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }
//
//    @Override
//    public void executeActivityAction(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        super.executeActivityAction(world, imageStore, scheduler);
//
//        if (!((Wiggler) this).transform(world, scheduler, imageStore)) {
//
//        }
//    }
//
//    public void spawnGroot(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Point homeTreePosition = this.getPosition();
//
//        // Check if the spawn point is valid and unoccupied
//        if (world.withinBounds(spawnPoint) && !world.isOccupied(spawnPoint)) {
//            Groot groot = new Groot(Groot.getGrootKey(), spawnPoint, imageStore.getImageList(Groot.getGrootKey()),
//            Groot.getGrootActionPeriod(), Groot.getGrootAnimationPeriod()
//
//            world.addEntity(groot);
//            groot.scheduleActions(scheduler, world, imageStore);
//        }
//    }


    public static String getHomeTreeKey() {
        return HOME_TREE_KEY;
    }

}

