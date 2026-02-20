package com.BookMySlot.repository;

import com.BookMySlot.entity.Category;
import com.BookMySlot.entity.Facility;
import com.BookMySlot.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    // Find by category
    List<Facility> findByCategory(Category category);

    // Find by sub-category
    List<Facility> findBySubCategory(SubCategory subCategory);

    // Find by city
    List<Facility> findByCity(String city);

    // Find by city and category
    List<Facility> findByCityAndCategory(String city, Category category);

    // Find by city and sub-category
    List<Facility> findByCityAndSubCategory(String city, SubCategory subCategory);

    // Find active and verified facilities
    List<Facility> findByIsActiveTrueAndIsVerifiedTrue();

    // Find by owner
    List<Facility> findByOwner_UserId(Long ownerId);

    // Find nearby facilities using distance formula
    @Query("SELECT f FROM Facility f WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(f.latitude)) * " +
            "cos(radians(f.longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(f.latitude)))) <= :radiusKm " +
            "AND f.isActive = true AND f.isVerified = true")
    List<Facility> findNearbyFacilities(
            @Param("latitude") BigDecimal latitude,
            @Param("longitude") BigDecimal longitude,
            @Param("radiusKm") double radiusKm);

    // Find nearby facilities by category
    @Query("SELECT f FROM Facility f WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(f.latitude)) * " +
            "cos(radians(f.longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(f.latitude)))) <= :radiusKm " +
            "AND f.category = :category " +
            "AND f.isActive = true AND f.isVerified = true")
    List<Facility> findNearbyFacilitiesByCategory(
            @Param("latitude") BigDecimal latitude,
            @Param("longitude") BigDecimal longitude,
            @Param("radiusKm") double radiusKm,
            @Param("category") Category category);
}

