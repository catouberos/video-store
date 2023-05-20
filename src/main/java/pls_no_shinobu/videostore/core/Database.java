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

import java.net.URL;

/**
 * Database interface is used to implement CRUD operations onto any database that the application
 * chooses, either JSON, a back-end SQL database or even into plain text.
 *
 * @author Do Khoa Nguyen
 */
public interface Database {
    // create
    void setItems(URL file);

    void setUsers(URL file);

    void setInteractions(URL file);

    // read
    ItemManager getItems();

    UserManager getUsers();

    TransactionManager getTransactions();
}
