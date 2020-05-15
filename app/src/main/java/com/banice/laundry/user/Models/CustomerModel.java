package com.banice.laundry.user.Models;

public class CustomerModel {

    String Customer_second_name, customer_email,customer_first_name,customer_id,customer_phoneNumber;

    public CustomerModel() {
    }

    public CustomerModel(String customer_second_name, String customer_email, String customer_first_name, String customer_id, String customer_phoneNumber) {
        Customer_second_name = customer_second_name;
        this.customer_email = customer_email;
        this.customer_first_name = customer_first_name;
        this.customer_id = customer_id;
        this.customer_phoneNumber = customer_phoneNumber;
    }


    public String getCustomer_second_name() {
        return Customer_second_name;
    }

    public void setCustomer_second_name(String customer_second_name) {
        Customer_second_name = customer_second_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_first_name() {
        return customer_first_name;
    }

    public void setCustomer_first_name(String customer_first_name) {
        this.customer_first_name = customer_first_name;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_phoneNumber() {
        return customer_phoneNumber;
    }

    public void setCustomer_phoneNumber(String customer_phoneNumber) {
        this.customer_phoneNumber = customer_phoneNumber;
    }
}
