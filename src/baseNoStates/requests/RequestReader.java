package baseNoStates.requests;

import baseNoStates.*;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestReader implements Request {
  private final String credential; // who
  private final String action;     // what
  private final LocalDateTime now; // when
  private final String doorId;     // where
  private String userName;
  private boolean authorized;
  private final ArrayList<String> reasons; // why not authorized
  private String doorStateName;
  private boolean doorClosed;

  public RequestReader(String credential, String action, LocalDateTime now, String doorId) {
    this.credential = credential;
    this.action = action;
    this.doorId = doorId;
    reasons = new ArrayList<>();
    this.now = now;
  }

  public void setDoorStateName(String name) {
    doorStateName = name;
  }

  public String getAction() {
    return action;
  }

  public boolean isAuthorized() {
    return authorized;
  }

  public void addReason(String reason) {
    reasons.add(reason);
  }


  @Override
  public String toString() {
    if (userName == null) {
      userName = "unknown";
    }
    return "Request{"
            + "credential=" + credential
            + ", userName=" + userName
            + ", action=" + action
            + ", now=" + now
            + ", doorID=" + doorId
            + ", closed=" + doorClosed
            + ", authorized=" + authorized
            + ", reasons=" + reasons
            + "}";
  }

  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("authorized", authorized);
    json.put("action", action);
    json.put("doorId", doorId);
    json.put("closed", doorClosed);
    json.put("state", doorStateName);
    json.put("reasons", new JSONArray(reasons));
    return json;
  }

  // see if the request is authorized and put this into the request, then send it to the door.
  // if authorized, perform the action.
  public void process() {
    User user = DirectoryUsers.findUserByCredential(credential);
    Door door = DirectoryAreas.findDoorById(doorId);
    //Debugging commands
    if(user != null) {
      System.out.println("The user " + user.getName() +" belongs to class " + user.getUserGroup().getName());
      System.out.println("Members of " + user.getUserGroup().getName() + " are: " + user.getUserGroup().getUsers());
    }
    assert door != null : "door " + doorId + " not found";
    authorize(user, door);
    // this sets the boolean authorize attribute of the request
    door.processRequest(this);
    // even if not authorized we process the request, so that if desired we could log all
    // the requests made to the server as part of processing the request
    doorClosed = door.isClosed();
  }

  // the result is put into the request object plus, if not authorized, why not,
  // only for testing
  private void authorize(User user, Door door) {
    if (user == null) {
      authorized = false;
      addReason("user doesn't exists");
    } else {
      //TODO: get the who, where, when and what in order to decide, and if not
      // authorized add the reason(s)

      Schedule employeeSchedule = new Schedule(LocalDate.of(LocalDate.now().getYear(),9,1), LocalDate.of(LocalDate.now().getYear() + 1, 3, 1), LocalTime.of(9,0),LocalTime.of(17,0),new ArrayList<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)));
      Schedule managerSchedule = new Schedule(LocalDate.of(LocalDate.now().getYear(),9,1), LocalDate.of(LocalDate.now().getYear() + 1, 3, 1), LocalTime.of(8,0), LocalTime.of(20,0), new ArrayList<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)));
      switch (user.getUserGroup().getName()){      //If user belongs to group 'invalid' nothing is triggered and authorized remains false.
        case "employees":
          boolean inSchedule = false;
          boolean canAccess = false;
          boolean canDo = false;
          Space whereTo = DirectoryAreas.findDoorById(doorId).getToSpace();
          Space whereFrom = DirectoryAreas.findDoorById(doorId).getFromSpace();
          if(employeeSchedule.contains(now))
            inSchedule = true;
          else
            reasons.add("User is off schedule");
          if (user.canBeInSpace(whereTo) && user.canBeInSpace(whereFrom))
            canAccess = true;
          else
            reasons.add("User cannot access this area");
          if (user.canPerformAction(action))
            canDo = true;
          else
            reasons.add("User cannot perform this action");
          if (inSchedule && canAccess && canDo)
            authorized = true;
          break;
        case "managers":
          if(managerSchedule.contains(now)) {
            authorized = true;
          }
          else
            reasons.add("User is off schedule");
          break;
        case "admin":
          if(now.getYear() < 2100){
            authorized = true;
          }
          else
            reasons.add("U too old 4 ts gng");
          break;
        default:
          reasons.add("User doesn't have permission");
      }
      if(!door.isClosed() && action.equals(Actions.LOCK))
        reasons.add("Can't lock an open door");
      else if(door.getStateName().equals("locked") && action.equals(Actions.OPEN))
        reasons.add("Can't open a locked door");
      else if (door.getStateName().equals("unlocked_shortly") && action.equals(Actions.LOCK))
        reasons.add("Can't lock a shortly unlocked door");
      else if (authorized)
          reasons.add("All correct");
    }
  }
}

