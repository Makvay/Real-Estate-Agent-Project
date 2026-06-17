package com.pm.corecrm.domain.dto.user;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private String firstname;
    private String lastname;
    @Email(message = "Неверный формат email")
    private String email;
    private String phone;

}