package com.pm.corecrm.service;

import com.pm.corecrm.domain.dto.user.CreateUserRequest;
import com.pm.corecrm.domain.dto.user.UserDto;
import com.pm.corecrm.domain.entity.User;

import java.util.List;

public interface UserService {

    UserDto createUser(CreateUserRequest request);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    List<UserDto> getAllUsers();

    boolean existsByEmail(String email);

    void deleteUser(Long id);





}
