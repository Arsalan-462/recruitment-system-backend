package com.example.recruitmentsystem.controller;

import com.example.recruitmentsystem.model.Profile;
import com.example.recruitmentsystem.model.User;
import com.example.recruitmentsystem.repository.ProfileRepository;
import com.example.recruitmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ResumeController {

    private static final String API_ENDPOINT = "https://api.apilayer.com/resume_parser/upload";
    private static final String API_KEY = "0bWeisRWoLj3UdXt3MXMSMWptYFIpQfS";

    @Autowired private UserRepository userRepository;
    @Autowired private ProfileRepository profileRepository;

    private final Path uploadDir = Paths.get("uploads");

    @PostMapping(value = "/uploadResume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file, Authentication authentication) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (!(ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("docx"))) {
            return ResponseEntity.badRequest().body("Only PDF or DOCX files are allowed");
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        if (!user.getUserType().equalsIgnoreCase("Applicant")) {
            return ResponseEntity.status(403).body("Only applicants can upload resume");
        }

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filepath = uploadDir.resolve(filename);
        Files.write(filepath, file.getBytes());

        // Call third party API to parse resume
        RestTemplate restTemplate = new RestTemplate();
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("apikey", API_KEY);
        headers.set("Content-Type", "application/octet-stream");

        org.springframework.http.HttpEntity<byte[]> requestEntity = new org.springframework.http.HttpEntity<>(file.getBytes(), headers);

        String apiUrl = API_ENDPOINT;

        var response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(500).body("Resume parsing API error");
        }

        // JSON response and update profile
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(response.getBody());

        // Extract strings for skills, education, experience, phone
        String skills = root.has("skills") ? root.get("skills").toString() : "";
        StringBuilder educationBuilder = new StringBuilder();
        if (root.has("education") && root.get("education").isArray()) {
            for (var edu : root.get("education")) {
                if (edu.has("name")) educationBuilder.append(edu.get("name").asText()).append("; ");
            }
        }
        String education = educationBuilder.toString();

        StringBuilder experienceBuilder = new StringBuilder();
        if (root.has("experience") && root.get("experience").isArray()) {
            for (var exp : root.get("experience")) {
                if (exp.has("name")) experienceBuilder.append(exp.get("name").asText()).append("; ");
            }
        }
        String experience = experienceBuilder.toString();

        String phone = root.has("phone") ? root.get("phone").asText() : "";

        Profile profile = user.getProfile();
        if (profile == null) profile = new Profile();
        profile.setApplicant(user);
        profile.setResumeFileAddress(filepath.toString());
        profile.setSkills(skills);
        profile.setEducation(education);
        profile.setExperience(experience);
        profile.setPhone(phone);

        profileRepository.save(profile);

        return ResponseEntity.ok("Resume uploaded and parsed successfully");
    }
}

