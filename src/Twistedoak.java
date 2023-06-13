import java.util.List;

import processing.core.PImage;

public class Twistedoak extends Tree {
    protected static final String TWISTED_OAK_KEY = "toak";
    public static final double twistedOakActionAnimationPeriod = 3;
    public static final int twistedOakHealth = 3;

    public Twistedoak(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health) {
        super(id, position, images, actionPeriod, animationPeriod, health);
    }


    public static String getTwistedOakKey() {
        return TWISTED_OAK_KEY;
    }

    public static double getTwistedoakactionanimationperiod() {
        return twistedOakActionAnimationPeriod;
    }

    public static int getTwistedoakHealth() {
        return twistedOakHealth;
    }


    @Override
    public void executeActivityAction(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        // System.out.println("\n" + "Position: " + this.getPosition() + " Health: " + this.getHealth() + "...");

        // spread corruption if tree is new
        if (this.getHealth() == getTwistedoakHealth()) {

            List<Point> corruptedArea = this.getPosition().getSpreadTiles(world);

            for (Point p : corruptedArea) {
                System.out.println(p);
                world.setBackgroundCell(p, new Background("cgrass", imageStore.getImageList("cgrass")));
                if (world.getOccupancyCell(p) instanceof Sapling || world.getOccupancyCell(p) instanceof Stump) {
                    world.removeEntity(world.getOccupancyCell(p), scheduler);
                } else if (world.getOccupancyCell(p) instanceof Tree && !(world.getOccupancyCell(p) instanceof Twistedoak)) {
                    Twistedoak twistedoak = new Twistedoak(Twistedoak.getTwistedOakKey(), p, imageStore.getImageList("toak"), getTwistedoakactionanimationperiod(), getTwistedoakactionanimationperiod(), getTwistedoakHealth());
                    world.removeEntity(world.getOccupancyCell(p), scheduler);
                    world.addEntity(twistedoak);
                    twistedoak.scheduleActions(scheduler, world, imageStore);
                }
            }
        }

        // update Twistedoak health
        if (this.getHealth() > 0) {
            this.setHealth(this.getHealth() - 1);
        }

        // continue decay if Twistedoak isn't destroyed otherwise destroy Twistedoak
        if (this.getHealth() == 0) {
            world.removeEntity(this, scheduler);
        }

        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), getTwistedoakactionanimationperiod());

    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), getTwistedoakactionanimationperiod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), getTwistedoakactionanimationperiod());
    }


}
