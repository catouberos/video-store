/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.manager;

import pls_no_shinobu.videostore.errors.DuplicateException;
import pls_no_shinobu.videostore.model.Transaction;

import java.util.ArrayList;

/**
 * Class for manage multiple {@link Transaction}, in which we can create, delete or to provide
 * informations.
 *
 * @see pls_no_shinobu.videostore.manager.Manager
 * @author Do Khoa Nguyen
 */
public class TransactionManager extends Manager<Transaction> {
    /**
     * Constructor for initialize a {@link TransactionManager}
     *
     * @author Do Khoa Nguyen
     */
    public TransactionManager() {
        super();
    }

    /**
     * Constructor for initialize a {@link TransactionManager}, while adding {@link Transaction}
     * into the class
     *
     * @author Do Khoa Nguyen
     */
    public TransactionManager(ArrayList<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            try {
                add(transaction);
            } catch (DuplicateException e) {
                // TODO: log system
                System.out.println("Cannot add transaction " + transaction.getId() + " to manager");
            }
        }
    }
}
