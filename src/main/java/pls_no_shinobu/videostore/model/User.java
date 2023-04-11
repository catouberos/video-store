/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.model;

import pls_no_shinobu.videostore.errors.OutOfStockException;
import pls_no_shinobu.videostore.errors.RentLimitException;
import pls_no_shinobu.videostore.utils.PasswordUtils;

import java.util.ArrayList;

/**
 * Class for building a user object, that will include basic informations, auth informations and
 * rental informations (storing as {@link Transaction})
 *
 * @author Do Khoa Nguyen, Tran The Quang Minh
 * @see pls_no_shinobu.videostore.model.Entity
 */
public class User extends Entity {

    /** Enum for user role (user type) */
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
    private int point;
    private ArrayList<Transaction> rentals = new ArrayList<>();

    User() {
        super();
        this.name = "";
        this.address = "";
        this.phone = "";
        this.rentalCount = 0;
        this.role = UserType.GUEST;
    }

    /**
     * Constructor for a bare-minimum user, which includes an username and password
     *
     * @author Do Khoa Nguyen
     */
    public User(String id, String username, String password) throws IllegalArgumentException {
        super();
        setId(id);
        setUsername(username);
        setPassword(password);
    }

    /**
     * Constructor for a full-blown user, normally to initiate the user when loading from persistant
     * data, such as a file
     *
     * @author Do Khoa Nguyen
     */
    public User(
            String id,
            String name,
            String address,
            String phone,
            int rentalCount,
            UserType role,
            String username,
            String password,
            ArrayList<Transaction> rentals)
            throws IllegalArgumentException {
        super();
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

    /**
     * Method for setting {@link User} id, with checks to ensure the correct format
     *
     * @author Do Khoa Nguyen
     */
    @Override
    public void setId(String id) throws IllegalArgumentException {
        if (!id.matches("^C\\d{3}$")) throw new IllegalArgumentException("Invalid user ID format");

        super.setId(id);
    }

    /**
     * Method to get {@link User} name
     *
     * @author Do Khoa Nguyen
     */
    public String getName() {
        return name;
    }

    /**
     * Method for setting {@link User} name, with checks to ensure the name is different before
     * changes
     *
     * @author Tran The Quang Minh
     */
    public void setName(String name) throws IllegalArgumentException {
        if (getName().contains(name)) throw new IllegalArgumentException("Name is unchanged");

        this.name = name;
    }

    /**
     * Method to get {@link User} address
     *
     * @author Do Khoa Nguyen
     */
    public String getAddress() {
        return address;
    }

    /**
     * Method for setting {@link User} address, with checks to ensure the address is different
     * before changes
     *
     * @author Tran The Quang Minh
     */
    public void setAddress(String address) throws IllegalArgumentException {
        if (getAddress().contains(address))
            throw new IllegalArgumentException("Address is unchanged");

        this.address = address;
    }

    /**
     * Method to get {@link User} phone number
     *
     * @author Do Khoa Nguyen
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Method for setting {@link User} phone number, with checks to ensure the phone number is
     * different before changes
     *
     * @author Tran The Quang Minh
     */
    public void setPhone(String phone) throws IllegalArgumentException {
        if (getPhone().contains(phone))
            throw new IllegalArgumentException("Phone number is unchanged");

        this.phone = phone;
    }

    /**
     * Method to get {@link User} rental count
     *
     * @author Do Khoa Nguyen
     */
    public int getRentalCount() {
        return rentalCount;
    }

    /**
     * Method for setting {@link User} rental count, with checks to ensure the rental count is
     * positive. Normally use for loading/initiating the object
     *
     * @author Do Khoa Nguyen
     */
    public void setRentalCount(int rentalCount) throws IllegalArgumentException {
        if (getRentalCount() < 0)
            throw new IllegalArgumentException("Rental count cannot be negative");

        this.rentalCount = rentalCount;
    }

    /**
     * Method to increase {@link User} rental count
     *
     * @author Do Khoa Nguyen
     */
    public int increateRentalCount() {
        this.rentalCount++;

        return rentalCount;
    }

    /**
     * Method to get {@link User} role
     *
     * @author Do Khoa Nguyen
     */
    public UserType getRole() {
        return role;
    }

    /**
     * Method for setting {@link User} role
     *
     * @author Do Khoa Nguyen
     */
    public void setRole(UserType role) {
        this.role = role;
    }

    /**
     * Method to get {@link User} username
     *
     * @author Do Khoa Nguyen
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method for setting {@link User} username
     *
     * @author Do Khoa Nguyen
     */
    public void setUsername(String username) throws IllegalArgumentException {
        if (username.isBlank()) throw new IllegalArgumentException("Username cannot be empty");

        if (username.contains(" "))
            throw new IllegalArgumentException("Username cannot contain spaces");

        this.username = username;
    }

    /**
     * Method for setting {@link User} password, which pass in a raw password into a utils and store
     * it hashed using PBKDF2
     *
     * @param password a raw string password to be hash and save
     * @see PasswordUtils
     * @author Do Khoa Nguyen
     */
    protected void setPassword(String password) throws IllegalArgumentException {
        if (password.isEmpty()) throw new IllegalArgumentException("Password cannot be empty");

        PasswordUtils p = new PasswordUtils();

        this.password = p.hash(password);
    }

    /**
     * Method for checking if {@link User} password is the same as the entered password
     *
     * @param password a raw string password to be hash and compare
     * @see PasswordUtils
     * @author Do Khoa Nguyen
     */
    protected boolean checkPassword(String password) {
        PasswordUtils p = new PasswordUtils();

        return p.check(password, this.password);
    }

    /**
     * Method to get {@link User} point
     *
     * @author Do Khoa Nguyen
     */
    public int getPoint() {
        return point;
    }

    /**
     * Method for setting {@link User} point, with checks for point to be positive
     *
     * @author Do Khoa Nguyen
     */
    public void setPoint(int point) throws IllegalArgumentException {
        if (point < 0) throw new IllegalArgumentException("Point cannot be negative");

        this.point = point;
    }

    /**
     * Method to get {@link User} rentals, which will return an {@link ArrayList} of {@link
     * Transaction}
     *
     * @author Do Khoa Nguyen
     */
    public ArrayList<Transaction> getRentals() {
        return rentals;
    }

    /**
     * Method for setting {@link User} rentals
     *
     * @author Do Khoa Nguyen
     */
    public void setRentals(ArrayList<Transaction> rentals) {
        this.rentals = rentals;
    }

    /**
     * Method to let a user rent an {@link Item}, which will performs checks on Guest user, that is:
     *
     * <ul>
     *   <li>Can only rent maximum 2 items at a time.
     *   <li>Cannot rent a TWO_DAY item
     * </ul>
     *
     * @return {@link Transaction} a successful transaction object
     * @author Do Khoa Nguyen
     */
    public Transaction addRental(Item item)
            throws OutOfStockException, RentLimitException, IllegalArgumentException {
        if (role == UserType.GUEST) {
            if (rentals.size() == 2)
                throw new RentLimitException("Guest user can only rent maximum of two items");
            if (item.getLoanType() == Item.LoanType.TWO_DAY)
                throw new IllegalArgumentException("Guest user cannot rent two-day item");
        }

        // Decrease item stock
        item.decreaseStock();

        // If success, create a new transaction
        Transaction transaction = new Transaction(this, item);

        this.rentals.add(transaction);

        increateRentalCount();

        return transaction;
    }

    /**
     * Method to remove a user rental - in case, "return the rental", which will resolve the {@link
     * Transaction} and performs any standing checks on the user
     *
     * @author Do Khoa Nguyen
     * @see #checkStanding()
     */
    public void removeRental(Transaction transaction) throws IllegalArgumentException {
        if (!this.rentals.contains(transaction))
            throw new IllegalArgumentException("Transaction not available");

        transaction.getItem().increaseStock();
        transaction.setResolved();
        increateRentalCount();

        // Check on user standing every successful transaction
        checkStanding();

        this.rentals.remove(transaction);
    }

    /**
     * Method to perform actions on user's role, that is:
     *
     * <ul>
     *   <li>If eligible to "rank up", then perform
     *   <li>If the user role is VIP, accumulate point
     * </ul>
     *
     * @author Do Khoa Nguyen
     */
    public void checkStanding() {
        // This performs comparison on >= in case the system misses some user out
        if (role == UserType.GUEST && rentalCount >= 3) role = UserType.REGULAR;
        if (role == UserType.REGULAR && rentalCount >= 5) role = UserType.VIP;
        if (role == UserType.VIP) point += 10;
    }
}
