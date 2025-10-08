package ar.edu.ceniit.demo.user.infraestructure.input.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private String email;
    private String firstName;
    private String secondName;
    private String surname;
    private String secondSurname;
    private Integer dni;
}
