package com.ky.dbmanagementsystem;

import com.ky.dbmanagementsystem.enumeration.Role;
import com.ky.dbmanagementsystem.model.SubjectTopic;
import com.ky.dbmanagementsystem.repository.SubjectTopicRepository;
import com.ky.dbmanagementsystem.request.RegisterRequest;
import com.ky.dbmanagementsystem.security.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class DbManagementSystemApplication implements CommandLineRunner {

    private final SubjectTopicRepository subjectTopicRepository;
    private final AuthenticationService authenticationService;

    public DbManagementSystemApplication(SubjectTopicRepository subjectTopicRepository, AuthenticationService authenticationService) {
        this.subjectTopicRepository = subjectTopicRepository;
        this.authenticationService = authenticationService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DbManagementSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<SubjectTopic> existTopics = subjectTopicRepository.findAll();
        List<SubjectTopic> newSubjectTopics = createTopics(existTopics);
        subjectTopicRepository.saveAll(newSubjectTopics);

    }
    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service){
        return args -> {
            if(!authenticationService.isAdminAlreadyRegistered()){
                var admin = RegisterRequest.builder()
                        .firstName("ADMIN")
                        .lastName("ADMIN")
                        .email("admin@gmail.com")
                        .password("password")
                        .role(Role.ADMIN)
                        .build();
                System.out.println("Admin Token: " + service.register(admin).getAccessToken());
            }else{
                System.out.println("Admin already saved.");
            }

        };
    }
    private List<SubjectTopic> createTopics(List<SubjectTopic> existTopics){
        List<SubjectTopic> newSubjectTopics = new ArrayList<>();
        String[] subjectTopicNames = {
                "History",
                "Philosophy",
                "Statistics",
                "Industrial Engineering",
                "Medicine",
                "Maths",
                "Economics",
                "Computer Engineering",
                "Software Engineering",
                "Electrical Engineering"
        };
        for(String topic : subjectTopicNames){

            boolean exists = existTopics.stream()
                    .anyMatch(existingTopic -> existingTopic.getName().equalsIgnoreCase(topic));

            if(!exists){
                SubjectTopic subjectTopic = SubjectTopic.builder()
                        .id(UUID.randomUUID().toString())
                        .name(topic)
                        .build();
                newSubjectTopics.add(subjectTopic);
            }

        }
        return newSubjectTopics;
    }
}
