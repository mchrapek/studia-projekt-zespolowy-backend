package com.journeyplanner.catalogue.domain.journey;

import lombok.NonNull;
import lombok.Value;

@Value
class Accommodation {

    @NonNull StayType stayType;

    @NonNull int ratingStar;

    @NonNull int peopleInRoom;

    @NonNull AccommodationAddress address;
}

@Value
class AccommodationAddress {

    @NonNull String country;

    @NonNull String city;

    @NonNull String street;

    @NonNull String postCode;
}

enum StayType {

    HOTEL, HOSTEL, APARTMENT, GUESTHOUSE;
}

