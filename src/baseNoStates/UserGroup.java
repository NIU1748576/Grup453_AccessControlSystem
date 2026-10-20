package baseNoStates;

import java.util.ArrayList;

public class UserGroup {                                           
    // Represents a group of users sharing common privileges and access rules. 
    // Instead of hardcoding permissions, schedules, or restrictions for every single user individually, 
    // we use this class to assign rules to an entire group at once and then we assign users to the groups.
    
    private final String name;
    private final ArrayList<User> users = new ArrayList<>(); // List of user objects belonging to this group

    public UserGroup(ArrayList<String> userList, String name) {  
        //Class constructor. Uses an ArrayList of ids to find the users and build its User object list.
        for(String id : userList)
        {
            User user = DirectoryUsers.findUserByCredential(id);
            if (!users.contains(user)) {
                users.add(user);
            }
        }
        this.name = name;
    }

    public User findUserByCredential(String credential) 
    //Searches for a user within this specific group using their unique credential. 
    //Returns the User object if found, otherwise null.
    {
        for (User user : users) {
            if (user.getCredential().equals(credential)) {
                return user;
            }
        }
        return null; // otherwise we get a Java error
    }

    //Class getters
    public String getName() { return name;}
    public ArrayList<String> getUsers() {  
        //Returns and ArrayList of User NAMES (not IDs).
        ArrayList<String> out = new ArrayList<>();
        for(User u : users){
            out.add(u.getName());
        }
        return out;
    }
}
