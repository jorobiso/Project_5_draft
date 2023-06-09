import java.util.Optional;

import processing.core.PApplet;
import processing.core.PImage;

public final class WorldView {
    private PApplet screen;
    public WorldModel world;
    private int tileWidth;
    private int tileHeight;
    public Viewport viewport;

    public WorldView(int numRows, int numCols, PApplet screen, WorldModel world, int tileWidth, int tileHeight) {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }

    private void drawBackground() {
        for (int row = 0; row < this.viewport.getNumRows(); row++) {
            for (int col = 0; col < this.viewport.getNumCols(); col++) {
                Point worldPoint = Viewport.viewportToWorld(this.viewport, col, row);
                Optional<PImage> image = this.world.getBackgroundImage(worldPoint);
                if (image.isPresent()) {
                    this.screen.image(image.get(), col * this.tileWidth, row * this.tileHeight);
                }
            }
        }
    }

    private void drawEntities() {
        for (Entity entity : this.world.entities) {
            Point pos = entity.getPosition();
    
            if (Viewport.contains(this.viewport, pos)) {
                Point viewPoint = Viewport.worldToViewport(this.viewport, pos.x, pos.y);
                this.screen.image(Background.getCurrentImage(entity), viewPoint.x * this.tileWidth, viewPoint.y * this.tileHeight);
            }
        }
    }

    public static void drawViewport(WorldView view) {
        view.drawBackground();
        view.drawEntities();
    }
}
