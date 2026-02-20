package com.BookMySlot.service;

import com.BookMySlot.dto.FacilityRequestDTO;
import com.BookMySlot.dto.FacilityResponseDTO;
import com.BookMySlot.entity.*;
import com.BookMySlot.exception.FacilityNotFoundException;
import com.BookMySlot.exception.InvalidCategoryException;
import com.BookMySlot.exception.UserNotFoundException;
import com.BookMySlot.repository.FacilityRepository;
import com.BookMySlot.repository.UserRepository;
import com.BookMySlot.util.FacilityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;
    private final UserRepository userRepository;

    @Autowired
    public FacilityServiceImpl(FacilityRepository facilityRepository,
                               UserRepository userRepository) {
        this.facilityRepository = facilityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FacilityResponseDTO createFacility(FacilityRequestDTO requestDTO) {
        // Validate owner exists
        User owner = userRepository.findById(requestDTO.getOwnerId())
                .orElseThrow(() -> new UserNotFoundException(requestDTO.getOwnerId()));

        // Validate category-subcategory combination
        validateCategorySubCategory(requestDTO.getCategory(), requestDTO.getSubCategory());

        // Convert DTO to Entity
        Facility facility = FacilityMapper.toEntity(requestDTO, owner);

        // Save to database
        Facility savedFacility = facilityRepository.save(facility);

        // Convert to Response DTO
        return FacilityMapper.toResponseDTO(savedFacility);
    }

    @Override
    public FacilityResponseDTO getFacilityById(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new FacilityNotFoundException(facilityId));

        return FacilityMapper.toResponseDTO(facility);
    }

    @Override
    public List<FacilityResponseDTO> getAllFacilities() {
        return facilityRepository.findByIsActiveTrueAndIsVerifiedTrue()
                .stream()
                .map(FacilityMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FacilityResponseDTO> getFacilitiesByCategory(Category category) {
        return facilityRepository.findByCategory(category)
                .stream()
                .map(FacilityMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FacilityResponseDTO> getFacilitiesByCity(String city) {
        return facilityRepository.findByCity(city)
                .stream()
                .map(FacilityMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FacilityResponseDTO> searchFacilities(String city, Category category, SubCategory subCategory) {
        List<Facility> facilities;

        if (city != null && category != null && subCategory != null) {
            // All filters provided
            facilities = facilityRepository.findByCityAndSubCategory(city, subCategory);
        } else if (city != null && category != null) {
            // City and category
            facilities = facilityRepository.findByCityAndCategory(city, category);
        } else if (city != null) {
            // City only
            facilities = facilityRepository.findByCity(city);
        } else if (category != null) {
            // Category only
            facilities = facilityRepository.findByCategory(category);
        } else {
            // No filters - all active and verified
            facilities = facilityRepository.findByIsActiveTrueAndIsVerifiedTrue();
        }

        return facilities.stream()
                .map(FacilityMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FacilityResponseDTO> findNearbyFacilities(BigDecimal latitude, BigDecimal longitude, double radiusKm) {
        return facilityRepository.findNearbyFacilities(latitude, longitude, radiusKm)
                .stream()
                .map(FacilityMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FacilityResponseDTO> getMyFacilities(Long ownerId) {
        return facilityRepository.findByOwner_UserId(ownerId)
                .stream()
                .map(FacilityMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFacility(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new FacilityNotFoundException(facilityId));

        facilityRepository.delete(facility);
    }

    // Helper method to validate category-subcategory combination
    private void validateCategorySubCategory(Category category, SubCategory subCategory) {
        boolean isValid = false;

        if (category == Category.TURF) {
            isValid = subCategory == SubCategory.CRICKET_TURF ||
                    subCategory == SubCategory.BADMINTON_TURF ||
                    subCategory == SubCategory.FOOTBALL_TURF ||
                    subCategory == SubCategory.BASKETBALL_TURF;
        } else if (category == Category.GAMING) {
            isValid = subCategory == SubCategory.GAMING_PC ||
                    subCategory == SubCategory.PS5 ||
                    subCategory == SubCategory.XBOX ||
                    subCategory == SubCategory.VR_SETUP;
        }

        if (!isValid) {
            throw new InvalidCategoryException(
                    "Invalid combination: " + category + " cannot have sub-category " + subCategory
            );
        }
    }
}