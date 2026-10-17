package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

public final class DirectoryAreas {
  private static Area rootArea;
  private static ArrayList<Door> allDoors;

  public static void makeArea() {
    // PARTITIONS
    Partition building = new Partition("building", null);
    Partition basement = new Partition("basement", building);
    Partition groundFloor = new Partition("ground_floor", building);
    Partition floor1 = new Partition("floor1", building);
    building.addChild(basement);
    building.addChild(groundFloor);
    building.addChild(floor1);

    // SPACES
    Space parking = new Space("parking", basement);
    Space hall = new Space("hall", groundFloor);
    Space room1 = new Space("room1", groundFloor);
    Space room2 = new Space("room2", groundFloor);
    Space room3 = new Space("room3", floor1);
    Space corridor = new Space("corridor", floor1);
    Space it = new Space("IT", floor1);
    Space stairs = new Space("stairs", building);
    Space exterior = new Space("exterior", building);
    basement.addChild(parking);
    groundFloor.addChild(hall);
    groundFloor.addChild(room1);
    groundFloor.addChild(room2);
    floor1.addChild(room3);
    floor1.addChild(corridor);
    floor1.addChild(it);
    building.addChild(stairs);
    building.addChild(exterior);

    // DOORS
    // parking
    Door d1 = new Door("D1", exterior, parking);
    Door d2 = new Door("D2", stairs, parking);

    // ground_floor
    Door d3 = new Door("D3", exterior, hall);
    Door d4 = new Door("D4", stairs, hall);
    Door d5 = new Door("D5", room1, hall);
    Door d6 = new Door("D6", room2, hall);

    // floor1
    Door d7 = new Door("D7", stairs, corridor);
    Door d8 = new Door("D8", room3, corridor);
    Door d9 = new Door("D9", it, corridor);

    setAllDoors(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9)));
    rootArea = building;
  }

  public static void setAllDoors(ArrayList<Door> doors) {
    allDoors = doors;
  }

  public static Area findAreaById(String id) {
    return rootArea.findAreaById(id); // an Area or null if not found
  }

  public static Door findDoorById(String id) {
    for (Door allDoor : allDoors) {
      if (allDoor.getId().equals(id)) {
        return allDoor;
      }
    }
    return null;
  }

  // this is needed by RequestRefresh
  public static ArrayList<Door> getAllDoors() {
    System.out.println(allDoors);
    return allDoors;
  }
}
