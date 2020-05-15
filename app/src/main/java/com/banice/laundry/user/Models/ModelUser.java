package com.banice.laundry.user.Models;

public class ModelUser {

    //user

    String Provider_Name, Provider_email, Provider_contact, Uid,Provider_Id;

    public ModelUser() {
    }

    public ModelUser(String provider_Name, String provider_email, String provider_contact, String uid, String provider_Id) {
        Provider_Name = provider_Name;
        Provider_email = provider_email;
        Provider_contact = provider_contact;
        Uid = uid;
        Provider_Id = provider_Id;
    }

    public String getProvider_Name() {
        return Provider_Name;
    }

    public void setProvider_Name(String provider_Name) {
        Provider_Name = provider_Name;
    }

    public String getProvider_email() {
        return Provider_email;
    }

    public void setProvider_email(String provider_email) {
        Provider_email = provider_email;
    }

    public String getProvider_contact() {
        return Provider_contact;
    }

    public void setProvider_contact(String provider_contact) {
        Provider_contact = provider_contact;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getProvider_Id() {
        return Provider_Id;
    }

    public void setProvider_Id(String provider_Id) {
        Provider_Id = provider_Id;
    }
}
