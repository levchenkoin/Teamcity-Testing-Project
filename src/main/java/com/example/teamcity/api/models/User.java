package com.example.teamcity.api.models;

<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
>>>>>>> hometask-4
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD
=======
@JsonIgnoreProperties(ignoreUnknown = true)

>>>>>>> hometask-4
public class User {
    private String username;
    private String password;
    private String email;
    private Roles roles;
}
