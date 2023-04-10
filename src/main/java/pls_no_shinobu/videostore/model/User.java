/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.model;

import java.util.ArrayList;

public class User extends Entity {
    public enum UserType {
        GUEST,
        REGULAR,
        VIP,
        ADMIN
    }

    private String name;
    private String address;
    private String phone;
    private int rentalCount;
    private UserType role;
    private String username;
    private String password;
    private ArrayList<String> rentals = new ArrayList<>();

    public User() {
        super();
        this.name = "";
        this.address = "";
        this.phone = "";
        this.rentalCount = 0;
        this.role = UserType.GUEST;
        this.username = "";
        this.password = "";
    }

    public User(String id, String username, String password) throws IllegalArgumentException {
        setId(id);
        setUsername(username);
        setPassword(password);
    }

    public User(
            String id,
            String name,
            String address,
            String phone,
            int rentalCount,
            UserType userType,
            String username,
            String password,
            ArrayList<String> rentals)
            throws IllegalArgumentException
    {
        setId(id);
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.rentalCount = rentalCount;
        this.role = userType;
        setUsername(username);
        setPassword(password);
        this.rentals = rentals;
    }

    @Override
    public void setId(String id) throws IllegalArgumentException {
        if (!id.matches("^C\\d{3}$")) {
            throw new IllegalArgumentException("Invalid user ID format");
        }

        super.setId(id);
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        this.name = name;

        return true;
    }

    public String getAddress() {
        return address;
    }

    public boolean setAddress(String address) {
        this.address = address;

        return true;
    }

    public String getPhone() {
        return phone;
    }

    public boolean setPhone(String phone) {
        this.phone = phone;

        return true;
    }

    public int getRentalCount() {
        return rentalCount;
    }

    public boolean setRentalCount(int rentalCount) {
        this.rentalCount = rentalCount;

        return true;
    }

    public int increateRentalCount() {
        this.rentalCount++;

        return rentalCount;
    }

    public UserType getRole() {
        return role;
    }

    public boolean setRole(UserType role) {
        this.role = role;

        return true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws IllegalArgumentException {
        if (username.isBlank()) throw new IllegalArgumentException("Username cannot be empty");

        if (username.contains(" "))
            throw new IllegalArgumentException("Username cannot contain spaces");

        this.username = username;
    }

    protected String getPassword() {
        return password;
    }

    protected void setPassword(String password) throws IllegalArgumentException {
        if (password.isEmpty()) throw new IllegalArgumentException("Password cannot be empty");

        this.password = password;
    }

    public ArrayList<String> getRentals() {
        return rentals;
    }

    public boolean setRentals(ArrayList<String> rentals) {
        this.rentals = rentals;

        return true;
    }

    public boolean addRental(String rId) {
        // TODO: add stock check and stock management

            this.rentals.add(rId);

            increateRentalCount();

            return true;
        }



    public boolean removeRental(String rId) {
        // TODO: stock management
        if (!this.rentals.contains(rId)) return false;

        this.rentals.remove(rId);

        return true;
    }

    private boolean isRented(Item item) {
        for (String i : rentals)
            if (i.equals(item.getId())) return true;

        return false;
    }

    private boolean canRent(Item item) throws IllegalArgumentException {
        if (isRented(item)) throw new IllegalArgumentException("This item is already rented by this account");

        if (!item.isInStock()) throw new IllegalArgumentException("This item is out-of-stock");

        if (this.getRole().equals(UserType.REGULAR) || this.getRole().equals(UserType.VIP)){
            return true;
        }

        if (this.rentals.size() >= 2) throw new IllegalArgumentException("You can't rent more than 2 items at a time");


        if (!item.getLoanType().equals(Item.LoanType.TWO_DAY)) throw new IllegalArgumentException(" You can't rent this item for 2 days");

        return true;
    }

}
