import java.util.List;
import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;

public final class Viewport {
    private int row;
    private int col;
    private int numRows;
    private int numCols;

// Getters for above fields:
    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

// Constructor
    public Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public static Point viewportToWorld(Viewport viewport, int col, int row) {
        return new Point(col + viewport.col, row + viewport.row);
    }

    public static Point worldToViewport(Viewport viewport, int col, int row) {
        return new Point(col - viewport.col, row - viewport.row);
    }

    private static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }

    public static void shiftView(WorldView view, int colDelta, int rowDelta) {
        int newCol = clamp(view.viewport.col + colDelta, 0, view.world.numCols - view.viewport.numCols);
        int newRow = clamp(view.viewport.row + rowDelta, 0, view.world.numRows - view.viewport.numRows);
    
        view.viewport.shift(newCol, newRow);
    }

    public static void processImageLine(Map<String, List<PImage>> images, String line, PApplet screen) {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = ImageStore.getImages(images, key);
                imgs.add(img);
    
                if (attrs.length >= ImageStore.getKeyedImageMin()) {
                    int r = Integer.parseInt(attrs[ImageStore.KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[ImageStore.KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[ImageStore.KEYED_BLUE_IDX]);
                    ImageStore.setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }
    }

    private void shift(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public static boolean contains(Viewport viewport, Point p) {
        return p.y >= viewport.row && p.y < viewport.row + viewport.numRows && p.x >= viewport.col && p.x < viewport.col + viewport.numCols;
    }
}
