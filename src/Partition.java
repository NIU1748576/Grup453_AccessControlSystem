package baseNoStates;

import java.util.ArrayList;

public class Partition extends Area {
  private final ArrayList<Area> children = new ArrayList<>();

  public Partition(String id, Area parent) {
    super(id, parent);
  }

  public void addChild(Area area) {
    children.add(area);
  }

  @Override
  public ArrayList<Door> getDoorsGivingAccess() {
    ArrayList<Door> doors = new ArrayList<>();
    for (Area child : children) {
      doors.addAll(child.getDoorsGivingAccess());
    }
    return doors;
  }

  @Override
  public Area findAreaById(String id) {
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
    ArrayList<Space> spaces = new ArrayList<>();
    for (Area child : children) {
      spaces.addAll(child.getSpaces());
    }
    return spaces;
  }

}
