package baseNoStates;

import java.util.ArrayList;

public class UserGroup {                                           //Class UserGroup: It's used to classify users into different groups with different privileges each.
    private final String name;
    private final ArrayList<User> users = new ArrayList<>();

    public UserGroup(ArrayList<String> userList, String name) {  //Class constructor. Uses an ArrayList of ids to build its User object list.
        for(String id : userList)
        {
            User user = DirectoryUsers.findUserByCredential(id);
            if (!users.contains(user)) {
                users.add(user);
            }
        }
        this.name = name;
    }

    public User findUserByCredential(String credential) //Looks for a user in the UserGroup and returns it. Returns null if not found.
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
    public ArrayList<String> getUsers() {  //Returns and ArrayList of User NAMES (not IDs).
        ArrayList<String> out = new ArrayList<>();
        for(User u : users){
            out.add(u.getName());
        }
        return out;
    }
}
