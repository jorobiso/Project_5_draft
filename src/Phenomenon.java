import java.util.List;
import java.util.Random;

public class Phenomenon {
    private static boolean otherClick = false;

    public static void terraform(String key, WorldModel world, EventScheduler scheduler, ImageStore imageStore, Point pressed) {
        switch (key) {
            case "portal":
                if (!otherClick) {


                    // make portal
                    Portal portal = new Portal(Portal.getPortalKey(), pressed, imageStore.getImageList(Portal.getPortalKey()));

                    world.setBackgroundCell(pressed, new Background("cgrass", imageStore.getImageList("cgrass")));

                    if (world.isOccupied(pressed)) {
                        world.removeEntity(world.getOccupancyCell(pressed), scheduler);
                    }

                    world.addEntity(portal);

                    // corrupt area
                    List<Point> corruptedArea = pressed.getDiamondTiles(world);


                    for (Point p : corruptedArea) {
                        world.setBackgroundCell(p, new Background("cgrass", imageStore.getImageList("cgrass")));
                        if (world.getOccupancyCell(p) instanceof Sapling || world.getOccupancyCell(p) instanceof Stump) {
                            world.removeEntity(world.getOccupancyCell(p), scheduler);
                        } else if (world.getOccupancyCell(p) instanceof Tree) {
                            Twistedoak twistedoak = new Twistedoak(Twistedoak.getTwistedOakKey(), p, imageStore.getImageList("toak"), Twistedoak.getTwistedoakactionanimationperiod(), Twistedoak.getTwistedoakactionanimationperiod(), Twistedoak.getTwistedoakHealth());
                            twistedoak.scheduleActions(scheduler, world, imageStore);
                            world.removeEntity(world.getOccupancyCell(p), scheduler);
                            world.addEntity(twistedoak);
                        }
                    }

                    // spawn worms conditionally
                    List<Point> wormSpawnLocations = pressed.getOrdinalTiles(world);

                    // Point p_test = new Point(pressed.x + 1, pressed.y);

                    Random random = new Random();
                    int idx1 = random.nextInt(wormSpawnLocations.size() - 1);
                    int idx2 = random.nextInt(wormSpawnLocations.size() - 1);

                    Point p1 = wormSpawnLocations.get(idx1);
                    wormSpawnLocations.remove(idx1);

                    Point p2 = wormSpawnLocations.get(idx2);
                    wormSpawnLocations.remove(idx2);
                    // Point p3 = wormSpawnLocations.get(Background.getIntFromRange(7, 0));

                    Worm worm1 = new Worm(Worm.getWormKey(), p1, imageStore.getImageList("worm"), Worm.getWormActionPeriod(), Worm.getWormAnimationPeriod());
                    Worm worm2 = new Worm(Worm.getWormKey(), p2, imageStore.getImageList("worm"), Worm.getWormActionPeriod(), Worm.getWormAnimationPeriod());
                    // Worm worm3 = new Worm(Worm.getWormKey(), p3, imageStore.getImageList("worm"), Worm.getWormActionPeriod(), Worm.getWormAnimationPeriod());

                    // add worms only if entity (including obstacle hopefully) isn't already occupying point

                    if (world.getOccupancyCell(p1) == null) {
                        world.addEntity(worm1);
                        worm1.scheduleActions(scheduler, world, imageStore);
                    }

                    if (world.getOccupancyCell(p2) == null) {
                        world.addEntity(worm2);
                        worm2.scheduleActions(scheduler, world, imageStore);
                    }
                    otherClick = true;
                    break;
                }

                // if (world.getOccupancyCell(p3) == null) {
                //     world.addEntity(worm3);
                // }


            case "sprite":
            case "groot":
                if(otherClick) {

                    HomeTree homeTree = new HomeTree(HomeTree.getHomeTreeKey(), pressed, imageStore.getImageList(HomeTree.getHomeTreeKey()));

                    if (world.isOccupied(pressed)) {
                        world.removeEntity(world.getOccupancyCell(pressed), scheduler);
                    }

                    world.addEntity(homeTree);


                    // spawn groots conditionally
                    List<Point> grootSpawnLocations = pressed.getOrdinalTiles(world);

                    // Point p_test = new Point(pressed.x + 1, pressed.y);

                    Random rand = new Random();
                    int indx1 = rand.nextInt(grootSpawnLocations.size() - 1);

                    Point p3 = grootSpawnLocations.get(indx1);
                    grootSpawnLocations.remove(indx1);


                    Groot groot1 = new Groot(Groot.getGrootKey(), p3, imageStore.getImageList("groot"), Groot.getGrootActionPeriod(), Groot.getGrootAnimationPeriod());


                    if (world.getOccupancyCell(p3) == null) {
                        world.addEntity(groot1);
                        groot1.scheduleActions(scheduler, world, imageStore);
                    }
                    otherClick = false;
                    break;

                }
        }

    } 
}
