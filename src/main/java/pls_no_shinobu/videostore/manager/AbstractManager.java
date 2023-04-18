/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.manager;

import pls_no_shinobu.videostore.errors.DuplicateException;
import pls_no_shinobu.videostore.errors.NotFoundException;
import pls_no_shinobu.videostore.model.Entity;

import java.util.ArrayList;

/**
 * Abstract class for implementing {@link Entity} manager
 *
 * @see Manager
 * @author Do Khoa Nguyen
 */
public abstract class AbstractManager<T extends Entity> {
    private final ArrayList<T> entities;

    public AbstractManager() {
        this.entities = new ArrayList<>();
    }

    /**
     * Method for getting {@link Entity} in manager
     *
     * @return {@link ArrayList} of type Entity
     * @author Do Khoa Nguyen
     */
    public ArrayList<T> getEntities() {
        return new ArrayList<>(entities);
    }

    /**
     * Method for adding {@link Entity} to manager, which checks for duplicate ID
     *
     * @author Do Khoa Nguyen
     */
    public void add(T entity) throws DuplicateException {
        if (contains(entity)) throw new DuplicateException("Cannot create with taken ID");

        this.entities.add(entity);
    }

    /**
     * Method for removing {@link Entity} from manager, which checks for not-found entity
     *
     * @param entity an {@link Entity} object
     * @author Do Khoa Nguyen
     */
    public void remove(T entity) throws NotFoundException {
        if (!contains(entity)) throw new NotFoundException("Entity not found");

        this.entities.remove(entity);
    }

    /**
     * Method for removing {@link Entity} from manager, which checks for not-found entity
     *
     * @param id an {@link Entity}'s id
     * @author Do Khoa Nguyen
     */
    public void remove(String id) throws NotFoundException {
        if (!contains(id)) throw new NotFoundException("Entity not found");

        this.entities.remove(get(id));
    }

    /**
     * Method for getting a specific {@link Entity} from manager with its id
     *
     * @param id an {@link Entity}'s id
     * @author Do Khoa Nguyen
     */
    public T get(String id) throws NotFoundException {
        for (T entity : entities) {
            if (entity.getId().equals(id)) return entity;
        }

        throw new NotFoundException("Entity not found");
    }

    /**
     * Method for checking if {@link Entity}'s **id** is in Manager
     *
     * @param entity an {@link Entity} object
     * @author Do Khoa Nguyen
     */
    public boolean contains(T entity) {
        for (T checkEntity : entities) {
            if (checkEntity.getId().equals(entity.getId())) return true;
        }

        return false;
    }

    /**
     * Method for checking if {@link Entity}'s id is in manager
     *
     * @param id an {@link Entity}'s id
     * @author Do Khoa Nguyen
     */
    public boolean contains(String id) {
        for (T checkEntity : entities) {
            if (checkEntity.getId().equals(id)) return true;
        }

        return false;
    }
}
