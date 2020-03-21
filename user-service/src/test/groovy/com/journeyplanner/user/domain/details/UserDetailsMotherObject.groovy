package com.journeyplanner.user.domain.details

class UserDetailsMotherObject {

    static aUserDetails(String email) {
        UserDetails.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .country("COUNTRY")
                .city("CITY")
                .street("STREET")
                .postCode("POSTCODE")
                .phoneNumber("PHONENUMBER")
                .build()
    }
}
