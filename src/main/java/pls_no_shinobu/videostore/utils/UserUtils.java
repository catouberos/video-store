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

/**
 * Utilities for parsing, and serialize {@link User} object into CSV-compatible file
 *
 * @author Do Khoa Nguyen
 */
public class UserUtils {
    /**
     * Method to parse an {@link User} from CSV-like file. <br>
     * File format: {@code id,name,address,phone,no_of_rental,type,username,password,[rentals-ids,]}
     *
     * @author Do Khoa Nguyen
     */
    public User parse(String str, ItemManager itemManager) {
        // TODO: this is a kind-of dangerous way to parse a User, which should be improved given
        // that there's time.
        try {
            User user;

            // parse the information first
            String[] tokens = str.split("\\s*,\\s*");

            /*
            there should be an easier way to do with OpenCSV CsvBindByPosition
            https://opencsv.sourceforge.net/apidocs/com/opencsv/bean/CsvBindByPosition.html
            although this takes a minimal approach
             */
            String id = tokens[0];
            String name = tokens[1];
            String address = tokens[2];
            String phone = tokens[3];
            int rentalCount = Integer.parseInt(tokens[4]);
            User.UserType role = parseUserType(tokens[5]);
            String username = tokens[6];
            String password = tokens[7];

            // initialize a user
            user = new User(id, name, address, phone, rentalCount, role, username, password);

            // then parse the rentals
            if (tokens.length > 8) {
                for (int i = 8; i <= tokens.length - 1; i++) {
                    Item item = itemManager.getEntity(tokens[i]);

                    if (item != null) user.addRental(item);
                }
            }

            // return the user
            return user;
        } catch (Exception e) {
            // TODO: log

            return null;
        }
    }

    /**
     * Method to serialize an {@link User} to CSV-compatible file. <br>
     * File format: {@code id,name,address,phone,no_of_rental,type,username,password}
     *
     * @author Do Khoa Nguyen
     */
    public String serialize(User user) {
        String str =
                String.format(
                        "%s,%s,%s,%s,%d,%s,%s,%s",
                        user.getId() == null ? "" : user.getId(),
                        user.getName() == null ? "" : user.getName(),
                        user.getAddress() == null ? "" : user.getAddress(),
                        user.getPhone() == null ? "" : user.getPhone(),
                        user.getRentalCount(),
                        user.getRole().toString(),
                        user.getUsername(),
                        user.getPassword());

        for (Item item : user.getRentals()) {
            str = str.concat("," + item.getId());
        }

        return str;
    }

    public static User.UserType parseUserType(String str) {
        if (str.equalsIgnoreCase("guest")) return User.UserType.GUEST;
        if (str.equalsIgnoreCase("regular")) return User.UserType.REGULAR;
        if (str.equalsIgnoreCase("vip")) return User.UserType.VIP;
        if (str.equalsIgnoreCase("admin")) return User.UserType.ADMIN;

        throw new IllegalArgumentException("Input loan type doesn't match any enum");
    }
}
