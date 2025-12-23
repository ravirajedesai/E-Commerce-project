package com.example.user_service.service;

import com.example.user_service.dto.UserResponse;
import com.example.user_service.entity.Users;
import org.springframework.data.domain.Page;

public interface UsersServices {
    Page<Users> getAllUsers(int pageNo,
                            int pageSize,
                            String sortBy);
    Users getUserById(Long id);
    void deleteUserById(Long id);
    Users addUser(Users users);

    UserResponse getByUserId(Long userId);
}
