package com.example.recruitmentsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private String address;

    @NotBlank
    private String userType;    // "Admin" or "Applicant"

    @NotBlank
    @Size(min = 6)
    private String password;

    private String profileHeadline;

    
}

