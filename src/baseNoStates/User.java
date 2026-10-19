package baseNoStates;

import java.util.ArrayList;

public class User {            //Class that's meant to represent a certain user. Users belong to groups that have certain permissions.
  private final String name; //Username
  private final String credential;  //User id

  public User(String name, String credential) {  //Class constructor.
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

  public UserGroup getUserGroup(){   //Looks through every UserGroup and sees which one it belongs to, then returns it. Returns null if nothing is found.
    for (UserGroup ug : DirectoryUserGroups.getUserGroups()) {
      if (ug.findUserByCredential(credential) != null) {
        return ug;
      }
    }
    System.out.println("user with credential " + credential + " has no UserGroup associated");
    return null;
  }

  public boolean canBeInSpace(Space space) {   //Checks if user can be in space
      return !this.getUserGroup().getName().equals("employees") || !space.getId().equals("parking");
  }

  public boolean canPerformAction(String action){  //Checks if user can perform action
      return !this.getUserGroup().getName().equals("employees") || (!action.equals(Actions.LOCK) && !action.equals(Actions.UNLOCK));
  }
}
