package com.banice.laundry.Provider.Model;

public class ServiceModel {

    String service_desc,service_id,service_name;

    public ServiceModel() {
    }

    public ServiceModel(String service_desc, String service_id, String service_name) {
        this.service_desc = service_desc;
        this.service_id = service_id;
        this.service_name = service_name;
    }

    public String getService_desc() {
        return service_desc;
    }

    public void setService_desc(String service_desc) {
        this.service_desc = service_desc;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }
}
