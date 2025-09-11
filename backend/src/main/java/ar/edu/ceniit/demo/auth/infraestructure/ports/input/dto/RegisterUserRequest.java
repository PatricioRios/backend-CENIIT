package ar.edu.ceniit.demo.auth.infraestructure.ports.input.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {

    //@NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;

    //@NotBlank(message = "La contraseña no puede estar vacía")
    //@Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    //@NotBlank(message = "El email no puede estar vacío")
    //@Email(message = "Debe ser una dirección de email válida")
    private String email;

    private String firstName;

    private String lastName;
}
