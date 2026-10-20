package baseNoStates;

import java.util.ArrayList;

public class User {            
  // Represents a user of the access control system.
  // Each user has unique credentials and belongs to a user group.
  private final String name; 
  private final String credential;  

  public User(String name, String credential) {  
    this.name = name;
    this.credential = credential;
  }

  //Class getters
  public String getCredential() {
    return credential;
  }
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  } //Prebuilt function

  public UserGroup getUserGroup(){   
    // Searches for and returns the UserGroup this user belongs to by iterating over the global directory of groups. 
    // Returns null if not found.
    for (UserGroup ug : DirectoryUserGroups.getUserGroups()) {
      if (ug.findUserByCredential(credential) != null) {
        return ug;
      }
    }
    System.out.println("user with credential " + credential + " has no UserGroup associated");
    return null;
  }
  // ACCESS RESTRICTION: Validates whether the user is permitted to be in a given space. 
  // This is used when we do a request to see if the user group the user belongs is able to access this space.
  // The only restriction is for employees, that can't access to the parking area.
  public boolean canBeInSpace(Space space) {
      return !this.getUserGroup().getName().equals("employees") || !space.getId().equals("parking");
  }

  // ACTION RESTRICTION: Validates whether the user can perform a specific action on doors.
  // This is used when we do a request to see if the user group the user belongs is able to do certain action.
  // The only restriction is for employees, that can't unlock and lock doors.
  public boolean canPerformAction(String action){
      return !this.getUserGroup().getName().equals("employees") || (!action.equals(Actions.LOCK) && !action.equals(Actions.UNLOCK));
  }
}
