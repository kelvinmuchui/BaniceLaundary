package com.banice.laundry.user.Models;

public class RequestModel {

    String request_time_surge,request_provider_service_id,request_pick_up_location,request_id,request_customer_id;

    public RequestModel() {
    }

    public RequestModel(String request_time_surge, String request_provider_service_id, String request_pick_up_location, String request_id, String request_customer_id) {
        this.request_time_surge = request_time_surge;
        this.request_provider_service_id = request_provider_service_id;
        this.request_pick_up_location = request_pick_up_location;
        this.request_id = request_id;
        this.request_customer_id = request_customer_id;
    }

    public String getRequest_time_surge() {
        return request_time_surge;
    }

    public void setRequest_time_surge(String request_time_surge) {
        this.request_time_surge = request_time_surge;
    }

    public String getRequest_provider_service_id() {
        return request_provider_service_id;
    }

    public void setRequest_provider_service_id(String request_provider_service_id) {
        this.request_provider_service_id = request_provider_service_id;
    }

    public String getRequest_pick_up_location() {
        return request_pick_up_location;
    }

    public void setRequest_pick_up_location(String request_pick_up_location) {
        this.request_pick_up_location = request_pick_up_location;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getRequest_customer_id() {
        return request_customer_id;
    }

    public void setRequest_customer_id(String request_customer_id) {
        this.request_customer_id = request_customer_id;
    }
}
