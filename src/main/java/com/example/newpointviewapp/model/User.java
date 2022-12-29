package com.example.newpointviewapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Username mustn't be empty !")
    private String username;

    @NotBlank(message = "Password mustn't be empty !")
    private String password;

    @Email(message = "Email should be valid !")
    @NotEmpty(message = "Email mustn't be empty !")
    private String email;

    private Instant created;

    private boolean enabled;


}
