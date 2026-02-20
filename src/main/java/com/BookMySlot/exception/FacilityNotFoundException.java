package com.BookMySlot.exception;

public class FacilityNotFoundException extends RuntimeException {

    public FacilityNotFoundException(String message) {
        super(message);
    }

    public FacilityNotFoundException(Long facilityId) {
        super("Facility not found with id: " + facilityId);
    }
}