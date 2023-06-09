import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import processing.core.PImage;

public class DudeNotFull extends Dude {

    public DudeNotFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }
    
    @Override
    public void executeActivityAction(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = world.findNearest(this, getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (target.isEmpty() || !(this.moveToNotFull(world, (Wiggler) target.get(), scheduler)) || !(this.transform(world, scheduler, imageStore))) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), getActionPeriod());
        }
    }

    private boolean moveToNotFull(WorldModel world, Wiggler target, EventScheduler scheduler) {
        // TODO: target can be either tree or sapling deal with both cases not just the Tree case
        if (Point.adjacent(this.getPosition(), target.getPosition()) && target instanceof Tree) {
            int x = getResourceCount();
            setResourceCount(x + 1);
            int y = ((Tree) target).getHealth();
            ((Tree) target).setHealth(y - 1);
            return true;
        } else if (Point.adjacent(this.getPosition(), target.getPosition()) && target instanceof Sapling) {
            int x = getResourceCount();
            setResourceCount(x + 1);
            int y = ((Sapling) target).getHealth();
            ((Sapling) target).setHealth(y - 4);
            return true;
        } else {
            Point nextPos = this.nextPositionDude(world, target.getPosition());
    
            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getResourceCount() >= this.getResourceLimit()) {
            DudeFull dude = new DudeFull(this.getId(), this.getPosition(), this.getImages(), ((Dude) this).getResourceLimit(), ((Dude) this).getResourceCount(), this.getActionPeriod(), this.getAnimationPeriod());

            world.removeEntity(this, scheduler);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(dude);
            dude.scheduleActions(scheduler, world, imageStore);
            return true;
        }
        return false;
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
        }

    // private Entity createDudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
    //     return new Entity(EntityKind.DUDE_FULL, id, position, images, resourceLimit, 0, actionPeriod, animationPeriod, 0, 0);
    // }
    
}
