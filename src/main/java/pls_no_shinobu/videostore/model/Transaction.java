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

public class Transaction extends Entity {
    private User user;
    private Item item;
    private boolean resolved;
    private Instant createdTimestamp;
    private Instant resolvedTimestamp;

    public Transaction(User user, Item item) {
        super(UUID.randomUUID().toString());
        this.user = user;
        this.item = item;
        this.resolved = false;
        this.createdTimestamp = java.time.Instant.now();
    }

    public Item getItem() {
        return item;
    }

    public void setResolved() {
        resolved = true;
    }
}
