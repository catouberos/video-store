/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.utils;

import pls_no_shinobu.videostore.model.Item;
import pls_no_shinobu.videostore.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIOUtils {
    public ArrayList<Item> parseItem() {
        ItemUtils utils = new ItemUtils();
        ArrayList<Item> items = new ArrayList<>();

        try {
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
        } catch (Exception e) {
            // TODO: do something
        }

        return items;
    }

    public ArrayList<User> parseUser() {
        return null;
    }
}
