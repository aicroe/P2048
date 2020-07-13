package org.example.p2048;

import java.util.ArrayList;
import java.util.List;


public abstract class Observable {
    
    private List<Observer> observers;
    
    public Observable() {
        
        observers = new ArrayList<>();
    }
    
    public void addObserver(Observer o) {
        
        observers.add(o);
    }
    
    public void notifyObservers() {
        
        for (Observer o: observers) {
            o.update();
        }
    }
    
    public void removeObservers() {
        
        observers.clear();
    }
    
}
