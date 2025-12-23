package com.example.user_service.service;

import com.example.user_service.dto.UserResponse;
import com.example.user_service.entity.Users;
import com.example.user_service.exception.UserNotFound;
import com.example.user_service.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersServices{
    @Autowired
    UsersRepository repository;

    @Override
    public Page<Users> getAllUsers(int pageNo,
                                   int pageSize,
                                   String sortBy) {
        Pageable pageable= PageRequest.of(
                 pageNo-1,
                            pageSize,
                            Sort.by(sortBy));
        return repository.findAll(pageable);
    }

    @Override
    public Users getUserById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(()->
                        new UserNotFound("User Not Found By UserId: "+id));
    }

    @Override
    public void deleteUserById(Long id) {
    repository.deleteById(id);
    }

    @Override
    public Users addUser(Users users) {
        Users add=repository.save(users);
        return add;
    }

    @Override
    public UserResponse getByUserId(Long userId) {
        UserResponse userById=repository.findByUserId(userId);
        return userById;
    }
}
