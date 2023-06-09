// import java.util.List;

// //New imports: -------------
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.LinkedList;
// import java.util.Optional;
// //--------------------------

// import java.util.Objects;
 
// import processing.core.PImage;

// /**
//  * An entity that exists in the world. See EntityKind for the
//  * different kinds of entities that exist.
//  */
// public class EntityOld {
//     private EntityKind kind;
//     private String id;
//     private Point position;
//     private List<PImage> images;
//     private int imageIndex;
//     private int resourceLimit;
//     private int resourceCount;
//     private double actionPeriod;
//     private double animationPeriod;
//     private int health;
//     private int healthLimit;

// // Getters and Setters for above fields:
//     public EntityKind getKind() {
//         return kind;
//     }

//     public void setKind(EntityKind kind) {
//         this.kind = kind;
//     }

//     public String getId() {
//         return id;
//     }

//     public void setId(String id) {
//         this.id = id;
//     }

//     public Point getPosition() {
//         return position;
//     }

//     public void setPosition(Point position) {
//         this.position = position;
//     }

//     public List<PImage> getImages() {
//         return images;
//     }

//     public void setImages(List<PImage> images) {
//         this.images = images;
//     }

//     public int getImageIndex() {
//         return imageIndex;
//     }

//     public void setImageIndex(int imageIndex) {
//         this.imageIndex = imageIndex;
//     }

//     public int getResourceLimit() {
//         return resourceLimit;
//     }

//     public void setResourceLimit(int resourceLimit) {
//         this.resourceLimit = resourceLimit;
//     }

//     public int getResourceCount() {
//         return resourceCount;
//     }

//     public void setResourceCount(int resourceCount) {
//         this.resourceCount = resourceCount;
//     }

//     public double getActionPeriod() {
//         return actionPeriod;
//     }

//     public void setActionPeriod(double actionPeriod) {
//         this.actionPeriod = actionPeriod;
//     }

//     public void setAnimationPeriod(double animationPeriod) {
//         this.animationPeriod = animationPeriod;
//     }

//     public int getHealth() {
//         return health;
//     }

//     public void setHealth(int health) {
//         this.health = health;
//     }

//     public int getHealthLimit() {
//         return healthLimit;
//     }

//     public void setHealthLimit(int healthLimit) {
//         this.healthLimit = healthLimit;
//     }

//     // New Instance Variables:------------------------------
//     private static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
//     private static final int SAPLING_HEALTH_LIMIT = 5;
//     private static final int PROPERTY_KEY = 0;
//     private static final int PROPERTY_ID = 1;
//     private static final int PROPERTY_COL = 2;
//     private static final int PROPERTY_ROW = 3;
//     private static final int ENTITY_NUM_PROPERTIES = 4;
//     private static final String STUMP_KEY = "stump";
//     private static final int STUMP_NUM_PROPERTIES = 0;
//     private static final String SAPLING_KEY = "sapling";
//     private static final int SAPLING_HEALTH = 0;
//     private static final int SAPLING_NUM_PROPERTIES = 1;
//     private static final String OBSTACLE_KEY = "obstacle";
//     private static final int OBSTACLE_ANIMATION_PERIOD = 0;
//     private static final int OBSTACLE_NUM_PROPERTIES = 1;
//     private static final String DUDE_KEY = "dude";
//     private static final int DUDE_ACTION_PERIOD = 0;
//     private static final int DUDE_ANIMATION_PERIOD = 1;
//     private static final int DUDE_LIMIT = 2;
//     private static final int DUDE_NUM_PROPERTIES = 3;
//     private static final String HOUSE_KEY = "house";
//     private static final int HOUSE_NUM_PROPERTIES = 0;
//     private static final String FAIRY_KEY = "fairy";
//     private static final int FAIRY_ANIMATION_PERIOD = 0;
//     private static final int FAIRY_ACTION_PERIOD = 1;
//     private static final int FAIRY_NUM_PROPERTIES = 2;
//     private static final String TREE_KEY = "tree";
//     private static final int TREE_ANIMATION_PERIOD = 0;
//     private static final int TREE_ACTION_PERIOD = 1;
//     private static final int TREE_HEALTH = 2;
//     private static final int TREE_NUM_PROPERTIES = 3;
//     private final double TREE_ANIMATION_MAX = 0.600;
//     private final double TREE_ANIMATION_MIN = 0.050;
//     private final double TREE_ACTION_MAX = 1.400;
//     private final double TREE_ACTION_MIN = 1.000;
//     private final int TREE_HEALTH_MAX = 3;
//     private final int TREE_HEALTH_MIN = 1;
// //------------------------------------------------------

