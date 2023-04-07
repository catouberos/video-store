/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.model;

import java.util.HashSet;

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

    public enum RentalStatus {
        BORROWED,
        AVAILABLE
    }

    private String title;
    private String genre;
    private RentalType rentalType;
    private LoanType loanType;
    private int stock;
    private float rentalFee;
    private RentalStatus rentalStatus;

    public Item() {
        super();
        this.title = "";
        this.genre = "";
        this.rentalType = RentalType.RECORD;
        this.loanType = LoanType.ONE_WEEK;
        this.stock = 0;
        this.rentalFee = 0;
        this.rentalStatus = RentalStatus.AVAILABLE;
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

    public Item(
        String id,
        String title,
        String genre,
        RentalType rentalType,
        LoanType loanType,
        int stock,
        float rentalFee,
        RentalStatus rentalStatus)
            throws IllegalArgumentException {
        setId(id);
        this.title = title;
        this.genre = genre;
        this.rentalType = rentalType;
        this.loanType = loanType;
        this.stock = stock;
        this.rentalFee = rentalFee;
        this.rentalStatus = rentalStatus;
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
        // Stock must be larger than 0
        if (stock < 0) {
            return false;
        }

        this.stock = stock;

        return true;
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

    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public boolean setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;

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
