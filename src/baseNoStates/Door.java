package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;


public class Door implements ClockObserver{                 //Class meant to represent a door between two spaces. Users with certain permissions can interact with them in a variety of ways (open, close, lock, unlock, etc.)
  private final String id;
  private boolean closed; // physically
  private boolean locked; // new
  private boolean propped;
  private boolean unlockedShortly;
  private final Space fromSpace; // Where it comes from
  private final Space toSpace; // Where it goes to
  private LocalDateTime unlockTime;

  public Door(String id, Space fromSpace, Space toSpace) {   //Class constructor. Initializes the class's attributes and defines itself as giving access to the place it leads to.
    this.id = id;
    this.closed = true;
    this.locked = false;
    this.fromSpace = fromSpace;
    this.toSpace = toSpace;

    if (toSpace != null) {
      toSpace.addDoorGivingAccess(this);
    }
  }
  //Prebuilt methods
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
        if (closed) { // If the door already closed, we do nothing
          System.out.println("Can't close door " + id + " because it's already closed");
        }
        else { // If the door is open, it can be closed
          closed = true;
          if (propped) {      // Propped --close--> Locked
            propped = false;
            locked = true;
          }
        }
        break;
      case Actions.LOCK:
        if (closed) { // If we want to lock the door, it should be closed
          if (locked) { // If the door is locked, we do nothing
            System.out.println("Can't lock door " + id + " because it's already locked");
          } else { // If the door is unlocked, we can lock it
            locked = true;
          }
        } else { // If it isn't closed, we can't lock it
          System.out.println("Can't lock door " + id + " because it's open");
        }
        break;
        // fall through
      case Actions.UNLOCK:
        if (closed) { // If the door is locked, it should be closed
          if (locked) { // If the door is locked, we can unlock it
            locked = false;
          } else { // If the door is already unlocked, we do nothing
            System.out.println("Can't unlock door " + id + " because it's already unlocked");
          }
        }
        else { // If it isn't closed, we can't unlock it
          System.out.println("Can't unlock door " + id + " because it's open");
        }
        break;
        // fall through
      case Actions.UNLOCK_SHORTLY:
        if (closed && locked) {
          locked = false;
          unlockedShortly = true;
          unlockTime = LocalDateTime.now();
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
  public void setPropped(Boolean isItPropped) {propped = isItPropped;}

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
    if (Duration.between(unlockTime, now).compareTo(Duration.ofSeconds(10)) >= 0) {
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
