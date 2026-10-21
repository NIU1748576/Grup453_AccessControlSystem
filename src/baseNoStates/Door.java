package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;



public class Door implements ClockObserver{ // Class meant to represent a physical door connecting two spaces.
// Users with certain permissions can interact with it in a variety of ways (open, close, lock, unlock, etc.)
// We use this class as the "Observer" of the observer design pattern, because that way we can have an external
// object change periodically to know if the door has been unlocked shortly at least 10 seconds to lock it again.

  private final String id;
  private boolean closed;
  private boolean locked;
  private boolean propped;
  private boolean unlockedShortly;
  private final Space fromSpace; // Where it comes from
  private final Space toSpace; // Where it goes to
  private LocalDateTime timeSinceUnlockedShortly;

    public Door(String id, Space fromSpace, Space toSpace) {
//Class constructor.
// Initializes the door and registers it with the destination space.
    this.id = id;
    this.closed = true;
    this.locked = false;
    this.fromSpace = fromSpace;
    this.toSpace = toSpace;

    if (toSpace != null) {
      toSpace.addDoorGivingAccess(this);
    }
  }
// Spaces need to know their incoming doors to realize what
// space they lead to.

  //Prebuilt method
  public void processRequest(RequestReader request) {
    // it is the Door that process the request because the door has and knows
    // its state, and if closed or open
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
    } else {
      System.out.println("not authorized");
    }
    request.setDoorStateName(getStateName());
  }

  public void doAction(String action) {
    // Executes a specific door action (OPEN, CLOSE, LOCK, UNLOCK or UNLOCK_SHORTLY)
    // after validating physical and security constraints.
    switch (action) {
      case Actions.OPEN:
        if (closed) {
          if (locked) { // If the door is locked and closed it can't be open
            System.out.println("Can't open door " + id + " because it's locked");
          } else { // If the door is unlocked and closed, it can be open
            closed = false;
          }
        } else { // If the door already open, we do nothing
          System.out.println("Can't open door " + id + " because it's already open");
        }
        break;
      case Actions.CLOSE:
        if (closed) {
          System.out.println("Can't close door " + id + " because it's already closed");
        }
        else {
          closed = true;
          if (propped) {      // Propped --close--> Locked
            propped = false;
            locked = true;
          }
        }
        break;
      case Actions.LOCK:
        if (closed) {
          if (locked) {
            System.out.println("Can't lock door " + id + " because it's already locked");
          } else {
            locked = true;
          }
        } else {
          System.out.println("Can't lock door " + id + " because it's open");
        }
        break;
      case Actions.UNLOCK:
        if (closed) {
          if (locked) {
            locked = false;
          } else {
            System.out.println("Can't unlock door " + id + " because it's already unlocked");
          }
        }
        else {
          System.out.println("Can't unlock door " + id + " because it's open");
        }
        break;

      case Actions.UNLOCK_SHORTLY:
        if (closed && locked) {
          locked = false;
          unlockedShortly = true;
          timeSinceUnlockedShortly = LocalDateTime.now();
          Clock.getInstance().addObserver(this);
        } else {
          System.out.println("Can't unlock shortly door " + id + "because it's already unlocked or open/propped");
        }
        break;
      default:
        assert false : "Unknown action " + action;
        System.exit(-1);
    }
  }
  public boolean isClosed() {
    return closed;
  }

  public String getId() {
    return id;
  }

  public String getStateName() {
    if (propped)
      return "propped";
    if (unlockedShortly)
      return "unlocked_shortly";
    if (locked)
      return "locked";
    else
      return "unlocked";
  }

  @Override
  public String toString() {
    return "Door{"
            + ", id='" + id + '\''
            + ", closed=" + closed
            + ", state=" + getStateName()
            + "}";
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", closed);
    return json;
  }

  // NEW FUNCTIONS
  public Space getFromSpace() { return fromSpace; }  //Returns the space the door is in (door is currently monodirectional).
  public Space getToSpace() { return toSpace; } //Returns the space the door connects to (door is currently monodirectional).

  @Override
  public synchronized void update(LocalDateTime now) {
      // Called by Clock via notifyObservers().
    // Looks the duration the door has been unlocked shortly and if it's 10 seconds or more,
    // it sets the door to locked or propped and deletes the observer.
      int LOCK_TIMER = 10;
      if (Duration.between(timeSinceUnlockedShortly, now).compareTo(Duration.ofSeconds(LOCK_TIMER)) >= 0) {
      unlockedShortly = false;
      if (closed) {
        locked = true;
      } else {
        propped = true;
      }
      Clock.getInstance().deleteObserver(this);
    }
  }
}