//     public Entity(EntityKind kind, String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
//         this.kind = kind;
//         this.id = id;
//         this.position = position;
//         this.images = images;
//         this.imageIndex = 0;
//         this.resourceLimit = resourceLimit;
//         this.resourceCount = resourceCount;
//         this.actionPeriod = actionPeriod;
//         this.animationPeriod = animationPeriod;
//         this.health = health;
//         this.healthLimit = healthLimit;
//     }

//     /**
//      * Helper method for testing. Preserve this functionality while refactoring.
//      */
//     public String log(){
//         return this.id.isEmpty() ? null :
//                 String.format("%s %d %d %d", this.id, this.position.x, this.position.y, this.imageIndex);
//     }

//     public double getAnimationPeriod() {
//         switch (this.kind) {
//             case DUDE_FULL:
//             case DUDE_NOT_FULL:
//             case OBSTACLE:
//             case FAIRY:
//             case SAPLING:
//             case TREE:
//                 return this.animationPeriod;
//             default:
//                 throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s", this.kind));
//         }
//     }

//     public void nextImage() {
//         this.imageIndex = this.imageIndex + 1;
//     }

//     public static void parseEntity(WorldModel world, String line, ImageStore imageStore) {
//         String[] properties = line.split(" ", Entity.ENTITY_NUM_PROPERTIES + 1);
//         if (properties.length >= Entity.ENTITY_NUM_PROPERTIES) {
//             String key = properties[Entity.PROPERTY_KEY];
//             String id = properties[Entity.PROPERTY_ID];
//             Point pt = new Point(Integer.parseInt(properties[Entity.PROPERTY_COL]), Integer.parseInt(properties[Entity.PROPERTY_ROW]));
    
//             properties = properties.length == Entity.ENTITY_NUM_PROPERTIES ?
//                     new String[0] : properties[Entity.ENTITY_NUM_PROPERTIES].split(" ");
    
//             switch (key) {
//                 case Entity.OBSTACLE_KEY -> parseObstacle(world, properties, pt, id, imageStore);
//                 case Entity.DUDE_KEY -> parseDude(world, properties, pt, id, imageStore);
//                 case Entity.FAIRY_KEY -> parseFairy(world, properties, pt, id, imageStore);
//                 case Entity.HOUSE_KEY -> parseHouse(world, properties, pt, id, imageStore);
//                 case Entity.TREE_KEY -> parseTree(world, properties, pt, id, imageStore);
//                 case SAPLING_KEY -> parseSapling(world, properties, pt, id, imageStore);
//                 case Entity.STUMP_KEY -> parseStump(world, properties, pt, id, imageStore);
//                 default -> throw new IllegalArgumentException("Entity key is unknown");
//             }
//         }else{
//             throw new IllegalArgumentException("Entity must be formatted as [key] [id] [x] [y] ...");
//         }
//     }

//     // creates (changed to private): ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//     // don't technically need resource count ... full
//     private Entity createDudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
//         return new Entity(EntityKind.DUDE_FULL, id, position, images, resourceLimit, 0, actionPeriod, animationPeriod, 0, 0);
//     }

//     // need resource count, though it always starts at 0
//     private static Entity createDudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
//         return new Entity(EntityKind.DUDE_NOT_FULL, id, position, images, resourceLimit, 0, actionPeriod, animationPeriod, 0, 0);
//     }

//     private static Entity createFairy(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
//         return new Entity(EntityKind.FAIRY, id, position, images, 0, 0, actionPeriod, animationPeriod, 0, 0);
//     }

//     // health starts at 0 and builds up until ready to convert to Tree
//     private static Entity createSapling(String id, Point position, List<PImage> images, int health) {
//         return new Entity(EntityKind.SAPLING, id, position, images, 0, 0, Entity.SAPLING_ACTION_ANIMATION_PERIOD, Entity.SAPLING_ACTION_ANIMATION_PERIOD, 0, Entity.SAPLING_HEALTH_LIMIT);
//     }

//     private static Entity createStump(String id, Point position, List<PImage> images) {
//         return new Entity(EntityKind.STUMP, id, position, images, 0, 0, 0, 0, 0, 0);
//     }

