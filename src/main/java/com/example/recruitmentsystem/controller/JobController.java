package com.example.recruitmentsystem.controller;

import com.example.recruitmentsystem.model.Job;
import com.example.recruitmentsystem.model.User;
import com.example.recruitmentsystem.repository.JobRepository;
import com.example.recruitmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class JobController {

    @Autowired private JobRepository jobRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/jobs")
    public ResponseEntity<?> getJobs() {
        return ResponseEntity.ok(jobRepository.findAll());
    }

    @GetMapping("/jobs/apply")
    public ResponseEntity<?> applyJob(@RequestParam Long job_id, Authentication authentication) {
        String email = authentication.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return ResponseEntity.status(403).body("Unauthorized");
        User user = userOpt.get();
        if (!user.getUserType().equalsIgnoreCase("Applicant"))
            return ResponseEntity.status(403).body("Only applicants can apply");

        Optional<Job> jobOpt = jobRepository.findById(job_id);
        if (jobOpt.isEmpty()) return ResponseEntity.notFound().build();

        Job job = jobOpt.get();
        job.getApplicants().add(user);
        job.setTotalApplications(job.getApplicants().size());
        jobRepository.save(job);

        return ResponseEntity.ok("Applied successfully");
    }
}

