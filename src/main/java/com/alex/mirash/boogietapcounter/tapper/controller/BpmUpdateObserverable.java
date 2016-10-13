package com.alex.mirash.boogietapcounter.tapper.controller;

import com.alex.mirash.boogietapcounter.tapper.tool.BpmUpdateListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Mirash
 */

public abstract class BpmUpdateObserverable<T> {
    private List<BpmUpdateListener<T>> observers = new ArrayList<>();

    public void addBpmUpdateObserver(BpmUpdateListener<T> observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void removeBpmUpdateObserver(BpmUpdateListener<T> observer) {
        observers.removeAll(Collections.singleton(observer));
    }

    public void removeAllObservers() {
        observers.clear();
    }

    protected void notifyObservers(T data) {
        for (BpmUpdateListener<T> observer : observers) {
            observer.onBpmUpdate(data);
        }
    }
}
