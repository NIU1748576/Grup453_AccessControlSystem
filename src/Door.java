package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;


public class Door {
  private final String id;
  private boolean closed; // physically
  private boolean locked; // new
  private final Space fromSpace; // Where it comes from
  private final Space toSpace; // Where it goes to

  public Door(String id, Space fromSpace, Space toSpace) {
    this.id = id;
    this.closed = true;
    this.locked = false;
    this.fromSpace = fromSpace;
    this.toSpace = toSpace;

    if (toSpace != null) {
      toSpace.addDoorGivingAccess(this);
    }
  }

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

  private void doAction(String action) {
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
        } else { // If the door is open, it can be closed
          closed = true;
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
        // TODO
        System.out.println("Action " + action + " not implemented yet");
        break;
      default:
        assert false : "Unknown action " + action;
        System.exit(-1);
    }
  }
  // NEW METHODS
  public Space getFromSpace() { return fromSpace; }
  public Space getToSpace() { return toSpace; }

  public boolean isClosed() {
    return closed;
  }

  public String getId() {
    return id;
  }

  public String getStateName() {
    if (locked) {
      return "locked";
    } else {
      return "unlocked";
    }
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
}
