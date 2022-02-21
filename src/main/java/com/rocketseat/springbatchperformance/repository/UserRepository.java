package com.rocketseat.springbatchperformance.repository;

import com.rocketseat.springbatchperformance.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Notification, Integer> {
}
