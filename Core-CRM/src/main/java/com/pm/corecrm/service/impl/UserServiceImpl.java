package com.pm.corecrm.service.impl;

import com.pm.corecrm.domain.dto.user.CreateUserRequest;
import com.pm.corecrm.domain.dto.user.UserDto;
import com.pm.corecrm.domain.entity.User;
import com.pm.corecrm.mapper.UserMapper;
import com.pm.corecrm.repository.UserRepository;
import com.pm.corecrm.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(CreateUserRequest request) {
        if (userRepository.existByEmail(request.getEmail())) {
            throw new RuntimeException("User with email " + request.getEmail() +
                    "already exists");
        }
        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public UserDto getUserById(Long id) {
        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return List.of();
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