//     private static Entity createTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
//         return new Entity(EntityKind.TREE, id, position, images, 0, 0, actionPeriod, animationPeriod, health, 0);
//     }

//     private static Entity createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
//         return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0, animationPeriod, 0, 0);
//     }

//     private static Entity createHouse(String id, Point position, List<PImage> images) {
//         return new Entity(EntityKind.HOUSE, id, position, images, 0, 0, 0, 0, 0, 0);
//     }
// //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//     public static Optional<Entity> getOccupant(WorldModel world, Point pos) {
//         if (WorldModel.isOccupied(world, pos)) {
//             return Optional.of(WorldModel.getOccupancyCell(world, pos));
//         } else {
//             return Optional.empty();
//         }
//     }

//     void removeEntityAt(WorldModel world, Point pos) {
//         if (WorldModel.withinBounds(world, pos) && WorldModel.getOccupancyCell(world, pos) != null) {
//             Entity entity = WorldModel.getOccupancyCell(world, pos);
    
//             /* This moves the entity just outside of the grid for
//              * debugging purposes. */
//             entity.position = new Point(-1, -1);
//             world.entities.remove(entity);
//             WorldModel.setOccupancyCell(world, pos, null);
//         }
//     }

//     public static void removeEntity(WorldModel world, EventScheduler scheduler, Entity entity) {
//         EventScheduler.unscheduleAllEvents(scheduler, entity);
//         entity.removeEntityAt(world, entity.position);
//     }

//     /*
//        Assumes that there is no entity currently occupying the
//        intended destination cell.
//     */
//     void addEntity(WorldModel world, Entity entity) {
//         if (WorldModel.withinBounds(world, entity.position)) {
//             WorldModel.setOccupancyCell(world, entity.position, entity);
//             world.entities.add(entity);
//         }
//     }

//     private Optional<Entity> findNearest(WorldModel world, Point pos, List<EntityKind> kinds) {
//         List<Entity> ofType = new LinkedList<>();
//         for (EntityKind kind : kinds) {
//             for (Entity entity : world.entities) {
//                 if (entity.kind == kind) {
//                     ofType.add(entity);
//                 }
//             }
//         }
    
//         return nearestEntity(ofType, pos);
//     }

//     private Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
//         if (entities.isEmpty()) {
//             return Optional.empty();
//         } else {
//             Entity nearest = entities.get(0);
//             int nearestDistance = Point.distanceSquared(nearest.position, pos);
    
//             for (Entity other : entities) {
//                 int otherDistance = Point.distanceSquared(other.position, pos);
    
//                 if (otherDistance < nearestDistance) {
//                     nearest = other;
//                     nearestDistance = otherDistance;
//                 }
//             }
    
//             return Optional.of(nearest);
//         }
//     }

//     private static void tryAddEntity(WorldModel world, Entity entity) {
//         if (WorldModel.isOccupied(world, entity.position)) {
//             // arguably the wrong type of exception, but we are not
//             // defining our own exceptions yet
//             throw new IllegalArgumentException("position occupied");
//         }
    
//         entity.addEntity(world, entity);
//     }

//     // PARSES: ---------------------------------------------------------------------------------------------------------------------------------------------------
//     private static void parseStump(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
//         if (properties.length == Entity.STUMP_NUM_PROPERTIES) {
//             Entity entity = Entity.createStump(id, pt, ImageStore.getImageList(imageStore, Entity.STUMP_KEY));
//             Entity.tryAddEntity(world, entity);
//         }else{
//             throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Entity.STUMP_KEY, Entity.STUMP_NUM_PROPERTIES));
//         }
//     }

//     private static void parseHouse(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
//         if (properties.length == Entity.HOUSE_NUM_PROPERTIES) {
//             Entity entity = Entity.createHouse(id, pt, ImageStore.getImageList(imageStore, Entity.HOUSE_KEY));
//             Entity.tryAddEntity(world, entity);
//         }else{
//             throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Entity.HOUSE_KEY, Entity.HOUSE_NUM_PROPERTIES));
//         }
//     }

//     private static void parseObstacle(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
//         if (properties.length == Entity.OBSTACLE_NUM_PROPERTIES) {
//             Entity entity = Entity.createObstacle(id, pt, Double.parseDouble(properties[Entity.OBSTACLE_ANIMATION_PERIOD]), ImageStore.getImageList(imageStore, Entity.OBSTACLE_KEY));
//             Entity.tryAddEntity(world, entity);
//         }else{
//             throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Entity.OBSTACLE_KEY, Entity.OBSTACLE_NUM_PROPERTIES));
//         }
//     }

