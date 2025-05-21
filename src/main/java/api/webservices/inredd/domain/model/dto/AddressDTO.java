package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Address;

public class AddressDTO {

    private Long idAddress;
    private String address;
    private String country;
    private String state;
    private String city;

    public AddressDTO() {
    }

    public AddressDTO(Address address) {
        this.idAddress = address.getIdAddress();
        this.address = address.getAddress();
        this.country = address.getCountry();
        this.state = address.getState();
        this.city = address.getCity();
    }

    // Getters and Setters
    public Long getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Long idAddress) {
        this.idAddress = idAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}