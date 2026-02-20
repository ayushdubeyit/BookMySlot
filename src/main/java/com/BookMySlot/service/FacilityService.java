package com.BookMySlot.service;

import com.BookMySlot.dto.FacilityRequestDTO;
import com.BookMySlot.dto.FacilityResponseDTO;
import com.BookMySlot.entity.Category;
import com.BookMySlot.entity.SubCategory;

import java.math.BigDecimal;
import java.util.List;

public interface FacilityService {
    FacilityResponseDTO createFacility(FacilityRequestDTO requestDTO);
    FacilityResponseDTO getFacilityById(Long facilityId);
    List<FacilityResponseDTO> getAllFacilities();
    List<FacilityResponseDTO> getFacilitiesByCategory(Category category);
    List<FacilityResponseDTO> getFacilitiesByCity(String city);
    List<FacilityResponseDTO> searchFacilities(String city, Category category, SubCategory subCategory);
    List<FacilityResponseDTO> findNearbyFacilities(BigDecimal latitude, BigDecimal longitude, double radiusKm);
    List<FacilityResponseDTO> getMyFacilities(Long ownerId);
    void deleteFacility(Long facilityId);
}