//     private static void parseTree(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
//         if (properties.length == Entity.TREE_NUM_PROPERTIES) {
//             Entity entity = Entity.createTree(id, pt, Double.parseDouble(properties[Entity.TREE_ACTION_PERIOD]), Double.parseDouble(properties[Entity.TREE_ANIMATION_PERIOD]), Integer.parseInt(properties[Entity.TREE_HEALTH]), ImageStore.getImageList(imageStore, Entity.TREE_KEY));
//             Entity.tryAddEntity(world, entity);
//         }else{
//             throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Entity.TREE_KEY, Entity.TREE_NUM_PROPERTIES));
//         }
//     }

//     private static void parseFairy(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
//         if (properties.length == Entity.FAIRY_NUM_PROPERTIES) {
//             Entity entity = Entity.createFairy(id, pt, Double.parseDouble(properties[Entity.FAIRY_ACTION_PERIOD]), Double.parseDouble(properties[Entity.FAIRY_ANIMATION_PERIOD]), ImageStore.getImageList(imageStore, Entity.FAIRY_KEY));
//             Entity.tryAddEntity(world, entity);
//         }else{
//             throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Entity.FAIRY_KEY, Entity.FAIRY_NUM_PROPERTIES));
//         }
//     }

//     private static void parseDude(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
//         if (properties.length == Entity.DUDE_NUM_PROPERTIES) {
//             Entity entity = Entity.createDudeNotFull(id, pt, Double.parseDouble(properties[Entity.DUDE_ACTION_PERIOD]), Double.parseDouble(properties[Entity.DUDE_ANIMATION_PERIOD]), Integer.parseInt(properties[Entity.DUDE_LIMIT]), ImageStore.getImageList(imageStore, Entity.DUDE_KEY));
//             Entity.tryAddEntity(world, entity);
//         }else{
//             throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Entity.DUDE_KEY, Entity.DUDE_NUM_PROPERTIES));
//         }
//     }

//     private static void parseSapling(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
//         if (properties.length == Entity.SAPLING_NUM_PROPERTIES) {
//             int health = Integer.parseInt(properties[Entity.SAPLING_HEALTH]);
//             Entity entity = Entity.createSapling(id, pt, ImageStore.getImageList(imageStore, SAPLING_KEY), health);
//             Entity.tryAddEntity(world, entity);
//         }else{
//             throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", SAPLING_KEY, Entity.SAPLING_NUM_PROPERTIES));
//         }
//     }
//     // END OF PARSES ---------------------------------------------------------------------------------------------------------------------------------------------------

//     private static Point nextPositionDude(Entity entity, WorldModel world, Point destPos) {
//         int horiz = Integer.signum(destPos.x - entity.position.x);
//         Point newPos = new Point(entity.position.x + horiz, entity.position.y);
    
//         if (horiz == 0 || WorldModel.isOccupied(world, newPos) && WorldModel.getOccupancyCell(world, newPos).kind != EntityKind.STUMP) {
//             int vert = Integer.signum(destPos.y - entity.position.y);
//             newPos = new Point(entity.position.x, entity.position.y + vert);
    
//             if (vert == 0 || WorldModel.isOccupied(world, newPos) && WorldModel.getOccupancyCell(world, newPos).kind != EntityKind.STUMP) {
//                 newPos = entity.position;
//             }
//         }
    
//         return newPos;
//     }

//     private Point nextPositionFairy(Entity entity, WorldModel world, Point destPos) {
//         int horiz = Integer.signum(destPos.x - entity.position.x);
//         Point newPos = new Point(entity.position.x + horiz, entity.position.y);
    
//         if (horiz == 0 || WorldModel.isOccupied(world, newPos)) {
//             int vert = Integer.signum(destPos.y - entity.position.y);
//             newPos = new Point(entity.position.x, entity.position.y + vert);
    
//             if (vert == 0 || WorldModel.isOccupied(world, newPos)) {
//                 newPos = entity.position;
//             }
//         }
    
//         return newPos;
//     }

//     private boolean moveToFull(Entity dude, WorldModel world, Entity target, EventScheduler scheduler) {
//         if (Point.adjacent(dude.position, target.position)) {
//             return true;
//         } else {
//             Point nextPos = nextPositionDude(dude, world, target.position);
    
