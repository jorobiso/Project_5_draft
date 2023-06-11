import java.util.List;

import org.junit.internal.runners.statements.FailOnTimeout;

import java.util.Objects;

import processing.core.PImage;

/**
 * An entity that exists in the world.
 */
public abstract class Entity {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private double actionPeriod;
    private double animationPeriod;

    // public Entity(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
    //     this.id = id;
    //     this.position = position;
    //     this.images = images;
    //     this.imageIndex = 0;
    //     this.actionPeriod = actionPeriod;
    //     this.animationPeriod = animationPeriod;
    // }

    public Entity(String id, Point position, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
    }

    static final int PROPERTY_KEY = 0;
    static final int PROPERTY_ID = 1;
    static final int PROPERTY_COL = 2;
    static final int PROPERTY_ROW = 3;
    static final int ENTITY_NUM_PROPERTIES = 4;

    // --------------------------------------------------------------------------------------------------------------------------------

    // Getters and Setters for above fields: #########################################################################################

    public String getId() {return id;}

    public static String getDUDE_KEY() {return Dude.DUDE_KEY;}

    public static int getDUDE_ACTION_PERIOD() {return Dude.DUDE_ACTION_PERIOD;}

    public static int getDUDE_ANIMATION_PERIOD() {return Dude.DUDE_ANIMATION_PERIOD;}

    public static int getDUDE_LIMIT() {return Dude.DUDE_LIMIT;}

    public static int getDUDE_NUM_PROPERTIES() {return Dude.DUDE_NUM_PROPERTIES;}

    public static String getHOUSE_KEY() {return House.HOUSE_KEY;}

    public static int getHOUSE_NUM_PROPERTIES() {return House.HOUSE_NUM_PROPERTIES;}

    public static String getOBSTACLE_KEY() {return Obstacle.OBSTACLE_KEY;}

    public static int getOBSTACLE_ANIMATION_PERIOD() {return Obstacle.OBSTACLE_ANIMATION_PERIOD;}

    public static int getOBSTACLE_NUM_PROPERTIES() {return Obstacle.OBSTACLE_NUM_PROPERTIES;}

    public static String getSTUMP_KEY() {return Stump.STUMP_KEY;}

    public static int getSTUMP_NUM_PROPERTIES() {return Stump.STUMP_NUM_PROPERTIES;}

    public static String getFAIRY_KEY() {return Fairy.FAIRY_KEY;}

    public static int getFAIRY_ANIMATION_PERIOD() {return Fairy.FAIRY_ANIMATION_PERIOD;}

    public static int getFAIRY_ACTION_PERIOD() {return Fairy.FAIRY_ACTION_PERIOD;}

    public static int getFAIRY_NUM_PROPERTIES() {return Fairy.FAIRY_NUM_PROPERTIES;}

    public static double getSAPLING_ACTION_ANIMATION_PERIOD() {return Sapling.SAPLING_ACTION_ANIMATION_PERIOD;}

    public static int getSAPLING_HEALTH_LIMIT() {return Sapling.SAPLING_HEALTH_LIMIT;}

    public static String getSAPLING_KEY() {return Sapling.SAPLING_KEY;}

    public static int getSAPLING_HEALTH() {return Sapling.SAPLING_HEALTH;}

    public static int getSAPLING_NUM_PROPERTIES() {return Sapling.SAPLING_NUM_PROPERTIES;}

    public static String getTREE_KEY() {return Tree.TREE_KEY;}

    public static int getTREE_ANIMATION_PERIOD() {return Tree.TREE_ANIMATION_PERIOD;}

    public static int getTREE_ACTION_PERIOD() {return Tree.TREE_ACTION_PERIOD;}

    public static int getTREE_HEALTH() {return Tree.TREE_HEALTH;}

    public static int getTREE_NUM_PROPERTIES() {return Tree.TREE_NUM_PROPERTIES;}

// ###############################################################################################################

    public Point getPosition() {return position;}

    public void setPosition(Point position) {
        this.position = position;
    }

    public List<PImage> getImages() {return images;}

    public int getImageIndex() {return imageIndex;}

    public double getActionPeriod() {return actionPeriod;}

