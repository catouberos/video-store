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
import pls_no_shinobu.videostore.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for manage multiple {@link Item}, in which we can create, delete or to provide
 * information.
 *
 * @see pls_no_shinobu.videostore.manager.Manager
 * @author Do Khoa Nguyen
 */
public class ItemManager extends Manager<Item> {
    /**
     * Constructor for initialize a {@link ItemManager}
     *
     * @author Do Khoa Nguyen
     */
    public ItemManager() {
        super();
    }

    /**
     * Constructor for initialize a {@link ItemManager}, while adding {@link Item} into the class
     *
     * @author Do Khoa Nguyen
     */
    public ItemManager(ArrayList<Item> items) {
        super();
        for (Item item : items) {
            try {
                add(item);
            } catch (DuplicateException e) {
                // TODO: log system
                System.out.println(
                        "Cannot add item " + item.getId() + "/" + item.getTitle() + "to manager");
            }
        }
    }

    /**
     * Method to get {@link Item} from manager based on year
     *
     * @author Do Khoa Nguyen
     */
    public ArrayList<Item> getEntities(int year) {
        return getEntities().stream()
                .filter((item) -> (item.getYear() == year))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Method to get an unused user ID (if possible)
     *
     * @return a correct format ID {@code I\d{3}-\d{1,4}}
     * @author Do Khoa Nguyen
     */
    public String getUnusedID(int year) throws ManagerLimitException {
        // this algorithm can be further optimize

        // throw exception immediately if the manager size is 1000 (id from I000-{year} -
        // I999-{year})
        if (getEntities(year).size() == 1000)
            throw new ManagerLimitException("The manager is full");

        // get all ID
        List<String> usedID = getEntities(year).stream().map(Item::getId).toList();

        // find unused id, ascendingly
        for (int i = 0; i <= 999; i++) {
            if (!usedID.contains(String.format("I%03d-%d", i, year)))
                return String.format("I%03d-%d", i, year);
        }

        return null;
    }
}
