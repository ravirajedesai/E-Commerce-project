package com.example.user_service.repository;

import com.example.user_service.dto.UserResponse;
import com.example.user_service.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {

    UserResponse findByUserId(Long userId);
}
