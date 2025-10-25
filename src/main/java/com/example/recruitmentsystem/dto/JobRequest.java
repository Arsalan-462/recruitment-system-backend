package com.example.recruitmentsystem.dto;

import jakarta.validation.constraints.NotBlank;

public class JobRequest {

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String companyName;

    
}

