package baseNoStates;

import java.util.ArrayList;

public class Partition extends Area {
  // Represents a group of spaces or other partitions (a floor per example). Serves as the 'Composite' element in the Composite design pattern, 

  private final ArrayList<Area> children = new ArrayList<>();   //ArrayList of all the areas it contains.

  public Partition(String id, Area parent) { 
    // Constructor that initializes the partition's identity and its parent container via the superclass.
    super(id, parent);
  }

  public void addChild(Area area) {
    children.add(area);
  }   // Adds a child area (either a Space or another Partition) to the partition's children list.

  @Override
  public ArrayList<Door> getDoorsGivingAccess() {  
    //Implementation of abstract function. Returns all doors giving access to the itself (the instance's area).
    ArrayList<Door> doors = new ArrayList<>();
    for (Area child : children) {
      doors.addAll(child.getDoorsGivingAccess());
    }
    return doors;
  }

  @Override
  public Area findAreaById(String id) {    
    //Implementation of abstract function. Returns an Area object given its id. Looks through every child.
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
  public ArrayList<Space> getSpaces() {   
    //Implementation of abstract function. Returns an ArrayList of all Spaces contained in the children ArrayList.
    ArrayList<Space> spaces = new ArrayList<>();
    for (Area child : children) {
      spaces.addAll(child.getSpaces());
    }
    return spaces;
  }

}
