package com.banice.laundry.user.Models;

public class ProviderServiceModel {

    String provider_service_id, provider_service_provider_id,provider_service_service_id;

    public ProviderServiceModel() {
    }

    public ProviderServiceModel(String provider_service_id, String provider_service_provider_id, String provider_service_service_id) {
        this.provider_service_id = provider_service_id;
        this.provider_service_provider_id = provider_service_provider_id;
        this.provider_service_service_id = provider_service_service_id;
    }

    public String getProvider_service_id() {
        return provider_service_id;
    }

    public void setProvider_service_id(String provider_service_id) {
        this.provider_service_id = provider_service_id;
    }

    public String getProvider_service_provider_id() {
        return provider_service_provider_id;
    }

    public void setProvider_service_provider_id(String provider_service_provider_id) {
        this.provider_service_provider_id = provider_service_provider_id;
    }

    public String getProvider_service_service_id() {
        return provider_service_service_id;
    }

    public void setProvider_service_service_id(String provider_service_service_id) {
        this.provider_service_service_id = provider_service_service_id;
    }
}
