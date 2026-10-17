package baseNoStates;

import java.util.ArrayList;

public class Space extends Area {
  private ArrayList<Door> doors = new ArrayList<>();

  public Space(String id, Area parent) {
    super(id, parent);
  }

  public void addDoorGivingAccess(Door door) {
    if (!doors.contains(door)) {
      doors.add(door);
    }
  }

  @Override
  public ArrayList<Door> getDoorsGivingAccess() {
    return new ArrayList<>(doors);
  }

  @Override
  public Area findAreaById(String id) {
    if(id.equals(this.getId())) {
      return this;
    }
    return null;
  }

  @Override
  public ArrayList<Space> getSpaces() {
    ArrayList<Space> space = new ArrayList<>();
    space.add(this);
    return space;
  }

}
