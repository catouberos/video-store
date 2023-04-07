/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.utils;

import pls_no_shinobu.videostore.model.Item;

public class ItemUtils implements CoreUtils<Item> {
    @Override
    public Item parse(String str) {
        try {
            String[] tokens = str.split("\\s*,\\s*");

            String id = tokens[0];
            String title = tokens[1];
            Item.RentalType rentalType = parseRentalType(tokens[2]);
            Item.LoanType loanType = parseLoanType(tokens[3]);
            int stock = Integer.parseInt(tokens[4]);
            System.out.print(tokens[5] + Float.parseFloat(tokens[5]));
            float rentalFee = Float.parseFloat(tokens[5]);
            String genre = tokens.length == 7 ? tokens[6] : null;

            return new Item(id, title, genre, rentalType, loanType, stock, rentalFee);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public String serialize(Item item) {
        // TODO: serialize
        return null;
    }

    public Item.RentalType parseRentalType(String str) {
        if (str.equalsIgnoreCase("game")) return Item.RentalType.GAME;
        if (str.equalsIgnoreCase("record")) return Item.RentalType.RECORD;
        if (str.equalsIgnoreCase("dvd")) return Item.RentalType.DVD;

        return null;
    }

    public Item.LoanType parseLoanType(String str) {
        if (str.equalsIgnoreCase("2-day")) return Item.LoanType.TWO_DAY;
        if (str.equalsIgnoreCase("1-week")) return Item.LoanType.ONE_WEEK;

        return null;
    }
}
