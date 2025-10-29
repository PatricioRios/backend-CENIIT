package ar.edu.ceniit.demo.user.infraestructure.input.dto;

import lombok.Builder;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Builder
@Relation(collectionRelation = "users")
public class UserResponseDTO {

    private Long id;
    private UUID uuid;
    private String username;
    private String email;
    private String firstName;
    private String secondName;
    private String surname;
    private String secondSurname;
    private Integer dni;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, UUID uuid, String username, String email, String firstName, String secondName, String surname, String secondSurname, Integer dni) {
        this.id = id;
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.surname = surname;
        this.secondSurname = secondSurname;
        this.dni = dni;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }
}
