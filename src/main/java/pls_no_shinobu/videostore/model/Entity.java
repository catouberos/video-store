/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.model;

/**
 * Abstract classs for building an entity, which requires an ID that can be extended
 *
 * @author Do Khoa Nguyen
 */
public abstract class Entity {
    private String id;

    /**
     * Constructor for setting the default value of {@link Entity}
     *
     * @author Do Khoa Nguyen
     */
    public Entity() {
        this.id = "";
    }

    /**
     * Constructor for an {@link Entity} with ID
     *
     * @author Do Khoa Nguyen
     */
    public Entity(String id) {
        setId(id);
    }

    /**
     * Method for setting the {@link Entity}'s ID
     *
     * @author Do Khoa Nguyen
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method to get the {@link Entity} ID
     *
     * @author Do Khoa Nguyen
     */
    public String getId() {
        return id;
    }
}
