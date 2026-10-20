package baseNoStates;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public final class Clock {
    private static final Clock instance = new Clock();
    private final ArrayList<ClockObserver> observers = new ArrayList<>();

    private Clock() {
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
        LocalDateTime now = LocalDateTime.now();
        ArrayList<ClockObserver> observers_copy;
        synchronized (observers){
            observers_copy = new ArrayList<>(observers);
        }
        for (ClockObserver door : observers_copy) { // safe even if an observer removes itself
            door.update(now);
        }
    }
}
