package com.jga.util.debug;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.jga.util.Validate;
import com.jga.util.entity.EntityBase;

/**
 * {@link ShapeRenderer} utility methods.
 *
 * @author goran on 16/11/2016.
 */
public class ShapeRendererUtils {

    // == constructors ==
    private ShapeRendererUtils() {
        // no-op constructor
    }

    // == public methods ==

    /**
     * Calls {@link ShapeRenderer#rect(float, float, float, float)} to draw specified {@link Rectangle}
     * NOTE: This method needs to be called inside {@link ShapeRenderer#begin()} and {@link ShapeRenderer#end()} block.
     *
     * @param renderer  The renderer to be used for drawing rectangle. Required.
     * @param rectangle The {@link Rectangle} instance. Required.
     * @throws NullPointerException if any argument is null.
     */
    public static void rect(ShapeRenderer renderer, Rectangle rectangle) {
        Validate.notNull(renderer);
        Validate.notNull(rectangle);

        renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    /**
     * Calls {@link ShapeRenderer#circle(float, float, float, int)} using 30 segments.
     * NOTE: This method needs to be called inside {@link ShapeRenderer#begin()} and {@link ShapeRenderer#end()} block.
     *
     * @param renderer The renderer to be use for drawing circle. Required.
     * @param circle   The {@link Circle} instance.
     * @throws NullPointerException if any argument is null.
     */
    public static void circle(ShapeRenderer renderer, Circle circle) {
        Validate.notNull(renderer);
        Validate.notNull(circle);

        renderer.circle(circle.x, circle.y, circle.radius, 30);
    }

    public static void polygon(ShapeRenderer renderer, Polygon polygon) {
        Validate.notNull(renderer);
        Validate.notNull(polygon);

        renderer.polygon(polygon.getTransformedVertices());
    }

    public static <T extends EntityBase> void entities(ShapeRenderer renderer, Array<T> entities) {
        Validate.notNull(renderer);
        Validate.notNull(entities);

        for (int i = 0; i < entities.size; i++) {
            T entityBase = entities.get(i);
            polygon(renderer, entityBase.getBounds());
        }
    }
}
