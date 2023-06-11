import java.util.List;

import processing.core.PImage;

public class Twistedoak extends Entity {
    protected static final String TWISTED_OAK_KEY = "toak";
    
    public Twistedoak(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }

    public static String getTwistedOakKey() {
        return TWISTED_OAK_KEY;
    }
}
