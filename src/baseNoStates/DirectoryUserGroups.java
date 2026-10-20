package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

public class DirectoryUserGroups {       
    //Utility class designated to instantiate and manage all user groups (employees, managers, admin and blank) that classify users and dictate privileges.
    private static ArrayList<UserGroup> userGroups; // List of all system user groups

    public static void makeUserGroups() {  
        // Initializes all UserGroups by passing its users' credentails and group names, then stores them in the list of userGroups.
        UserGroup blank = new UserGroup(new ArrayList<>(Arrays.asList("12345","77532")), "blank");
        UserGroup employees = new UserGroup(new ArrayList<>(Arrays.asList("74984","43295")), "employees");
        UserGroup managers = new UserGroup(new ArrayList<>(Arrays.asList("95783","05827")), "managers");
        UserGroup admin = new UserGroup(new ArrayList<>(Arrays.asList("11343")), "admin");

        userGroups = new ArrayList<>(Arrays.asList(blank,employees,managers,admin));
    }
// Commented because it's not being used, it is being used the one in DirectoryUsers
//    public static User findUserByCredential(String credential) {
//        for (UserGroup ug : userGroups) {
//            User found = ug.findUserByCredential(credential);
//            if (found != null) {
//                return found;
//            }
//        }
//        System.out.println("user with credential " + credential + " not found");
//        return null;
//    }

    public static ArrayList<UserGroup> getUserGroups() {  
        // Returns the complete list of all active user groups in the system 
        // to find which user group does the user belong.
        return userGroups;
    }
}
