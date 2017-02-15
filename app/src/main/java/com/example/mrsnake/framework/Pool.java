package com.example.mrsnake.framework;


import java.util.ArrayList;
import java.util.List;

public class Pool<T> {

    public interface PoolObjectFactory<T> {
        T createObject();
    }

    private final List<T> mFreeObjects;
    private final PoolObjectFactory<T> mFactory;
    private final int mMaxSize;

    public Pool(PoolObjectFactory<T> factory, int maxSize) {
        this.mFactory = factory;
        this.mMaxSize = maxSize;
        this.mFreeObjects = new ArrayList<>(maxSize);
    }

    public T newObject() {
        T object = null;

        if (mFreeObjects.isEmpty()) {
            object = mFactory.createObject();
        } else {
            object = mFreeObjects.remove(mFreeObjects.size() - 1);
        }

        return object;
    }

    public void free(T object) {
        if (mFreeObjects.size() < mMaxSize) {
            mFreeObjects.add(object);
        }
    }
}
