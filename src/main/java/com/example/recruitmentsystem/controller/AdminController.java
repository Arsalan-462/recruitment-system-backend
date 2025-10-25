package com.example.recruitmentsystem.controller;

import com.example.recruitmentsystem.model.Job;
import com.example.recruitmentsystem.model.User;
import com.example.recruitmentsystem.repository.JobRepository;
import com.example.recruitmentsystem.repository.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/admin")
@RolesAllowed("ADMIN")
public class AdminController {

    @Autowired private JobRepository jobRepository;
    @Autowired private UserRepository userRepository;

    @PostMapping("/job")
    public ResponseEntity<?> createJob(@RequestBody Job job, @RequestHeader("Authorization") String authHeader) {
        // Simplify: postedBy assigned later

        job.setPostedOn(LocalDateTime.now());
        job.setTotalApplications(0);

        jobRepository.save(job);
        return ResponseEntity.ok("Job created");
    }

    @GetMapping("/job/{job_id}")
    public ResponseEntity<?> getJob(@PathVariable Long job_id) {
        Optional<Job> jobOpt = jobRepository.findById(job_id);
        if (jobOpt.isEmpty())
            return ResponseEntity.notFound().build();

        Job job = jobOpt.get();
        // Return job details + list of applicants
        Map<String, Object> map = new HashMap<>();
        map.put("job", job);
        map.put("applicants", job.getApplicants());
        return ResponseEntity.ok(map);
    }

    @GetMapping("/applicants")
    public ResponseEntity<?> getApplicants() {
        List<User> applicants = userRepository.findAll().stream()
                .filter(u -> u.getUserType().equalsIgnoreCase("Applicant"))
                .toList();
        return ResponseEntity.ok(applicants);
    }

    @GetMapping("/applicant/{applicant_id}")
    public ResponseEntity<?> getApplicant(@PathVariable Long applicant_id) {
        Optional<User> userOpt = userRepository.findById(applicant_id);
        if (userOpt.isEmpty()) return ResponseEntity.notFound().build();

        User user = userOpt.get();
        if (!user.getUserType().equalsIgnoreCase("Applicant")) {
            return ResponseEntity.badRequest().body("User is not an applicant");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("profile", user.getProfile());
        return ResponseEntity.ok(map);
    }
}

