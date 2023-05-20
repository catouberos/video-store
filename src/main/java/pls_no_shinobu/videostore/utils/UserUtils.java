package pls_no_shinobu.videostore.utils;

import pls_no_shinobu.videostore.model.Item;
import pls_no_shinobu.videostore.model.User;

import java.util.ArrayList;

/**
 * Utilities for parsing, and serialize {@link User} object into CSV-compatible file
 *
 * @author Do Khoa Nguyen
 */
public class UserUtils implements ObjectUtils<User> {
    /**
     * Method to parse an {@link User} from CSV-like file. <br>
     * File format: {@code id,name,address,phone,no_of_rental,type,username,password}
     *
     * @author Do Khoa Nguyen
     */
    public User parse(String str) {
        try {
            String[] tokens = str.split("\\s*,\\s*");

            String id = tokens[0];
            String name = tokens[1];
            String address = tokens[2];
            String phone = tokens[3];
            int rentalCount = Integer.parseInt(tokens[4]);
            User.UserType role = parseUserType(tokens[5]);
            String username = tokens[6];
            String password = tokens[7];
            
            return new User(id, name, address, phone, rentalCount, role, username, password);
        } catch (Exception e) {
            // TODO: handle exception
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
        return String.format(
                "%s,%s,%s,%s,%d,%s,%s,%s",
                user.getId(),
                user.getName(),
                user.getAddress(),
                user.getPhone(),
                user.getRentalCount(),
                user.getRole().toString(),
                user.getUsername(),
                user.getPassword());
    }
    
    public static User.UserType parseUserType(String str) {
        if (str.equalsIgnoreCase("guest")) return User.UserType.GUEST;
        if (str.equalsIgnoreCase("regular")) return User.UserType.REGULAR;
        if (str.equalsIgnoreCase("vip")) return User.UserType.VIP;
        if (str.equalsIgnoreCase("admin")) return User.UserType.ADMIN;

        throw new IllegalArgumentException("Input loan type doesn't match any enum");
    }
}