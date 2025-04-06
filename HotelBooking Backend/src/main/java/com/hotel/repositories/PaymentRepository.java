package com.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.entities.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
