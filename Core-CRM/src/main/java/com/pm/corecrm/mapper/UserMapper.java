package com.pm.corecrm.mapper;

import com.pm.corecrm.domain.entity.User;
import com.pm.corecrm.domain.dto.user.CreateUserRequest;
import com.pm.corecrm.domain.dto.user.UpdateUserRequest;
import com.pm.corecrm.domain.dto.user.UserDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserRequest request);

    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateUserRequest request, @MappingTarget User user);
}