package baseNoStates;

import java.util.ArrayList;

public class User {
  private final String name;
  private final String credential;

  public User(String name, String credential) {
    this.name = name;
    this.credential = credential;
  }

  public String getCredential() {
    return credential;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }

  public UserGroup getUserGroup(){
    for (UserGroup ug : DirectoryUserGroups.getUserGroups()) {
      if (ug.findUserByCredential(credential) != null) {
        return ug;
      }
    }
    System.out.println("user with credential " + credential + " has no UserGroup associated");
    return null;
  }

  public boolean canBeInSpace(Space space) {
      return !this.getUserGroup().getName().equals("employees") || !space.getId().equals("parking");
  }

  public boolean canPerformAction(String action){
      return !this.getUserGroup().getName().equals("employees") || (!action.equals("lock") && !action.equals("unlock"));
  }
}