    public double getAnimationPeriod() {return animationPeriod;}

// ###############################################################################################################

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    public String log(){
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.x, this.position.y, this.imageIndex);
    }

    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    }

    // PARSES: ---------------------------------------------------------------------------------------------------------------------------------------------------
    // private static void parseStump(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
    //     if (properties.length == Entity.STUMP_NUM_PROPERTIES) {
    //         Entity entity = Entity.createStump(id, pt, ImageStore.getImageList(imageStore, Entity.STUMP_KEY));
    //         Entity.tryAddEntity(world, entity);
    //     }else{
    //         throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Entity.STUMP_KEY, Entity.STUMP_NUM_PROPERTIES));
    //     }
    // }

    // private static void parseHouse(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
    //     if (properties.length == Entity.HOUSE_NUM_PROPERTIES) {
    //         Entity entity = Entity.createHouse(id, pt, ImageStore.getImageList(imageStore, Entity.HOUSE_KEY));
    //         Entity.tryAddEntity(world, entity);
    //     }else{
    //         throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Entity.HOUSE_KEY, Entity.HOUSE_NUM_PROPERTIES));
    //     }
    // }

    // private static void parseObstacle(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
    //     if (properties.length == Entity.OBSTACLE_NUM_PROPERTIES) {
    //         Entity entity = Entity.createObstacle(id, pt, Double.parseDouble(properties[Entity.OBSTACLE_ANIMATION_PERIOD]), ImageStore.getImageList(imageStore, Entity.OBSTACLE_KEY));
    //         Entity.tryAddEntity(world, entity);
    //     }else{
    //         throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Entity.OBSTACLE_KEY, Entity.OBSTACLE_NUM_PROPERTIES));
    //     }
    // }

    // private static void parseTree(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
    //     if (properties.length == Entity.TREE_NUM_PROPERTIES) {
    //         Entity entity = Entity.createTree(id, pt, Double.parseDouble(properties[Entity.TREE_ACTION_PERIOD]), Double.parseDouble(properties[Entity.TREE_ANIMATION_PERIOD]), Integer.parseInt(properties[Entity.TREE_HEALTH]), ImageStore.getImageList(imageStore, Entity.TREE_KEY));
    //         Entity.tryAddEntity(world, entity);
    //     }else{
    //         throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Entity.TREE_KEY, Entity.TREE_NUM_PROPERTIES));
    //     }
    // }

    // private static void parseFairy(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
    //     if (properties.length == Entity.FAIRY_NUM_PROPERTIES) {
    //         Entity entity = Entity.createFairy(id, pt, Double.parseDouble(properties[Entity.FAIRY_ACTION_PERIOD]), Double.parseDouble(properties[Entity.FAIRY_ANIMATION_PERIOD]), ImageStore.getImageList(imageStore, Entity.FAIRY_KEY));
    //         Entity.tryAddEntity(world, entity);
    //     }else{
    //         throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Entity.FAIRY_KEY, Entity.FAIRY_NUM_PROPERTIES));
    //     }
    // }

    // private static void parseDude(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
    //     if (properties.length == Entity.DUDE_NUM_PROPERTIES) {
    //         Entity entity = Entity.createDudeNotFull(id, pt, Double.parseDouble(properties[Entity.DUDE_ACTION_PERIOD]), Double.parseDouble(properties[Entity.DUDE_ANIMATION_PERIOD]), Integer.parseInt(properties[Entity.DUDE_LIMIT]), ImageStore.getImageList(imageStore, Entity.DUDE_KEY));
    //         Entity.tryAddEntity(world, entity);
    //     }else{
    //         throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Entity.DUDE_KEY, Entity.DUDE_NUM_PROPERTIES));
    //     }
    // }

    // private static void parseSapling(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
    //     if (properties.length == Entity.SAPLING_NUM_PROPERTIES) {
    //         int health = Integer.parseInt(properties[Entity.SAPLING_HEALTH]);
    //         Entity entity = Entity.createSapling(id, pt, ImageStore.getImageList(imageStore, SAPLING_KEY), health);
    //         Entity.tryAddEntity(world, entity);
    //     }else{
    //         throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", SAPLING_KEY, Entity.SAPLING_NUM_PROPERTIES));
    //     }
    // }
    // END OF PARSES ---------------------------------------------------------------------------------------------------------------------------------------------------

    // private static Point nextPositionDude(Entity entity, WorldModel world, Point destPos) {
    //     int horiz = Integer.signum(destPos.x - entity.position.x);
    //     Point newPos = new Point(entity.position.x + horiz, entity.position.y);
    
    //     if (horiz == 0 || WorldModel.isOccupied(world, newPos) && WorldModel.getOccupancyCell(world, newPos).kind != EntityKind.STUMP) {
    //         int vert = Integer.signum(destPos.y - entity.position.y);
    //         newPos = new Point(entity.position.x, entity.position.y + vert);
    
    //         if (vert == 0 || WorldModel.isOccupied(world, newPos) && WorldModel.getOccupancyCell(world, newPos).kind != EntityKind.STUMP) {
    //             newPos = entity.position;
    //         }
    //     }
    
    //     return newPos;
    // }

    // private Point nextPositionFairy(Entity entity, WorldModel world, Point destPos) {
    //     int horiz = Integer.signum(destPos.x - entity.position.x);
    //     Point newPos = new Point(entity.position.x + horiz, entity.position.y);
    
    //     if (horiz == 0 || WorldModel.isOccupied(world, newPos)) {
    //         int vert = Integer.signum(destPos.y - entity.position.y);
    //         newPos = new Point(entity.position.x, entity.position.y + vert);
    
    //         if (vert == 0 || WorldModel.isOccupied(world, newPos)) {
    //             newPos = entity.position;
    //         }
    //     }
    
    //     return newPos;
    // }

    // private boolean moveToFull(Entity dude, WorldModel world, Entity target, EventScheduler scheduler) {
    //     if (Point.adjacent(dude.position, target.position)) {
    //         return true;
    //     } else {
    //         Point nextPos = nextPositionDude(dude, world, target.position);
    
    //         if (!dude.position.equals(nextPos)) {
    //             WorldModel.moveEntity(world, scheduler, dude, nextPos);
    //         }
    //         return false;
    //     }
    // }

    // private boolean moveToNotFull(Entity dude, WorldModel world, Entity target, EventScheduler scheduler) {
    //     if (Point.adjacent(dude.position, target.position)) {
    //         dude.resourceCount += 1;
    //         target.health--;
    //         return true;
    //     } else {
    //         Point nextPos = nextPositionDude(dude, world, target.position);
    
    //         if (!dude.position.equals(nextPos)) {
    //             WorldModel.moveEntity(world, scheduler, dude, nextPos);
    //         }
    //         return false;
    //     }
    // }

    // private boolean moveToFairy(Entity fairy, WorldModel world, Entity target, EventScheduler scheduler) {
    //     if (Point.adjacent(fairy.position, target.position)) {
    //         Entity.removeEntity(world, scheduler, target);
    //         return true;
    //     } else {
    //         Point nextPos = fairy.nextPositionFairy(fairy, world, target.position);
    
    //         if (!fairy.position.equals(nextPos)) {
    //             WorldModel.moveEntity(world, scheduler, fairy, nextPos);
    //         }
    //         return false;
    //     }
    // }

    // private boolean transformNotFull(Entity entity, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
    //     if (entity.resourceCount >= entity.resourceLimit) {
    //         Entity dude = entity.createDudeFull(entity.id, entity.position, entity.actionPeriod, entity.animationPeriod, entity.resourceLimit, entity.images);

    //         Entity.removeEntity(world, scheduler, entity);
    //         EventScheduler.unscheduleAllEvents(scheduler, entity);

    //         entity.addEntity(world, dude);
    //         Entity.scheduleActions(dude, scheduler, world, imageStore);

    //         return true;
    //     }

    //     return false;
    // }

    // private void transformFull(Entity entity, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
    //     Entity dude = Entity.createDudeNotFull(entity.id, entity.position, entity.actionPeriod, entity.animationPeriod, entity.resourceLimit, entity.images);
    
    //     Entity.removeEntity(world, scheduler, entity);
    
    //     entity.addEntity(world, dude);
    //     scheduleActions(dude, scheduler, world, imageStore);
    // }

    // private boolean transformSapling(Entity entity, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
    //     if (entity.health <= 0) {
    //         Entity stump = Entity.createStump(Entity.STUMP_KEY + "_" + entity.id, entity.position, ImageStore.getImageList(imageStore, Entity.STUMP_KEY));
    
    //         Entity.removeEntity(world, scheduler, entity);
    
    //         entity.addEntity(world, stump);
    
    //         return true;
    //     } else if (entity.health >= entity.healthLimit) {
    //         Entity tree = Entity.createTree(Entity.TREE_KEY + "_" + entity.id, entity.position, Background.getNumFromRange(entity.TREE_ACTION_MAX, entity.TREE_ACTION_MIN), Background.getNumFromRange(entity.TREE_ANIMATION_MAX, entity.TREE_ANIMATION_MIN), Background.getIntFromRange(entity.TREE_HEALTH_MAX, entity.TREE_HEALTH_MIN), ImageStore.getImageList(imageStore, Entity.TREE_KEY));
    
    //         Entity.removeEntity(world, scheduler, entity);
    
    //         entity.addEntity(world, tree);
    //         scheduleActions(tree, scheduler, world, imageStore);
    
    //         return true;
    //     }
    
    //     return false;
    // }

    // private boolean transformTree(Entity entity, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
    //     if (entity.health <= 0) {
    //         Entity stump = Entity.createStump(Entity.STUMP_KEY + "_" + entity.id, entity.position, ImageStore.getImageList(imageStore, Entity.STUMP_KEY));
    
    //         Entity.removeEntity(world, scheduler, entity);
    
    //         entity.addEntity(world, stump);
    
    //         return true;
    //     }
    
    //     return false;
    // }

    // private boolean transformPlant(Entity entity, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
    //     if (entity.kind == EntityKind.TREE) {
    //         return entity.transformTree(entity, world, scheduler, imageStore);
    //     } else if (entity.kind == EntityKind.SAPLING) {
    //         return entity.transformSapling(entity, world, scheduler, imageStore);
    //     } else {
    //         throw new UnsupportedOperationException(String.format("transformPlant not supported for %s", entity));
    //     }
    // }

    // EXECUTES:
    // public static void executeSaplingActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
    //     entity.health++;
    //     if (!entity.transformPlant(entity, world, scheduler, imageStore)) {
    //         EventScheduler.scheduleEvent(scheduler, entity, createActivityAction(entity, world, imageStore), entity.actionPeriod);
    //     }
    // }

    // public static void executeTreeActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

    //     if (!entity.transformPlant(entity, world, scheduler, imageStore)) {

    //         EventScheduler.scheduleEvent(scheduler, entity, createActivityAction(entity, world, imageStore), entity.actionPeriod);
    //     }
    // }

    // public static void executeFairyActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
    //     Optional<Entity> fairyTarget = entity.findNearest(world, entity.position, new ArrayList<>(List.of(EntityKind.STUMP)));

    //     if (fairyTarget.isPresent()) {
    //         Point tgtPos = fairyTarget.get().position;

    //         if (entity.moveToFairy(entity, world, fairyTarget.get(), scheduler)) {

    //             Entity sapling = Entity.createSapling(SAPLING_KEY + "_" + fairyTarget.get().id, tgtPos, ImageStore.getImageList(imageStore, SAPLING_KEY), 0);

    //             entity.addEntity(world, sapling);
    //             scheduleActions(sapling, scheduler, world, imageStore);
    //         }
    //     }

    //     EventScheduler.scheduleEvent(scheduler, entity, createActivityAction(entity, world, imageStore), entity.actionPeriod);
    // }

    // public static void executeDudeNotFullActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
    //     Optional<Entity> target = entity.findNearest(world, entity.position, new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));

    //     if (target.isEmpty() || !entity.moveToNotFull(entity, world, target.get(), scheduler) || !entity.transformNotFull(entity, world, scheduler, imageStore)) {
    //         EventScheduler.scheduleEvent(scheduler, entity, createActivityAction(entity, world, imageStore), entity.actionPeriod);
    //     }
    // }


    // public static void executeDudeFullActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
    //     Optional<Entity> fullTarget = entity.findNearest(world, entity.position, new ArrayList<>(List.of(EntityKind.HOUSE)));

    //     if (fullTarget.isPresent() && entity.moveToFull(entity, world, fullTarget.get(), scheduler)) {
    //         entity.transformFull(entity, world, scheduler, imageStore);
    //     } else {
    //         EventScheduler.scheduleEvent(scheduler, entity, createActivityAction(entity, world, imageStore), entity.actionPeriod);
    //     }
    // }


    public Animation createAnimationAction(int repeatCount) {
        return new Animation(this, repeatCount);
    }

    public Activity createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }


    //go to wiggler
    // public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);

    // public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
    // }



}
