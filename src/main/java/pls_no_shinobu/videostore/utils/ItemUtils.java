/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.utils;

import pls_no_shinobu.videostore.model.Item;

/**
 * Utilities for parsing, and serialize Item object into CSV-compatible file
 *
 * @author Do Khoa Nguyen
 */
public class ItemUtils {
    /**
     * Method to parse an {@link Item} from CSV-like file. <br>
     * File format: {@code id,title,rentType,loanType,stock,rentalFee[,genre]}
     *
     * @author Do Khoa Nguyen
     */
    public Item parse(String str) {
        try {
            String[] tokens = str.split("\\s*,\\s*");

            String id = tokens[0];
            String title = tokens[1];
            Item.RentalType rentalType = parseRentalType(tokens[2]);
            Item.LoanType loanType = parseLoanType(tokens[3]);
            int stock = Integer.parseInt(tokens[4]);
            float rentalFee = Float.parseFloat(tokens[5]);
            String genre = tokens.length == 7 ? tokens[6] : null;

            return new Item(id, title, genre, rentalType, loanType, stock, rentalFee);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    /**
     * Method to serialize an {@link Item} to CSV-compatible file. <br>
     * File format: {@code id,title,rentType,loanType,stock,rentalFee[,genre]}
     *
     * @author Do Khoa Nguyen
     */
    public String serialize(Item item) {
        if (item.getGenre() == null)
            return String.format(
                    "%s,%s,%s,%s,%d,%.2f",
                    item.getId(),
                    item.getTitle(),
                    item.getRentalType().toString(),
                    item.getLoanType().toString(),
                    item.getStock(),
                    item.getRentalFee());

        return String.format(
                "%s,%s,%s,%s,%d,%.2f,%s",
                item.getId(),
                item.getTitle(),
                item.getRentalType().toString(),
                item.getLoanType().toString(),
                item.getStock(),
                item.getRentalFee(),
                item.getGenre());
    }

    /**
     * Method to parse {@link String} of rental type into {@link Item.RentalType} enum
     *
     * @author Do Khoa Nguyen
     */
    public Item.RentalType parseRentalType(String str) throws IllegalArgumentException {
        if (str.equalsIgnoreCase("game")) return Item.RentalType.GAME;
        if (str.equalsIgnoreCase("record")) return Item.RentalType.RECORD;
        if (str.equalsIgnoreCase("dvd")) return Item.RentalType.DVD;

        throw new IllegalArgumentException("Input rental type doesn't match any enum");
    }

    /**
     * Method to parse {@link String} of loan type into {@link Item.LoanType} enum
     *
     * @author Do Khoa Nguyen
     */
    public Item.LoanType parseLoanType(String str) throws IllegalArgumentException {
        if (str.equalsIgnoreCase("2-day")) return Item.LoanType.TWO_DAY;
        if (str.equalsIgnoreCase("1-week")) return Item.LoanType.ONE_WEEK;

        throw new IllegalArgumentException("Input loan type doesn't match any enum");
    }
}
