package com.BookMySlot.util;

import com.BookMySlot.dto.FacilityRequestDTO;
import com.BookMySlot.dto.FacilityResponseDTO;
import com.BookMySlot.entity.Facility;
import com.BookMySlot.entity.User;

public class FacilityMapper {

    // Convert FacilityRequestDTO to Facility Entity
    public static Facility toEntity(FacilityRequestDTO dto, User owner) {
        Facility facility = new Facility();
        facility.setOwner(owner);
        facility.setFacilityName(dto.getFacilityName());
        facility.setDescription(dto.getDescription());
        facility.setCategory(dto.getCategory());
        facility.setSubCategory(dto.getSubCategory());
        facility.setAddress(dto.getAddress());
        facility.setCity(dto.getCity());
        facility.setArea(dto.getArea());
        facility.setPincode(dto.getPincode());
        facility.setLatitude(dto.getLatitude());
        facility.setLongitude(dto.getLongitude());
        facility.setContactNumber(dto.getContactNumber());
        facility.setOperatingHours(dto.getOperatingHours());
        facility.setAmenities(dto.getAmenities());
        facility.setBasePriceHourly(dto.getBasePriceHourly());
        return facility;
    }

    // Convert Facility Entity to FacilityResponseDTO
    public static FacilityResponseDTO toResponseDTO(Facility facility) {
        FacilityResponseDTO dto = new FacilityResponseDTO();
        dto.setFacilityId(facility.getFacilityId());
        dto.setOwnerId(facility.getOwner().getUserId());
        dto.setOwnerName(facility.getOwner().getUsername());
        dto.setFacilityName(facility.getFacilityName());
        dto.setDescription(facility.getDescription());
        dto.setCategory(facility.getCategory());
        dto.setSubCategory(facility.getSubCategory());
        dto.setAddress(facility.getAddress());
        dto.setCity(facility.getCity());
        dto.setArea(facility.getArea());
        dto.setPincode(facility.getPincode());
        dto.setLatitude(facility.getLatitude());
        dto.setLongitude(facility.getLongitude());
        dto.setContactNumber(facility.getContactNumber());
        dto.setOperatingHours(facility.getOperatingHours());
        dto.setAmenities(facility.getAmenities());
        dto.setBasePriceHourly(facility.getBasePriceHourly());
        dto.setRatingAvg(facility.getRatingAvg());
        dto.setTotalReviews(facility.getTotalReviews());
        dto.setIsVerified(facility.getIsVerified());
        dto.setIsActive(facility.getIsActive());
        dto.setCreatedAt(facility.getCreatedAt());
        return dto;
    }
}