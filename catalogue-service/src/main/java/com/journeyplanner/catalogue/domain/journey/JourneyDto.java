package com.journeyplanner.catalogue.domain.journey;

import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class JourneyDto {

    String id;
    String name;
    String description;
    TransportType transportType;
    BigInteger price;
    Instant start;
    Instant end;
    List<JourneyPlaceDto> places;
    AccommodationDto accommodation;

    static JourneyDto from(final Journey journey) {
        return JourneyDto.builder()
                .id(journey.getId())
                .name(journey.getName())
                .description(journey.getDescription())
                .transportType(journey.getTransportType())
                .price(journey.getPrice())
                .start(journey.getStart())
                .end(journey.getEnd())
                .places(journey.getPlaces()
                        .stream()
                        .map(p -> new JourneyPlaceDto(p.getName(), p.getCountry(), p.getDescription()))
                        .collect(Collectors.toList()))
                .accommodation(AccommodationDto.builder()
                        .stayType(journey.getAccommodation().getStayType())
                        .ratingStar(journey.getAccommodation().getRatingStar())
                        .peopleInRoom(journey.getAccommodation().getPeopleInRoom())
                        .country(journey.getAccommodation().getAddress().getCountry())
                        .city(journey.getAccommodation().getAddress().getCity())
                        .street(journey.getAccommodation().getAddress().getStreet())
                        .postCode(journey.getAccommodation().getAddress().getPostCode())
                        .build())
                .build();
    }
}

@Value
class JourneyPlaceDto {

    String name;
    String country;
    String description;
}

@Value
@Builder
class AccommodationDto {

    StayType stayType;
    int ratingStar;
    int peopleInRoom;
    String country;
    String city;
    String street;
    String postCode;
}