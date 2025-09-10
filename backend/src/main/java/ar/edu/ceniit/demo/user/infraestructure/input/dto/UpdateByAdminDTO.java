package ar.edu.ceniit.demo.user.infraestructure.input.dto;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class UpdateByAdminDTO {
    private String uuid;
    private String firstName;
    private String secondName;
    private String surname;
    private String secondSurname;
    private String email;
    private Integer dni;


    public UpdateByAdminDTO() {
    }

    public UpdateByAdminDTO(String firstName, String secondName, String surname, String secondSurname, Integer dni) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.surname = surname;
        this.secondSurname = secondSurname;
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
