package baseNoStates;

import java.util.ArrayList;

public class Partition extends Area {         //Class that's meant to represent a group of spaces/other partitions (floor). Inheritance of the abstract class Area. 'Composite' element of the composite design pattern.
  private final ArrayList<Area> children = new ArrayList<>();   //ArrayList of all the areas it contains.

  public Partition(String id, Area parent) { //Class constructor. Summons parent class's constructor to initialize attributes.
    super(id, parent);
  }

  public void addChild(Area area) {
    children.add(area);
  }   //Adds a child (Area) to the children ArrayList.

  @Override
  public ArrayList<Door> getDoorsGivingAccess() {  //Implementation of abstract function. Returns all doors giving access to the itself (the instance's area).
    ArrayList<Door> doors = new ArrayList<>();
    for (Area child : children) {
      doors.addAll(child.getDoorsGivingAccess());
    }
    return doors;
  }

  @Override
  public Area findAreaById(String id) {    //Implementation of abstract function. Returns an Area object given its id. Looks through every child.
    if (this.getId().equals(id)) {
      return this;
    }
    for (Area child : children) {
      Area found = child.findAreaById(id);
      if (found != null) {
        return found;
      }
    }
    return null;
  }

  @Override
  public ArrayList<Space> getSpaces() {   //Implementation of abstract function. Returns an ArrayList of all Spaces contained in the children ArrayList.
    ArrayList<Space> spaces = new ArrayList<>();
    for (Area child : children) {
      spaces.addAll(child.getSpaces());
    }
    return spaces;
  }

}
