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
    private ArrayList<String> rentals;

    public User() {
        super();
        this.name = "";
        this.address = "";
        this.phone = "";
        this.rentalCount = 0;
        this.role = UserType.GUEST;
        this.username = "";
        this.password = "";
        this.rentals = new ArrayList<>();
    }

    public User(String id, String username, String password) {
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
            throws IllegalArgumentException {
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

        if (username.contains("\\s"))
            throw new IllegalArgumentException("Username cannot contain spaces");

        this.username = username;
    }

    protected String getPassword() {
        return password;
    }

    protected void setPassword(String password) throws IllegalArgumentException {
        if (username.isEmpty()) throw new IllegalArgumentException("Password cannot be empty");

        this.password = password;
    }

    public ArrayList<String> getRentals() {
        return rentals;
    }

    public boolean setRentals(ArrayList<String> rentals) {
        this.rentals = rentals;

        return true;
    }

    public boolean addRentals(String rId) {
        // TODO: add stock check

        this.rentals.add(rId);

        return true;
    }

    public boolean removeRentals(String rId) {
        if (!this.rentals.contains(rId)) return false;

        this.rentals.remove(rId);

        return true;
    }
}
