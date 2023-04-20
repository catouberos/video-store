/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.model;

import pls_no_shinobu.videostore.errors.OutOfStockException;

/**
 * Class for building an item object, that will include the item details and the stock with its
 * methods
 *
 * @author Do Khoa Nguyen
 * @see pls_no_shinobu.videostore.model.Entity
 */
public class Item extends Entity {
    /** Enum for rental type */
    public enum RentalType {
        RECORD,
        DVD,
        GAME
    }

    /** Enum for loan type */
    public enum LoanType {
        TWO_DAY,
        ONE_WEEK;

        @Override
        public String toString() {
            if (this == TWO_DAY) return "2-day";
            if (this == ONE_WEEK) return "1-week";

            throw new IllegalArgumentException();
        }
    }

    private String title;
    private String genre;
    private RentalType rentalType;
    private LoanType loanType;
    private int stock;
    private float rentalFee;

    /**
     * Constructor for a bare-minimum item, which includes an id and title
     *
     * @author Do Khoa Nguyen
     */
    public Item(String id, String title) throws IllegalArgumentException {
        super();
        setId(id);
        this.title = title;
    }

    /**
     * Constructor for a full-blown item, normally to initiate the item when loading from persistant
     * data, such as a file
     *
     * @author Do Khoa Nguyen
     */
    public Item(
            String id,
            String title,
            String genre,
            RentalType rentalType,
            LoanType loanType,
            int stock,
            float rentalFee)
            throws IllegalArgumentException {
        super();
        setId(id);
        this.title = title;
        this.genre = genre;
        this.rentalType = rentalType;
        this.loanType = loanType;
        this.stock = stock;
        this.rentalFee = rentalFee;
    }

    /**
     * Method for setting {@link Item} id, with checks to ensure the correct format
     *
     * @author Do Khoa Nguyen
     */
    @Override
    public void setId(String id) throws IllegalArgumentException {
        if (!id.matches("^I\\d{3}-\\d{1,4}$"))
            throw new IllegalArgumentException("Invalid item ID format");

        super.setId(id);
    }

    /**
     * Method to get {@link Item} title (name)
     *
     * @author Do Khoa Nguyen
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method for setting {@link Item} title (name), with checks to ensure the title is different
     * before changes
     *
     * @author Tran The Quang Minh
     */
    public void setTitle(String title) throws IllegalArgumentException {
        if (getTitle().contains(title)) throw new IllegalArgumentException("Title is unchanged");

        this.title = title;
    }

    /**
     * Method to get {@link Item}'s rental type
     *
     * @author Do Khoa Nguyen
     */
    public RentalType getRentalType() {
        return rentalType;
    }

    /**
     * Method for setting {@link Item} rental type
     *
     * @author Do Khoa Nguyen
     */
    public void setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
    }

    /**
     * Method to get {@link Item}'s loan type
     *
     * @author Do Khoa Nguyen
     */
    public LoanType getLoanType() {
        return loanType;
    }

    /**
     * Method for setting {@link Item} loan type
     *
     * @author Do Khoa Nguyen
     */
    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    /**
     * Method to get {@link Item} stock
     *
     * @author Do Khoa Nguyen
     */
    public int getStock() {
        return stock;
    }

    /**
     * Method for setting {@link Item} stock
     *
     * @author Do Khoa Nguyen
     */
    public void setStock(int stock) throws IllegalArgumentException {
        if (stock < 0) throw new IllegalArgumentException("Stock cannot be negative");

        this.stock = stock;
    }

    /**
     * Method for checking if {@link Item} is in stock
     *
     * @return true if in stock, false if not
     * @author Do Khoa Nguyen
     */
    public boolean inStock() {
        return stock > 0;
    }

    /**
     * Method for increase an {@link Item} stock by one count
     *
     * @author Do Khoa Nguyen
     */
    public void increaseStock() {
        stock++;
    }

    /**
     * Method for increase an {@link Item} stock by number of count
     *
     * @author Do Khoa Nguyen
     */
    public void increaseStock(int count) {
        stock += count;
    }

    /**
     * Method for decrease an {@link Item} stock by one count
     *
     * @author Do Khoa Nguyen
     */
    public void decreaseStock() throws OutOfStockException {
        if (!inStock()) throw new OutOfStockException("Item is out of stock");

        stock--;
    }

    /**
     * Method for decrease an {@link Item} stock by number of count
     *
     * @author Do Khoa Nguyen
     */
    public void decreaseStock(int number) throws OutOfStockException, IllegalArgumentException {
        if (!inStock()) throw new OutOfStockException("Item is out of stock");

        if (stock - number < 0)
            throw new IllegalArgumentException("Stock cannot be negative after decrease");

        stock -= number;
    }

    /**
     * Method to get {@link Item} rental fee
     *
     * @author Do Khoa Nguyen
     */
    public float getRentalFee() {
        return rentalFee;
    }

    /**
     * Method for setting {@link Item} rental fee
     *
     * @author Do Khoa Nguyen
     */
    public void setRentalFee(float rentalFee) throws IllegalArgumentException {
        if (rentalFee < 0) throw new IllegalArgumentException("Rental fee cannot be negative");

        this.rentalFee = rentalFee;
    }

    /**
     * Method to get {@link Item} genre
     *
     * @author Do Khoa Nguyen
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Method for setting {@link Item} genre
     *
     * @author Do Khoa Nguyen
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return Integer.parseInt(getId().split("-")[1]);
    }
}
