package baseNoStates;

import java.util.ArrayList;

public final class DirectoryUsers {             
  // Utility class designated to create and manage all User instances in the system.
  private static final ArrayList<User> users = new ArrayList<>();  //ArrayList of all Users.

  public static void makeUsers() {  
    // Instantiates all system users across different privilege tiers 
    // (blank, employees, managers, admin) and stores them in the master list.

    // users without any privilege, just to keep temporally users instead of deleting them,
    // this is to withdraw all permissions but still to keep user data to give back
    // permissions later
    users.add(new User("Bernat", "12345"));
    users.add(new User("Blai", "77532"));

    // employees :
    // Sep. 1 this year to Mar. 1 next year
    // week days 9-17h
    // just shortly unlock
    // ground floor, floor1, exterior, stairs (this, for all), that is, everywhere but the parking
    users.add(new User("Ernest", "74984"));
    users.add(new User("Eulalia", "43295"));

    // managers :
    // Sep. 1 this year to Mar. 1 next year
    // week days + saturday, 8-20h
    // all actions
    // all spaces
    users.add(new User("Manel", "95783"));
    users.add(new User("Marta", "05827"));

    // admin :
    // always=Jan. 1 this year to 2100
    // all days of the week
    // all actions
    // all spaces
    users.add(new User("Ana", "11343"));
  }

  public static User findUserByCredential(String credential) {   
    // Searches the global registry and returns a User object given its credential ID. Returns null if no match is found.
    for (User user : users) {
      if (user.getCredential().equals(credential)) {
        return user;
      }
    }
    return null;
  }

}
