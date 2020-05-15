package com.banice.laundry.Provider.Model;

public class ServiceDetails {

    String service_name, service_desc, Uid;

    public ServiceDetails() {
    }

    public ServiceDetails(String service_name, String service_desc, String uid) {
        this.service_name = service_name;
        this.service_desc = service_desc;
        this.Uid = uid;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_desc() {
        return service_desc;
    }

    public void setService_desc(String service_desc) {
        this.service_desc = service_desc;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        this.Uid = uid;
    }
}
