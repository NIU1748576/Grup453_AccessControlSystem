package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class Area {         //Abstract class meant to represent a room/group of rooms. It can either be a space or a partition through inheritance. Part of a composite design pattern along with DirectoryAreas.
  private final String id;
  private final Area parent;

  public Area(String id, Area parent) {     //Class constructor. Assigns the area's id and it's parent.
    this.id = id;
    this.parent = parent;
  }

  public String getId() {
    return id;
  }   //Returns the area's id.

  public Area getParent() {    //Returns the area's parent area (unless it's rootArea, in which case it returns null).
    return parent;
  }

  public abstract ArrayList<Door> getDoorsGivingAccess();      //Abstract function. Returns ArrayList of doors giving access to the area.
  public abstract Area findAreaById(String id);     //Abstract function. Returns Area object given its id.
  public abstract ArrayList<Space> getSpaces();     //Abstract function. Returns an ArrayList of the spaces contained in the area. If area is a space, it returns an ArrayList of itself.
}
