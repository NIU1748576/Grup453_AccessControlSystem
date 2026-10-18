package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

public class DirectoryUserGroups {       //Class designated to create all the UserGroups.
    private static ArrayList<UserGroup> userGroups;

    public static void makeUserGroups() {  //Main method. It creates all the UserGroups and stores them in an ArrayList.
        //Create UserGroups
        UserGroup blank = new UserGroup(new ArrayList<>(Arrays.asList("12345","77532")), "blank");
        UserGroup employees = new UserGroup(new ArrayList<>(Arrays.asList("74984","43295")), "employees");
        UserGroup managers = new UserGroup(new ArrayList<>(Arrays.asList("95783","05827")), "managers");
        UserGroup admin = new UserGroup(new ArrayList<>(Arrays.asList("11343")), "admin");

        //Add user groups to list
        userGroups = new ArrayList<>(Arrays.asList(blank,employees,managers,admin));
    }
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
        return userGroups;
    }  //Returns ArrayList of all userGroups.
}