package com.jga.util.script;

/**
 * @author goran on 29/11/2016.
 */
public interface EntityScript<T> {

    void added(T entity);

    void removed(T entity);

    void update(float delta);

    boolean isFinished();
}
