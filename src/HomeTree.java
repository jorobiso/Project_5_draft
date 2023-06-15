import processing.core.PImage;

import java.util.List;

public class HomeTree extends Entity {

    protected static final String HOME_TREE_KEY = "homeTree";

    public HomeTree(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }

    public static String getHomeTreeKey() {
        return HOME_TREE_KEY;
    }

}

