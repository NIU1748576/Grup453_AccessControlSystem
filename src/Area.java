package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class Area {
  private final String id;
  private final Area parent;

  public Area(String id, Area parent) {
    this.id = id;
    this.parent = parent;
  }

  public String getId() {
    return id;
  }

  public Area getParent() {
    return parent;
  }

  public abstract ArrayList<Door> getDoorsGivingAccess();
  public abstract Area findAreaById(String id);
  public abstract ArrayList<Space> getSpaces();
}
