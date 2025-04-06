package com.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
