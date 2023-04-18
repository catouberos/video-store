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
 * informations. <br>
 * This manager doesn't implements {@link Manager} due to its nature of using {@link java.util.UUID}
 * instead of {@link Integer}-formatted iD
 *
 * @see pls_no_shinobu.videostore.manager.AbstractManager
 * @author Do Khoa Nguyen
 */
public class TransactionManager extends AbstractManager<Transaction> {
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
