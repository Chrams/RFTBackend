package com.rftdevgroup.transporthub.data.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDTO {
    private String country;
    private String zipcode;
    private String city;
    private String street;
    private String houseNo;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer()
                .append(country).append(", ")
                .append(zipcode).append(" ")
                .append(city).append(" ")
                .append(street).append(" ")
                .append(houseNo);
        return sb.toString();
    }
}
