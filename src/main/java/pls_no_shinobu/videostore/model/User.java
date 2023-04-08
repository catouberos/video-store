package pls_no_shinobu.videostore.model;

import java.util.ArrayList;

public class User extends Entity {
    public enum AccountType {
        GUEST,
        REGULAR,
        VIP
    }
    private String name;
    private String userAddress;
    private String userPhone;
    private String userName;
    private String userPassword;
    private AccountType role;
    private int rolePoint = 0;
    private ArrayList<Item> rentedItems = new ArrayList<Item>();

    public User(String id, String userName, String userPassword) {
        super(id);
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = AccountType.GUEST;
    }

    public User(String id, String userName, String userPassword, String userAddress, String userPhone, String name) {
        this(id, userName, userPassword);
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.name = name;
        this.role = AccountType.GUEST;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return userPassword;
    }

    public void setPassword(String password) {
        this.userPassword = password;
    }

    public String getAddress() {
        return userAddress;
    }

    public void setAddress(String address) {
        this.userAddress = address;
    }

    public String getPhone() {
        return userPhone;
    }

    public void setPhone(String phone) {
        this.userPhone = phone;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public AccountType getRole() {
        return role;
    }

    public int getPoints() {
        return rolePoint;
    }

    public ArrayList<Item> getRentedItems() {
        return rentedItems;
    }

}
