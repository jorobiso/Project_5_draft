import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import processing.core.PImage;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel {
    public int numRows;
    public int numCols;
    public Background[][] background;
    private Entity[][] occupancy;
    public Set<Entity> entities;

    public WorldModel() {

    }

    /**
     * Helper method for testing. Don't move or modify this method.
     */
    public List<String> log(){
        List<String> list = new ArrayList<>();
        for (Entity entity : entities) {
            String log = entity.log();
            if(log != null) list.add(log);
        }
        return list;
    }

    public Optional<PImage> getBackgroundImage(Point pos) {
        if (this.withinBounds(pos)) {
            return Optional.of(Background.getCurrentImage(this.getBackgroundCell(pos)));
        } else {
            return Optional.empty();
        }
    }

    private void parseSaveFile(Scanner saveFile, ImageStore imageStore, Background defaultBackground){
        String lastHeader = "";
        int headerLine = 0;
        int lineCounter = 0;
        while(saveFile.hasNextLine()){
            lineCounter++;
            String line = saveFile.nextLine().strip();
            if(line.endsWith(":")){
                headerLine = lineCounter;
                lastHeader = line;
                switch (line){
                    case "Backgrounds:" -> this.background = new Background[this.numRows][this.numCols];
                    case "Entities:" -> {
                        this.occupancy = new Entity[this.numRows][this.numCols];
                        this.entities = new HashSet<>();
                    }
                }
            }else{
                switch (lastHeader){
                    case "Rows:" -> this.numRows = Integer.parseInt(line);
                    case "Cols:" -> this.numCols = Integer.parseInt(line);
                    case "Backgrounds:" -> this.parseBackgroundRow(line, lineCounter-headerLine-1, imageStore);
                    case "Entities:" -> this.parseEntity(line, imageStore);
                }
            }
        }
    }

    public void setBackgroundCell(Point pos, Background background) {
        this.background[pos.y][pos.x] = background;
    }

    private Background getBackgroundCell(Point pos) {
        return this.background[pos.y][pos.x];
    }

    public void load(Scanner saveFile, ImageStore imageStore, Background defaultBackground){
        this.parseSaveFile(saveFile, imageStore, defaultBackground);
        if(this.background == null){
            this.background = new Background[this.numRows][this.numCols];
            for (Background[] row : this.background)
                Arrays.fill(row, defaultBackground);
        }
        if(this.occupancy == null){
            this.occupancy = new Entity[this.numRows][this.numCols];
            this.entities = new HashSet<>();
        }
    }

    public void setOccupancyCell(Point pos, Entity entity) {
        this.occupancy[pos.y][pos.x] = entity;
    }

    public Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.y][pos.x];
    }

    public void moveEntity(EventScheduler scheduler, Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (this.withinBounds(pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            Optional<Entity> occupant = this.getOccupant(pos);
            occupant.ifPresent(target -> removeEntity(target, scheduler));
            this.setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public boolean isOccupied(Point pos) {
        return this.withinBounds(pos) && this.getOccupancyCell(pos) != null;
    }

    public boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < this.numRows && pos.x >= 0 && pos.x < this.numCols;
    }

    public void parseEntity(String line, ImageStore imageStore) {
        String[] properties = line.split(" ", Entity.ENTITY_NUM_PROPERTIES + 1);
        if (properties.length >= Entity.ENTITY_NUM_PROPERTIES) {
            String key = properties[Entity.PROPERTY_KEY];
            String id = properties[Entity.PROPERTY_ID];
            Point pt = new Point(Integer.parseInt(properties[Entity.PROPERTY_COL]), Integer.parseInt(properties[Entity.PROPERTY_ROW]));
    
            properties = properties.length == Entity.ENTITY_NUM_PROPERTIES ?
                    new String[0] : properties[Entity.ENTITY_NUM_PROPERTIES].split(" ");
    
            switch (key) {
                case Obstacle.OBSTACLE_KEY -> this.parseObstacle(properties, pt, id, imageStore);
                case Dude.DUDE_KEY -> this.parseDude(properties, pt, id, imageStore);
                case Fairy.FAIRY_KEY -> this.parseFairy(properties, pt, id, imageStore);
                case House.HOUSE_KEY -> this.parseHouse(properties, pt, id, imageStore);
                case Tree.TREE_KEY -> this.parseTree(properties, pt, id, imageStore);
                case Sapling.SAPLING_KEY -> this.parseSapling(properties, pt, id, imageStore);
                case Stump.STUMP_KEY -> this.parseStump(properties, pt, id, imageStore);
                default -> throw new IllegalArgumentException("Entity key is unknown");
            }
        }else{
            throw new IllegalArgumentException("Entity must be formatted as [key] [id] [x] [y] ...");
        }
    }

    public void parseBackgroundRow(String line, int row, ImageStore imageStore) {
        String[] cells = line.split(" ");
        if(row < this.numRows){
            int rows = Math.min(cells.length, this.numCols);
            for (int col = 0; col < rows; col++){
                this.background[row][col] = new Background(cells[col], imageStore.getImageList(cells[col]));
            }
        }
    }

    // Parses:

    public void parseTree(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Tree.getTREE_NUM_PROPERTIES()) {
            Entity tree = new Tree(id, pt, imageStore.getImageList(Tree.getTREE_KEY()), Double.parseDouble(properties[Tree.getTREE_ACTION_PERIOD()]), Double.parseDouble(properties[Tree.getTREE_ANIMATION_PERIOD()]), Integer.parseInt(properties[Tree.getTREE_HEALTH()]));
            tryAddEntity(tree);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Tree.getTREE_KEY(), Tree.getTREE_NUM_PROPERTIES()));
        }
    }

    public void parseStump(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Stump.getSTUMP_NUM_PROPERTIES()) {
            Entity stump = new Stump(id, pt, imageStore.getImageList(Stump.getSTUMP_KEY()));
            tryAddEntity(stump);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Stump.getSTUMP_KEY(), Stump.getSTUMP_NUM_PROPERTIES()));
        }
    }

    public void parseSapling(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Sapling.getSAPLING_NUM_PROPERTIES()) {
            int health = Integer.parseInt(properties[Sapling.getSAPLING_HEALTH()]);
            
            Entity sapling = new Sapling(id, pt, imageStore.getImageList(Sapling.getSAPLING_KEY()), Sapling.getSAPLING_ACTION_ANIMATION_PERIOD(), Sapling.getSAPLING_ACTION_ANIMATION_PERIOD(), health, Sapling.getSAPLING_HEALTH_LIMIT());
    
    
            tryAddEntity(sapling);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Sapling.getSAPLING_KEY(), Sapling.getSAPLING_NUM_PROPERTIES()));
        }
    }

    public void parseObstacle(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Obstacle.getOBSTACLE_NUM_PROPERTIES()) {
    
            Entity obstacle = new Obstacle(id, pt, Double.parseDouble(properties[Obstacle.getOBSTACLE_ANIMATION_PERIOD()]), imageStore.getImageList(Obstacle.getOBSTACLE_KEY()));
            tryAddEntity(obstacle);
    
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Obstacle.getOBSTACLE_KEY(), Obstacle.getOBSTACLE_NUM_PROPERTIES()));
        }
    }

    public void parseHouse(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == House.getHOUSE_NUM_PROPERTIES()) {
            Entity house = new House(id, pt, imageStore.getImageList(House.getHOUSE_KEY()));
            tryAddEntity(house);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", House.getHOUSE_KEY(), House.getHOUSE_NUM_PROPERTIES()));
        }
    }

    public void parseFairy(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Fairy.getFAIRY_NUM_PROPERTIES()) {
            Entity fairy = new Fairy(id, pt, imageStore.getImageList(Fairy.getFAIRY_KEY()), Double.parseDouble(properties[Fairy.getFAIRY_ACTION_PERIOD()]), Double.parseDouble(properties[Fairy.getFAIRY_ANIMATION_PERIOD()]));
            tryAddEntity(fairy);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Fairy.getFAIRY_KEY(), Fairy.getFAIRY_NUM_PROPERTIES()));
        }
    }

    // public void parseWorm(String[] properties, Point pt, String id, ImageStore imageStore) {
    //     if (properties.length == Worm.getWormNumProperties()) {
    //         Entity worm = new Worm(id, pt, imageStore.getImageList(Worm.getWormKey()), Double.parseDouble(properties[Worm.getWormActionPeriod()]), Double.parseDouble(properties[Worm.getWormAnimationPeriod()]));
    //         tryAddEntity(worm);
    //     }else{
    //         throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Worm.getWormKey(), Worm.getWormNumProperties()));
    //     }
    // }


    public void parseDude(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Dude.getDUDE_NUM_PROPERTIES()) {
            DudeNotFull dude = new DudeNotFull(id, pt, imageStore.getImageList(Dude.getDUDE_KEY()), Integer.parseInt(properties[Dude.getDUDE_LIMIT()]), Dude.getDUDE_NUM_PROPERTIES(), Double.parseDouble(properties[Dude.getDUDE_ACTION_PERIOD()]), Double.parseDouble(properties[Dude.getDUDE_ANIMATION_PERIOD()]));
            tryAddEntity(dude);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Dude.getDUDE_KEY(), Dude.getDUDE_NUM_PROPERTIES()));
        }
    }

    public Optional<Entity> getOccupant(Point pos) {
        if (this.isOccupied(pos)) {
            return Optional.of(this.getOccupancyCell(pos));
        } else {
            return Optional.empty();
        }
    }

    void removeEntityAt(Entity entity2, Point pos) {
        if (withinBounds(pos) && getOccupancyCell(pos) != null) {
            Entity entity = getOccupancyCell(pos);
    
            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }

    public void removeEntity(Entity entity, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(entity);
        removeEntityAt(entity, entity.getPosition());
    }

    protected void addEntity(Entity entity) {
        if (withinBounds(entity.getPosition())) {
            setOccupancyCell(entity.getPosition(), entity);
            entities.add(entity);
        }
    }

    protected Optional<Entity> findNearest(Entity entity2, Point pos, List<Class<?>> kinds) {
        List<Entity> ofType = new LinkedList<>();
        for (Class<?> kind : kinds) {
            for (Entity entity : entities) {
                if (kind.isInstance(entity)) {
                    ofType.add(entity);
                }
            }
        }
        return pos.nearestEntity(ofType);
    }

    public void tryAddEntity(Entity entity) {
        if (isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity);
    }
    
    /*
       Assumes that there is no entity currently occupying the
       intended destination cell.
    */

    
}