//             if (!dude.position.equals(nextPos)) {
//                 WorldModel.moveEntity(world, scheduler, dude, nextPos);
//             }
//             return false;
//         }
//     }

//     private boolean moveToNotFull(Entity dude, WorldModel world, Entity target, EventScheduler scheduler) {
//         if (Point.adjacent(dude.position, target.position)) {
//             dude.resourceCount += 1;
//             target.health--;
//             return true;
//         } else {
//             Point nextPos = nextPositionDude(dude, world, target.position);
    
//             if (!dude.position.equals(nextPos)) {
//                 WorldModel.moveEntity(world, scheduler, dude, nextPos);
//             }
//             return false;
//         }
//     }

//     private boolean moveToFairy(Entity fairy, WorldModel world, Entity target, EventScheduler scheduler) {
//         if (Point.adjacent(fairy.position, target.position)) {
//             Entity.removeEntity(world, scheduler, target);
//             return true;
//         } else {
//             Point nextPos = fairy.nextPositionFairy(fairy, world, target.position);
    
//             if (!fairy.position.equals(nextPos)) {
//                 WorldModel.moveEntity(world, scheduler, fairy, nextPos);
//             }
//             return false;
//         }
//     }

//     private boolean transformNotFull(Entity entity, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//         if (entity.resourceCount >= entity.resourceLimit) {
//             Entity dude = entity.createDudeFull(entity.id, entity.position, entity.actionPeriod, entity.animationPeriod, entity.resourceLimit, entity.images);

//             Entity.removeEntity(world, scheduler, entity);
//             EventScheduler.unscheduleAllEvents(scheduler, entity);

//             entity.addEntity(world, dude);
//             Entity.scheduleActions(dude, scheduler, world, imageStore);

//             return true;
//         }

//         return false;
//     }

//     private void transformFull(Entity entity, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//         Entity dude = Entity.createDudeNotFull(entity.id, entity.position, entity.actionPeriod, entity.animationPeriod, entity.resourceLimit, entity.images);
    
//         Entity.removeEntity(world, scheduler, entity);
    
//         entity.addEntity(world, dude);
//         scheduleActions(dude, scheduler, world, imageStore);
//     }

//     private boolean transformSapling(Entity entity, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//         if (entity.health <= 0) {
//             Entity stump = Entity.createStump(Entity.STUMP_KEY + "_" + entity.id, entity.position, ImageStore.getImageList(imageStore, Entity.STUMP_KEY));
    
//             Entity.removeEntity(world, scheduler, entity);
    
//             entity.addEntity(world, stump);
    
//             return true;
//         } else if (entity.health >= entity.healthLimit) {
//             Entity tree = Entity.createTree(Entity.TREE_KEY + "_" + entity.id, entity.position, Background.getNumFromRange(entity.TREE_ACTION_MAX, entity.TREE_ACTION_MIN), Background.getNumFromRange(entity.TREE_ANIMATION_MAX, entity.TREE_ANIMATION_MIN), Background.getIntFromRange(entity.TREE_HEALTH_MAX, entity.TREE_HEALTH_MIN), ImageStore.getImageList(imageStore, Entity.TREE_KEY));
    
//             Entity.removeEntity(world, scheduler, entity);
    
//             entity.addEntity(world, tree);
//             scheduleActions(tree, scheduler, world, imageStore);
    
//             return true;
//         }
    
//         return false;
//     }

//     private boolean transformTree(Entity entity, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//         if (entity.health <= 0) {
//             Entity stump = Entity.createStump(Entity.STUMP_KEY + "_" + entity.id, entity.position, ImageStore.getImageList(imageStore, Entity.STUMP_KEY));
    
//             Entity.removeEntity(world, scheduler, entity);
    
//             entity.addEntity(world, stump);
    
//             return true;
//         }
    
//         return false;
//     }

//     private boolean transformPlant(Entity entity, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//         if (entity.kind == EntityKind.TREE) {
//             return entity.transformTree(entity, world, scheduler, imageStore);
//         } else if (entity.kind == EntityKind.SAPLING) {
//             return entity.transformSapling(entity, world, scheduler, imageStore);
//         } else {
//             throw new UnsupportedOperationException(String.format("transformPlant not supported for %s", entity));
//         }
//     }

//     public static void executeSaplingActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//         entity.health++;
//         if (!entity.transformPlant(entity, world, scheduler, imageStore)) {
//             EventScheduler.scheduleEvent(scheduler, entity, createActivityAction(entity, world, imageStore), entity.actionPeriod);
//         }
//     }

