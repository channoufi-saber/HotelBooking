package com.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.entities.BookingReference;

import java.util.Optional;

public interface BookingReferenceRepository extends JpaRepository<BookingReference, Long> {

    Optional<BookingReference> findByReferenceNo(String referenceNo);

}
