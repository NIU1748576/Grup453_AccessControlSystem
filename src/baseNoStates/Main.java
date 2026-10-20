package baseNoStates;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

public class Main {
  public static void main(String[] args) {
    DirectoryAreas.makeArea(); // First we create the areas of the building
    DirectoryUsers.makeUsers(); // Then we create the users
    DirectoryUserGroups.makeUserGroups(); // The users are assigned to their respective groups
    new WebServer();
  }
}
