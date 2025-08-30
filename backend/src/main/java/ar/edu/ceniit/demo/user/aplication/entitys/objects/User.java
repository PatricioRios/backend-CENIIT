package ar.edu.ceniit.demo.user.aplication.entitys.objects;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBaseException;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public class User {
    //Propiedades del sistema
    private UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private Integer id ;
    private String username;
    private String password;
    private String email = "default@email.com";

    //propiedades del dominio
    private String firstName;
    private String secondName;
    private String surname;
    private String secondSurname;
    private Integer dni;

    //Propiedades de DB (?
    private Instant createdAt;
    private Instant updatedAt;

    public User(
            UUID uuid,
            Integer id,
            String username,
            String password,
            String email,
            String firstName,
            String secondName,
            String surname,
            String secondSurname,
            Integer dni,
            Instant createdAt,
            Instant updatedAt
    ) throws UserBaseException {

        if (!verifyEmail(email)) {
            throw new UserBaseException("Email format is invalid");
        }

        this.uuid = uuid;
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.surname = surname;
        this.secondSurname = secondSurname;
        this.dni = dni;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private boolean verifyUsername(String username) {
        // Verifica que el nombre de usuario tenga al menos 3 caracteres y no más de 20
        return username.length() >= 3 && username.length() <= 20;
    }
    private boolean verifyPassword(String password) {//TODO
        // Verifica que la contraseña tenga al menos 8 caracteres, una letra mayúscula, una letra minúscula y un número
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        return password.matches(passwordRegex);
    }
    private boolean verifyEmail(String email) {
        // Verifica que el email tenga un formato válido
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
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

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
