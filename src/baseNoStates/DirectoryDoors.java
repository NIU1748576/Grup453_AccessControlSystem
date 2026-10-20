package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

public final class DirectoryDoors {        
  // Legacy class previously used to create and manage doors. 
  // Retained primarily for archival purposes.
  private static ArrayList<Door> allDoors; //Outdated. ArrayList that used to contain all doors.

  public static void makeDoors() {    
    //Outdated method. It used to create all doors. Now it just prints a debugging message if it's ever used by accident. Duty relegated to DirectoryAreas.
    System.out.println("Creant Portes");
  }

  public static void setAllDoors(ArrayList<Door> doors) {  
    //Outdated method. It used to set the value of the ArrayList allDoors.
    allDoors = doors;
  }

  public static Door findDoorById(String id) {     
    //Outdated function. It used to return a Door object given its id.
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    System.out.println("door with id " + id + " not found");
    return null; // otherwise we get a Java error
  }

  // this is needed by RequestRefresh
  public static ArrayList<Door> getAllDoors() {     
    //Outdated function. It used to return an ArrayList with all the doors.
    System.out.println(allDoors);
    return allDoors;
  }
}
