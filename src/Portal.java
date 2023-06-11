import java.util.List;

import processing.core.PImage;

public class Portal extends Entity {
    protected static final String PORTAL_KEY = "portal";

    public Portal(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }

    public static String getPortalKey() {
        return PORTAL_KEY;
    }

    

}
