/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.model;

import pls_no_shinobu.videostore.errors.OutOfStockException;

public class Item extends Entity {
    public enum RentalType {
        RECORD,
        DVD,
        GAME
    }

    public enum LoanType {
        TWO_DAY,
        ONE_WEEK
    }

    private String title;
    private String genre;
    private RentalType rentalType;
    private LoanType loanType;
    private int stock;
    private float rentalFee;

    public Item() {
        super();
        this.title = "";
        this.genre = "";
        this.rentalType = RentalType.RECORD;
        this.loanType = LoanType.ONE_WEEK;
        this.stock = 0;
        this.rentalFee = 0;
    }

    public Item(
            String id,
            String title,
            String genre,
            RentalType rentalType,
            LoanType loanType,
            int stock,
            float rentalFee)
            throws IllegalArgumentException {
        setId(id);
        this.title = title;
        this.genre = genre;
        this.rentalType = rentalType;
        this.loanType = loanType;
        this.stock = stock;
        this.rentalFee = rentalFee;
    }

    public void setId(String id) throws IllegalArgumentException {
        if (!id.matches("^I\\d{3}-\\d{1,4}$")) {
            throw new IllegalArgumentException();
        }

        super.setId(id);
    }

    public String getTitle() {
        return title;
    }

    public boolean setTitle(String title) {
        this.title = title;

        return true;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

    public boolean setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;

        return true;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public boolean setLoanType(LoanType loanType) {
        this.loanType = loanType;

        return true;
    }

    public int getStock() {
        return stock;
    }

    public boolean setStock(int stock) {
        if (stock < 0) return false;

        this.stock = stock;

        return true;
    }

    public boolean inStock() {
        return stock > 0;
    }

    public void increaseStock() {
        stock++;
    }

    public void decreaseStock() throws OutOfStockException {
        if (!inStock()) throw new OutOfStockException("Item is out of stock");

        stock--;
    }

    public float getRentalFee() {
        return rentalFee;
    }

    public boolean setRentalFee(float rentalFee) {
        if (rentalFee < 0) {
            return false;
        }

        this.rentalFee = rentalFee;

        return true;
    }

    public String getGenre() {
        return genre;
    }

    public boolean setGenre(String genre) {
        this.genre = genre;

        return true;
    }
}
