package com.example.recruitmentsystem.repository;

import com.example.recruitmentsystem.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}

