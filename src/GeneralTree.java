import java.util.List;

import processing.core.PImage;

public abstract class GeneralTree extends Wiggler {

    public GeneralTree(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

}
