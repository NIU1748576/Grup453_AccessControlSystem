package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

public class Space extends Area {  //Class that's meant to represent a particular room inside a partition. Inheritance of the abstract class Area. 'Leaf' element of the composite design pattern.
  private ArrayList<Door> doors = new ArrayList<>();  //ArrayList of all doors in the Space (Room).

  public Space(String id, Area parent) {  //Class constructor. Summons parent class's constructor to initialize attributes.
    super(id, parent);
  }

  public void addDoorGivingAccess(Door door) {    //Checks if a door is in the doors ArrayList and adds it.
    if (!doors.contains(door)) {
      doors.add(door);
    }
  }

  @Override
  public ArrayList<Door> getDoorsGivingAccess() { //Implementation of abstract function. Returns all doors giving access to the itself (the instance's space).
    return new ArrayList<>(doors);
  }

  @Override
  public Area findAreaById(String id) {   //Implementation of abstract function. Returns itself if the ids line up. Otherwise, it returns null.
    if(id.equals(this.getId()))
      return this;
    else
      return null;
  }

  @Override
  public ArrayList<Space> getSpaces() {   //Implementation of abstract function. Returns an ArrayList of itself, as it is the only space contained within itself.
    return new ArrayList<>(Arrays.asList(this));
  }

}
