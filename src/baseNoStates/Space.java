package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

public class Space extends Area {  
// Class that's meant to represent a particular room inside a partition or individual room. Inheritance of the abstract class Area. 
// Acts as the 'Leaf' element in the Composite design pattern
  private ArrayList<Door> doors = new ArrayList<>();  // Registry of all doors that provide access to this space
  // This allows to determine how rooms relate to one another and which doors provide access to a particular area.

  public Space(String id, Area parent) {  
  // Constructor that initializes the space id and parent attribute via the parent class constructor.
    super(id, parent);
  }


  public void addDoorGivingAccess(Door door) {    
  // Registers a door as giving access to this space if it is not already in the list.
    if (!doors.contains(door)) {
      doors.add(door);
    }
  }

  @Override
  public ArrayList<Door> getDoorsGivingAccess() { 
  // Implementation of abstract function. Returns all doors giving access to the itself (the instance's space).
    return new ArrayList<>(doors);
  }

  @Override
  public Area findAreaById(String id) {   
  //Implementation of abstract function. Returns itself if the ids line up. Otherwise, it returns null.
    if(id.equals(this.getId()))
      return this;
    else
      return null;
  }

  @Override
  public ArrayList<Space> getSpaces() {   
  //Implementation of abstract function. Returns an ArrayList of itself, as it is the only space contained within itself.
    return new ArrayList<>(Arrays.asList(this));
  }

}
