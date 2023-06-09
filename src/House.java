import java.util.List;

import processing.core.PImage;

public class House extends Entity {
    protected static final String HOUSE_KEY = "house";
    protected static final int HOUSE_NUM_PROPERTIES = 0;

    public House(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }

}