//     public static void executeTreeActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

//         if (!entity.transformPlant(entity, world, scheduler, imageStore)) {

//             EventScheduler.scheduleEvent(scheduler, entity, createActivityAction(entity, world, imageStore), entity.actionPeriod);
//         }
//     }

//     public static void executeFairyActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//         Optional<Entity> fairyTarget = entity.findNearest(world, entity.position, new ArrayList<>(List.of(EntityKind.STUMP)));

//         if (fairyTarget.isPresent()) {
//             Point tgtPos = fairyTarget.get().position;

//             if (entity.moveToFairy(entity, world, fairyTarget.get(), scheduler)) {

//                 Entity sapling = Entity.createSapling(SAPLING_KEY + "_" + fairyTarget.get().id, tgtPos, ImageStore.getImageList(imageStore, SAPLING_KEY), 0);

//                 entity.addEntity(world, sapling);
//                 scheduleActions(sapling, scheduler, world, imageStore);
//             }
//         }

//         EventScheduler.scheduleEvent(scheduler, entity, createActivityAction(entity, world, imageStore), entity.actionPeriod);
//     }

//     public static void executeDudeNotFullActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//         Optional<Entity> target = entity.findNearest(world, entity.position, new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));

//         if (target.isEmpty() || !entity.moveToNotFull(entity, world, target.get(), scheduler) || !entity.transformNotFull(entity, world, scheduler, imageStore)) {
//             EventScheduler.scheduleEvent(scheduler, entity, createActivityAction(entity, world, imageStore), entity.actionPeriod);
//         }
//     }


//     public static void executeDudeFullActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//         Optional<Entity> fullTarget = entity.findNearest(world, entity.position, new ArrayList<>(List.of(EntityKind.HOUSE)));

//         if (fullTarget.isPresent() && entity.moveToFull(entity, world, fullTarget.get(), scheduler)) {
//             entity.transformFull(entity, world, scheduler, imageStore);
//         } else {
//             EventScheduler.scheduleEvent(scheduler, entity, createActivityAction(entity, world, imageStore), entity.actionPeriod);
//         }
//     }


//     public static Action createAnimationAction(Entity entity, int repeatCount) {
//         return new Action(ActionKind.ANIMATION, entity, null, null, repeatCount);
//     }

//     public static Action createActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
//         return new Action(ActionKind.ACTIVITY, entity, world, imageStore, 0);
//     }

//     public static void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
//         switch (entity.kind) {
//             case DUDE_FULL:
//             EventScheduler.scheduleEvent(scheduler, entity, Entity.createActivityAction(entity, world, imageStore), entity.actionPeriod);
//             EventScheduler.scheduleEvent(scheduler, entity, Entity.createAnimationAction(entity, 0), entity.getAnimationPeriod());
//                 break;

//             case DUDE_NOT_FULL:
//                 EventScheduler.scheduleEvent(scheduler, entity, Entity.createActivityAction(entity, world, imageStore), entity.actionPeriod);
//                 EventScheduler.scheduleEvent(scheduler, entity, Entity.createAnimationAction(entity, 0), entity.getAnimationPeriod());
//                 break;

//             case OBSTACLE:
//                 EventScheduler.scheduleEvent(scheduler, entity, Entity.createAnimationAction(entity, 0), entity.getAnimationPeriod());
//                 break;

//             case FAIRY:
//                 EventScheduler.scheduleEvent(scheduler, entity, Entity.createActivityAction(entity, world, imageStore), entity.actionPeriod);
//                 EventScheduler.scheduleEvent(scheduler, entity, Entity.createAnimationAction(entity, 0), entity.getAnimationPeriod());
//                 break;

//             case SAPLING:
//                 EventScheduler.scheduleEvent(scheduler, entity, Entity.createActivityAction(entity, world, imageStore), entity.actionPeriod);
//                 EventScheduler.scheduleEvent(scheduler, entity, Entity.createAnimationAction(entity, 0), entity.getAnimationPeriod());
//                 break;

//             case TREE:
//                 EventScheduler.scheduleEvent(scheduler, entity, Entity.createActivityAction(entity, world, imageStore), entity.actionPeriod);
//                 EventScheduler.scheduleEvent(scheduler, entity, Entity.createAnimationAction(entity, 0), entity.getAnimationPeriod());
//                 break;

//             default:
//         }
//     }



// }
