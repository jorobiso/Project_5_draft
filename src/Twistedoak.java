import java.util.List;

import processing.core.PImage;

public class Twistedoak extends Tree {
    protected static final String TWISTED_OAK_KEY = "toak";
    public static final double twistedOakActionAnimationPeriod = 5;

    public Twistedoak(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health) {
        super(id, position, images, actionPeriod, animationPeriod, health);
    }


    public static String getTwistedOakKey() {
        return TWISTED_OAK_KEY;
    }

    public static double getTwistedoakactionanimationperiod() {
        return twistedOakActionAnimationPeriod;
    }


    @Override
    public void executeActivityAction(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        // update Twistedoak health
        this.setHealth(this.getHealth() - 1);

        // spread corruption
        List<Point> corruptedArea = this.getPosition().getOrdinalTiles(world);
        for (Point p : corruptedArea) {
            world.setBackgroundCell(p, new Background("cgrass", imageStore.getImageList("cgrass")));
            if (world.getOccupancyCell(p) instanceof Sapling || world.getOccupancyCell(p) instanceof Stump) {
                world.removeEntity(world.getOccupancyCell(p), scheduler);
            }
            else if (world.getOccupancyCell(p) instanceof Tree) {
                Twistedoak twistedoak = new Twistedoak(Twistedoak.getTwistedOakKey(), p, imageStore.getImageList("toak"), getTwistedoakactionanimationperiod(), getTwistedoakactionanimationperiod(), 3);
                world.removeEntity(world.getOccupancyCell(p), scheduler);
                world.addEntity(twistedoak);
            }
        }

        // continue decay if tree isn't destroyed otherwise destory tree
        if (this.getHealth() > 0) {
            this.scheduleActions(scheduler, world, imageStore);
        } else {
            world.removeEntity(this, scheduler);
        }

    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }


}
