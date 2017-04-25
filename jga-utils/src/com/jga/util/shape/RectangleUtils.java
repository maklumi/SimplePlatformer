package com.jga.util.shape;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jga.util.Validate;

/**
 * {@link Rectangle} utilities.
 *
 * @author goran on 28/11/2016.
 */
public class RectangleUtils {

    // == public methods ==

    /**
     * Creates {@link Vector2} instance representing bottom left corner of specified {@link Rectangle}.
     *
     * @param rectangle The rectangle. Required.
     * @return {@link Vector2} instance that represents bottom left corner.
     * @throws NullPointerException if rectangle param is null.
     */
    public static Vector2 getBottomLeft(Rectangle rectangle) {
        Validate.notNull(rectangle);

        return new Vector2(rectangle.x, rectangle.y);
    }

    /**
     * Creates {@link Vector2} instance representing bottom right corner of specified {@link Rectangle}.
     *
     * @param rectangle The rectangle. Required.
     * @return {@link Vector2} instance that represents bottom right corner.
     * @throws NullPointerException if rectangle param is null.
     */
    public static Vector2 getBottomRight(Rectangle rectangle) {
        Validate.notNull(rectangle);

        return new Vector2(rectangle.x + rectangle.width, rectangle.y);
    }

    /**
     * Creates {@link Vector2} instance representing top left corner of specified {@link Rectangle}.
     *
     * @param rectangle The rectangle. Required.
     * @return {@link Vector2} instance that represents top left corner.
     * @throws NullPointerException if rectangle param is null.
     */
    public static Vector2 getTopLeft(Rectangle rectangle) {
        Validate.notNull(rectangle);

        return new Vector2(rectangle.x, rectangle.y + rectangle.height);
    }

    /**
     * Creates {@link Vector2} instance representing top right corner of specified {@link Rectangle}.
     *
     * @param rectangle The rectangle. Required.
     * @return {@link Vector2} instance that represents top right corner.
     * @throws NullPointerException if rectangle param is null.
     */
    public static Vector2 getTopRight(Rectangle rectangle) {
        Validate.notNull(rectangle);

        return new Vector2(rectangle.x + rectangle.width, rectangle.y + rectangle.height);
    }

    // == constructors ==
    private RectangleUtils() {
    }

}
