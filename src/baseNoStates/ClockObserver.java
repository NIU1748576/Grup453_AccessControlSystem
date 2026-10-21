package baseNoStates;

import java.time.LocalDateTime;

public interface ClockObserver {
  // Observer interface of the Observer pattern.
  // Doors that need to be automatically re-locked after a short unlock
  // implement this interface so the Clock can notify them every second.
  void update(LocalDateTime now);
}
