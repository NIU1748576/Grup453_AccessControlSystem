package baseNoStates;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public final class Clock {
    // Singleton. "Observable" subject of the Observer pattern.
    // Ticks every second and notifies all registered ClockObservers
    // (doors that have been unlocked shortly).
    private static final Clock instance = new Clock();
    private final ArrayList<ClockObserver> observers = new ArrayList<>();

    private Clock() {
        // Starts a timer that fires every second when the instance is created.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                notifyObservers();
            }
        }, 1000L, 1000L); // tick every second
    }

    public static Clock getInstance() {
        return instance;
    }

    public void addObserver(ClockObserver door) {
        observers.add(door);
    }

    public void deleteObserver(ClockObserver door) {
        observers.remove(door);
    }

    private void notifyObservers() {
        // Called by Clock every second while a door is unlocked shortly.
        // Creates a copy of the observer list and calls the update function for each observer.
        LocalDateTime now = LocalDateTime.now();
        ArrayList<ClockObserver> observers_copy;
        synchronized (observers){
            // The synchronized keyword is important to make sure the ArrayList doesn't
            // give an error if it's ever modified while iterating through it.
            observers_copy = new ArrayList<>(observers);
        }
        for (ClockObserver door : observers_copy) {
            door.update(now);
        }
    }
}
