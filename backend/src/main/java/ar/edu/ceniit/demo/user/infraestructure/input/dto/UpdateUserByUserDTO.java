package ar.edu.ceniit.demo.user.infraestructure.input.dto;

import lombok.Data;

@Data
public class UpdateUserByUserDTO {
    private String uuid;
    private String firstName;
    private String secondName;
    private String surname;
    private String secondSurname;
    private String email;

    public UpdateUserByUserDTO() {
    }

    public UpdateUserByUserDTO(String firstName, String secondName, String surname, String secondSurname) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.surname = surname;
        this.secondSurname = secondSurname;
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
