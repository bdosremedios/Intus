package model;

import java.util.ArrayList;
import java.util.List;

// Subject in Observable pattern
public abstract class Subject {

    public List<Observer> observers = new ArrayList<Observer>();

    // EFFECTS: notifies observer of change
    public void notifyObservers() {
        for (Observer o: observers) {
            o.update();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds observer to list of observers
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

}
