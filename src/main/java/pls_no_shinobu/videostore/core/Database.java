/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.core;

import pls_no_shinobu.videostore.manager.ItemManager;
import pls_no_shinobu.videostore.manager.TransactionManager;
import pls_no_shinobu.videostore.manager.UserManager;

import java.io.FileNotFoundException;

/**
 * Database interface is used to implement CRUD operations onto any database that the application
 * chooses, either JSON, a back-end SQL database or even into plain text.
 *
 * @author Do Khoa Nguyen
 */
public interface Database {
    // create from persistent storage
    void createItems() throws NullPointerException, FileNotFoundException;

    void createUsers() throws NullPointerException, FileNotFoundException;

    void createTransactions() throws NullPointerException, FileNotFoundException;

    // get from creation
    ItemManager getItems();

    UserManager getUsers();

    TransactionManager getTransactions();

    // update to persistent storage
    void updateItems();

    void updateUsers();

    void updateTransactions();
}
