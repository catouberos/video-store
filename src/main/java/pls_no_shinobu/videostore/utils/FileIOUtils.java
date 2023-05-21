/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.utils;

import pls_no_shinobu.videostore.manager.ItemManager;
import pls_no_shinobu.videostore.model.Item;
import pls_no_shinobu.videostore.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIOUtils {
    public ArrayList<Item> parseItem() throws NullPointerException, FileNotFoundException {
        ItemUtils utils = new ItemUtils();
        ArrayList<Item> items = new ArrayList<>();

        // TODO: dynamically set file src
        File itemsFile = new File(System.getProperty("user.dir") + "/data/items.csv");
        Scanner scanner = new Scanner(itemsFile);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (!line.startsWith("#")) {
                Item item = utils.parse(line);

                items.add(item);
            }
        }

        return items;
    }

    public ArrayList<User> parseUser(ItemManager itemManager)
            throws NullPointerException, FileNotFoundException {
        UserUtils utils = new UserUtils();
        ArrayList<User> users = new ArrayList<>();

        // TODO: dynamically set file src
        File usersFile = new File(System.getProperty("user.dir") + "/data/users.csv");
        Scanner scanner = new Scanner(usersFile);

        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();

            if (!str.startsWith("#")) {
                User user = utils.parse(str, itemManager);

                users.add(user);
            }
        }

        return users;
    }

    public boolean serializeItems(ArrayList<Item> items) {
        ItemUtils utils = new ItemUtils();
        try {
            // TODO: dynamically set file src
            File itemsFile = new File(System.getProperty("user.dir") + "/data/items.csv");

            PrintWriter writer = new PrintWriter(itemsFile);

            for (Item item : items) {
                writer.println(utils.serialize(item));
            }

            writer.close();
        } catch (Exception e) {
            System.err.println("Something went wrong while serialize items to file.");
            e.printStackTrace();

            return false;
        }

        return true;
    }

    public boolean serializeUsers(ArrayList<User> users) {
        UserUtils utils = new UserUtils();

        try {
            // TODO: dynamically set file src
            File itemsFile = new File(System.getProperty("user.dir") + "/data/users.csv");

            PrintWriter writer = new PrintWriter(itemsFile);

            for (User user : users) {
                writer.println(utils.serialize(user));
            }

            writer.close();
        } catch (Exception e) {
            System.err.println("Something went wrong while serialize users to file.");
            e.printStackTrace();

            return false;
        }

        return true;
    }
}
