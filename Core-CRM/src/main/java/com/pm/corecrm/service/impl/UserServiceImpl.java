package com.pm.corecrm.service.impl;

import com.pm.corecrm.domain.dto.user.CreateUserRequest;
import com.pm.corecrm.domain.dto.user.UpdateUserRequest;
import com.pm.corecrm.domain.dto.user.UserDto;
import com.pm.corecrm.domain.entity.User;
import com.pm.corecrm.mapper.UserMapper;
import com.pm.corecrm.repository.UserRepository;
import com.pm.corecrm.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with email " + request.getEmail() +
                    "already exists");
        }
        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found id:" + id));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found id:" + email));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw  new RuntimeException("User not found with id" + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found with id:" + id));
        userMapper.updateEntity(request, user);
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }
}
