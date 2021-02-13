package edu.byu.cs.tweeter.view.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSubject {

    //Do we still need this package?
    private List<ObserverInterface> observers;

    public void registerObserver(ObserverInterface observer) {
        this.observers.add(observer);
    }

    public void deregisterObserver(ObserverInterface observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers(Object data) {
        for (ObserverInterface ob : this.observers) {
            ob.update(data);
        }
    }

    public AbstractSubject() {
        this.observers = new ArrayList<>();
    }
}
