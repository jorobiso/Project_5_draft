import java.util.List;

import processing.core.PImage;

public class Stump extends Entity {
    protected static final String STUMP_KEY = "stump";
    protected static final int STUMP_NUM_PROPERTIES = 0;

    public Stump(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }

    // public Entity create(String id, Point position, List<PImage> images) {
    //     return new Stump(id, position, images);
    // }
}
