/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.model;

import java.time.Instant;
import java.util.UUID;

/**
 * Class for building a transaction object, use for storing transaction data such as the {@link
 * User}, {@link Item}, its status and create-resolve timestamp in {@link Instant}
 *
 * @author Do Khoa Nguyen
 * @see pls_no_shinobu.videostore.model.Entity
 */
public class Transaction extends Entity {
    private final User user;
    private final Item item;
    private boolean resolved;
    private final Instant createdTimestamp;
    private Instant resolvedTimestamp;

    /**
     * Constructor for making a new {@link Transaction}
     *
     * @param user {@link User} who is making the transaction
     * @param item {@link Item} the user is making the transaction on
     * @author Do Khoa Nguyen
     */
    public Transaction(User user, Item item) {
        super(UUID.randomUUID().toString());
        this.user = user;
        this.item = item;
        this.resolved = false;
        this.createdTimestamp = java.time.Instant.now();
    }

    /**
     * Method to get the {@link Transaction} item
     *
     * @author Do Khoa Nguyen
     */
    public Item getItem() {
        return item;
    }

    /**
     * Method to get the {@link Transaction} user
     *
     * @author Do Khoa Nguyen
     */
    public User getUser() {
        return user;
    }

    /**
     * Method to get the timestamp when the {@link Transaction} is created
     *
     * @author Do Khoa Nguyen
     */
    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    /**
     * Method to get the timestamp when the {@link Transaction} is resolved
     *
     * @return {@link Instant}, or null when the transaction is not resolved
     * @author Do Khoa Nguyen
     */
    public Instant getResolvedTimestamp() {
        return resolvedTimestamp;
    }

    /**
     * Method to get the status of the {@link Transaction}
     *
     * @author Do Khoa Nguyen
     */
    public boolean isResolved() {
        return resolved;
    }

    /**
     * Method to set the status of the {@link Transaction}, which then set the {@link
     * #resolvedTimestamp}
     *
     * @author Do Khoa Nguyen
     */
    public void setResolved() throws IllegalArgumentException {
        if (isResolved())
            throw new IllegalArgumentException("This transaction was already resolved");

        resolvedTimestamp = java.time.Instant.now();
        resolved = true;
    }

    /**
     * Method to get the {@link Transaction} informations into String
     *
     * @author Do Khoa Nguyen
     */
    @Override
    public String toString() {
        return "Transaction:\n"
                + "- User: "
                + getUser().getUsername()
                + "\n"
                + "- Item: "
               + getItem().getTitle()
               + "\n"
               + "- Created: "
               + getCreatedTimestamp().toString()
                + "\n"
                + "- Status: "
               + (isResolved() ? "Resolved"
                                 + "\n"
                                 + "- Resolved: "
                                 + getResolvedTimestamp().toString()
               : "Pending");
    }
}
