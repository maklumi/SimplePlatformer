package com.jga.util;

/**
 * @author goran on 16/11/2016.
 */
public final class Validate {

    // == constants ==
    private static final String DEFAULT_IS_NULL_MESSAGE = "The validated object is null";

    // == constructors ==
    private Validate() {
    }

    // == public methods ==

    /**
     * Validate that the specified argument is not null, otherwise throwing an exception with
     * message "The validated object is null."
     * <p>Example usage:</p>
     * <p>
     * <code>
     * Validate.notNull(myObject, "My Message");
     * </code>
     *
     * @param object the object to check.
     * @param <T>    the object type.
     */
    public static <T> void notNull(T object) {
        notNull(object, DEFAULT_IS_NULL_MESSAGE);
    }

    /**
     * Validate that the specified argument is not {@code null}, otherwise throwing an exception with the specified message.
     * <p>Example usage:</p>
     * <p>
     * <code>
     * Validate.notNull(myObject, "My Message");
     * </code>
     *
     * @param object  the object to check.
     * @param message the message
     * @param <T>     the object type.
     */
    public static <T> void notNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
    }
}
