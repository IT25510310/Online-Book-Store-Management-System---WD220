package com.example.onlinebookstore.model;

public class RegularUser extends User {
    private String contactNumber;
    private String address;

    public RegularUser() {
        super();
    }

    public RegularUser(String userId, String username, String password, String email, String fullName, String contactNumber, String address) {
        super(userId, username, password, email, fullName);
        this.contactNumber = contactNumber;
        this.address = address;
    }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String getDetails() {
        return "Customer [" + getUserId() + "]: Contact: " + contactNumber + ", Address: " + address;
    }

    @Override
    public String getUserType() {
        return "CUSTOMER";
    }
}
