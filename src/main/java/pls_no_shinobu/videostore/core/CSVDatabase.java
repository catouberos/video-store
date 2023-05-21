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
import pls_no_shinobu.videostore.utils.FileIOUtils;

import java.io.FileNotFoundException;

public final class CSVDatabase implements Database {
    private static CSVDatabase INSTANCE;

    ItemManager itemManager;
    UserManager userManager;
    TransactionManager transactionManager;

    FileIOUtils fileIOUtils;

    private CSVDatabase() throws NullPointerException, FileNotFoundException {
        fileIOUtils = new FileIOUtils();
        createItems();
        createUsers();
    }

    public static CSVDatabase getInstance() throws NullPointerException, FileNotFoundException {
        if (INSTANCE == null) {
            INSTANCE = new CSVDatabase();
        }

        return INSTANCE;
    }

    @Override
    public void createItems() throws NullPointerException, FileNotFoundException {
        itemManager = new ItemManager(fileIOUtils.parseItem());
    }

    @Override
    public void createUsers() throws NullPointerException, FileNotFoundException {
        userManager = new UserManager(fileIOUtils.parseUser(itemManager));
    }

    // TODO: implement
    @Override
    public void createTransactions() {}

    @Override
    public ItemManager getItems() {
        return itemManager;
    }

    @Override
    public UserManager getUsers() {
        return userManager;
    }

    @Override
    public TransactionManager getTransactions() {
        return transactionManager;
    }

    @Override
    public void updateItems() {
        fileIOUtils.serializeItems(itemManager.getEntities());
    }

    @Override
    public void updateUsers() {
        fileIOUtils.serializeUsers(userManager.getEntities());
    }

    @Override
    public void updateTransactions() {}
}
