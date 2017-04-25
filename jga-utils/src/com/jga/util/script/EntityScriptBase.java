package com.jga.util.script;

/**
 * @author goran on 30/11/2016.
 */
public abstract class EntityScriptBase<T> implements EntityScript<T> {

    // == attributes ==
    private boolean finished = false;

    // == public methods ==
    @Override
    public void added(T entity) {

    }

    @Override
    public void removed(T entity) {

    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    protected void finish() {
        finished = true;
    }
}
