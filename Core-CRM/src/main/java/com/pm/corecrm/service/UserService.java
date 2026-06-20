package com.pm.corecrm.service;

import com.pm.corecrm.domain.dto.user.CreateUserRequest;
import com.pm.corecrm.domain.dto.user.UpdateUserRequest;
import com.pm.corecrm.domain.dto.user.UserDto;
import com.pm.corecrm.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserDto createUser(CreateUserRequest request);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    List<UserDto> getAllUsers();

    boolean existsByEmail(String email);

    void deleteUser(Long id);

    UserDto updateUser(Long id, UpdateUserRequest request);

    Page<UserDto> getAllUsers(Pageable pageable);





}
