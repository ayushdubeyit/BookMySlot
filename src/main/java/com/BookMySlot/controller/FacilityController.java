package com.BookMySlot.controller;

import com.BookMySlot.dto.FacilityRequestDTO;
import com.BookMySlot.dto.FacilityResponseDTO;
import com.BookMySlot.entity.Category;
import com.BookMySlot.entity.SubCategory;
import com.BookMySlot.service.FacilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @Autowired
    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @PostMapping
    public ResponseEntity<FacilityResponseDTO> createFacility(
            @Valid @RequestBody FacilityRequestDTO requestDTO) {
        FacilityResponseDTO response = facilityService.createFacility(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacilityResponseDTO> getFacilityById(@PathVariable Long id) {
        FacilityResponseDTO response = facilityService.getFacilityById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FacilityResponseDTO>> getAllFacilities() {
        List<FacilityResponseDTO> facilities = facilityService.getAllFacilities();
        return ResponseEntity.ok(facilities);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FacilityResponseDTO>> searchFacilities(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) SubCategory subCategory) {

        List<FacilityResponseDTO> facilities = facilityService
                .searchFacilities(city, category, subCategory);
        return ResponseEntity.ok(facilities);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<FacilityResponseDTO>> findNearbyFacilities(
            @RequestParam BigDecimal latitude,
            @RequestParam BigDecimal longitude,
            @RequestParam(defaultValue = "5.0") double radius) {

        List<FacilityResponseDTO> facilities = facilityService
                .findNearbyFacilities(latitude, longitude, radius);
        return ResponseEntity.ok(facilities);
    }

    @GetMapping("/my-facilities")
    public ResponseEntity<List<FacilityResponseDTO>> getMyFacilities(
            @RequestParam Long ownerId) {
        List<FacilityResponseDTO> facilities = facilityService.getMyFacilities(ownerId);
        return ResponseEntity.ok(facilities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
        return ResponseEntity.noContent().build();
    }
}