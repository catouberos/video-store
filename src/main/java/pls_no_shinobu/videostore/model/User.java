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
    private ArrayList<Item> rentals = new ArrayList<>();

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
            UserType role,
            String username,
            String password,
            ArrayList<Item> rentals)
            throws IllegalArgumentException {
        setId(id);
        setName(name);
        setAddress(address);
        setPhone(phone);
        setRentalCount(rentalCount);
        setRole(role);
        setUsername(username);
        setPassword(password);
        setRentals(rentals);
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

    public void setName(String name) throws IllegalArgumentException {
        if (getName().contains(name)) throw new IllegalArgumentException("Name is unchanged");

        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws IllegalArgumentException {
        if (getAddress().contains(address))
            throw new IllegalArgumentException("Address is unchanged");

        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws IllegalArgumentException {
        if (getPhone().contains(phone))
            throw new IllegalArgumentException("Phone number is unchanged");

        this.phone = phone;
    }

    public int getRentalCount() {
        return rentalCount;
    }

    public void setRentalCount(int rentalCount) {
        if (getRentalCount() < 0)
            throw new IllegalArgumentException("Rental count cannot be negative");

        this.rentalCount = rentalCount;
    }

    public int increateRentalCount() {
        this.rentalCount++;

        return rentalCount;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
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

    public ArrayList<Item> getRentals() {
        return rentals;
    }

    public void setRentals(ArrayList<Item> rentals) {
        this.rentals = rentals;
    }

    public void addRental(Item item) {
        // TODO: add stock check and stock management

        this.rentals.add(item);

        increateRentalCount();
    }

    public boolean removeRental(Item item) {
        // TODO: stock management
        if (!this.rentals.contains(item)) return false;

        this.rentals.remove(item);

        return true;
    }
}
