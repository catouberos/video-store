/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.manager;

import pls_no_shinobu.videostore.errors.DuplicateException;
import pls_no_shinobu.videostore.errors.ManagerLimitException;
import pls_no_shinobu.videostore.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for manage multiple {@link User}, in which we can create, delete or to provide
 * informations.
 *
 * @see pls_no_shinobu.videostore.manager.AbstractManager
 * @see pls_no_shinobu.videostore.manager.Manager
 * @author Do Khoa Nguyen
 */
public class UserManager extends AbstractManager<User> implements Manager {
    /**
     * Constructor for initialize a {@link UserManager}
     *
     * @author Do Khoa Nguyen
     */
    public UserManager() {
        super();
    }

    /**
     * Constructor for initialize a {@link UserManager}, while adding {@link User} into the class
     *
     * @author Do Khoa Nguyen
     */
    public UserManager(ArrayList<User> users) {
        super();
        for (User user : users) {
            try {
                add(user);
            } catch (DuplicateException e) {
                // TODO: log system
                System.out.println(
                        "Cannot add user " + user.getId() + "/" + user.getName() + "to manager");
            }
        }
    }

    /**
     * Method for adding {@link User} into {@link UserManager}
     *
     * @author Do Khoa Nguyen
     */
    @Override
    public void add(User user) throws DuplicateException {
        if (!checkUsername(user))
            throw new DuplicateException("Cannot create user with taken username");
        super.add(user);
    }

    /**
     * Method for getting {@link User} in manager with filter for {@link User.UserType}
     *
     * @return {@link ArrayList} of type Entity
     * @author Do Khoa Nguyen
     */
    public ArrayList<User> getEntities(User.UserType type) {
        // get all entities of current list, then create a stream and filter out each user with
        // specified role, then collect into a new ArrayList
        return getEntities().stream()
                .filter((user) -> (user.getRole() == type))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Method for checking if a username is taken
     *
     * @return true if the username is available, false if not
     * @author Do Khoa Nguyen
     */
    public boolean checkUsername(User entity) {
        for (User user : getEntities()) {
            if (user.getUsername().contains(entity.getUsername())) return false;
        }

        return true;
    }

    /**
     * Method to get an unused user ID (if possible)
     *
     * @return a correct format ID {@code C\d{3}}
     * @author Do Khoa Nguyen
     */
    @Override
    public String getUnusedID() throws ManagerLimitException {
        // this algorithm can be further optimize

        // throw exception immediately if the manager size is 1000 (id from C000 - C999)
        if (getEntities().size() == 1000) throw new ManagerLimitException("The manager is full");

        // get all ID
        List<String> usedID = getEntities().stream().map(User::getId).toList();

        // find unused id, ascendingly
        for (int i = 0; i <= 999; i++) {
            if (!usedID.contains(String.format("C%03d", i))) return String.format("C%03d", i);
        }

        return null;
    }
}
