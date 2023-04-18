/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.manager;

import pls_no_shinobu.videostore.errors.ManagerLimitException;
import pls_no_shinobu.videostore.model.Entity;

/**
 * Interface with methods for implementing an {@link Entity} manager
 *
 * @see AbstractManager
 * @author Do Khoa Nguyen
 */
public interface Manager {
    /**
     * Method for getting unused (free) ID, which could be use to create a new {@link Entity}
     *
     * @author Do Khoa Nguyen
     */
    String getUnusedID() throws ManagerLimitException;
}
